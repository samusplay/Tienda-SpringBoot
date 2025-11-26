package com.example.tienda.service.impl;

import com.example.tienda.entity.*;
import com.example.tienda.models.VentaActualizarRq;
import com.example.tienda.models.VentaItemRq;
import com.example.tienda.models.VentaRq;
import com.example.tienda.models.VentaRs;
import com.example.tienda.repository.*;
import com.example.tienda.service.VentaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final SucursalRepository sucursalRepository;
    private final ClienteRepository clienteRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ProductoRepository productoRepository;
    private final  InventarioRepository inventarioRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    @Override
    @Transactional
    public VentaRs crear(VentaRq rq) {

        Sucursal sucursal = sucursalRepository.findById(rq.getIdSucursal())
                .orElseThrow(() -> new IllegalArgumentException("La sucursal indicada no existe"));

        Cliente cliente = clienteRepository.findById(rq.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("El cliente indicado no existe"));

        Empleado empleado = empleadoRepository.findById(rq.getIdEmpleado())
                .orElseThrow(() -> new IllegalArgumentException("El empleado indicado no existe"));

        if (rq.getItems() == null || rq.getItems().isEmpty()) {
            throw new IllegalArgumentException("Debe agregar al menos un producto a la venta");
        }

        // Creamos venta con total 0; luego lo recalculamos
        Venta venta = Venta.builder()
                .sucursal(sucursal)
                .cliente(cliente)
                .empleado(empleado)
                .total(BigDecimal.ZERO)
                .build();

        // Guardamos para obtener id_venta
        venta = ventaRepository.save(venta);

        BigDecimal total = BigDecimal.ZERO;
        Producto primerProducto = null; // para mostrar algo en la tabla de ventas

        // -------- DETALLES --------
        for (VentaItemRq item : rq.getItems()) {

            // Producto
            Producto producto = productoRepository.findById(item.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("El producto indicado no existe"));

            if (primerProducto == null) {
                primerProducto = producto;
            }

            // Inventario por sucursal + producto
            Inventario inventario = inventarioRepository
                    .findBySucursal_IdAndProducto_Id(rq.getIdSucursal(), item.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No hay inventario para el producto " + producto.getNombre() + " en la sucursal"));

            int cantidad = item.getCantidad();
            if (inventario.getStock() < cantidad) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para el producto " + producto.getNombre());
            }

            // Descontar stock
            inventario.setStock(inventario.getStock() - cantidad);
            inventarioRepository.save(inventario);

            // Calcular subtotal
            BigDecimal precioUnit = item.getPrecioUnit();
            BigDecimal subtotal = precioUnit.multiply(BigDecimal.valueOf(cantidad));

            // Crear detalle
            DetalleVenta detalle = DetalleVenta.builder()
                    .venta(venta)
                    .producto(producto)
                    .cantidad(cantidad)
                    .precioUnit(precioUnit)
                    .build();  // subtotal lo calcula MySQL (STORED GENERATED) si lo tienes así

            detalleVentaRepository.save(detalle);

            total = total.add(subtotal);
        }

        // Setear total y (opcional) producto "principal" de la venta
        venta.setTotal(total);
        if (primerProducto != null) {
            // si tu entity Venta todavía tiene el campo producto, lo rellenamos con el primero
            venta.setProducto(primerProducto);
        }

        venta = ventaRepository.save(venta);

        return mapToRs(venta);
    }

    @Override
    @Transactional
    public List<VentaRs> listar() {
        return ventaRepository.findAllWithRelations()
                .stream()
                .map(this::mapToRs)
                .toList();
    }

    @Override
    @Transactional
    public VentaRs actualizar(Long idVenta, VentaActualizarRq rq) {
        Venta venta = Optional.ofNullable(
                ventaRepository.findVentaWithRelations(idVenta)
        ).orElseThrow(() -> new IllegalArgumentException("La venta indicada no existe"));

        // Validar sucursal
        Sucursal sucursal = sucursalRepository.findById(rq.getIdSucursal())
                .orElseThrow(() -> new IllegalArgumentException("La sucursal indicada no existe"));

        // Validar cliente
        Cliente cliente = clienteRepository.findById(rq.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("El cliente indicado no existe"));

        // Validar empleado
        Empleado empleado = empleadoRepository.findById(rq.getIdEmpleado())
                .orElseThrow(() -> new IllegalArgumentException("El empleado indicado no existe"));

        // Actualizar cabecera
        venta.setSucursal(sucursal);
        venta.setCliente(cliente);
        venta.setEmpleado(empleado);

        // Opcional: recalcular total desde los detalles (más consistente)
        BigDecimal total = detalleVentaRepository.findByVenta_Id(idVenta)
                .stream()
                .map(DetalleVenta::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        venta.setTotal(total);

        ventaRepository.save(venta);

        Venta actualizado = ventaRepository.findVentaWithRelations(idVenta);
        return mapToRs(actualizado);
    }

    // ================== MAPPER ==================

    private VentaRs mapToRs(Venta v) {
        // Nombre cliente (nombre + apellido si existe)
        String clienteNombre = v.getCliente().getNombre();
        if (v.getCliente().getApellidoPaterno() != null) {
            clienteNombre += " " + v.getCliente().getApellidoPaterno();
        }

        // Nombre empleado (primerNombre + apellido si existe)
        String empleadoNombre = v.getEmpleado().getPrimerNombre();
        if (v.getEmpleado().getApellidoPaterno() != null) {
            empleadoNombre += " " + v.getEmpleado().getApellidoPaterno();
        }

        // Producto "principal" solo para mostrar algo en la tabla.
        String productoNombre = "(sin productos)";
        if (v.getProducto() != null) {
            productoNombre = v.getProducto().getNombre();
        }

        return VentaRs.builder()
                .id(v.getId())
                .fecha(v.getFecha())
                .sucursalNombre(v.getSucursal().getNombre())
                .clienteNombre(clienteNombre)
                .empleadoNombre(empleadoNombre)
                .productoNombre(productoNombre)
                .total(v.getTotal())
                .build();
    }

    }





