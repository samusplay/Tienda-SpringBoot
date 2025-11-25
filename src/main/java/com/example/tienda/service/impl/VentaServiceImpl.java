package com.example.tienda.service.impl;

import com.example.tienda.entity.*;
import com.example.tienda.models.VentaActualizarRq;
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
    @Override
    public VentaRs crear(VentaRq rq) {
        // Validar sucursal
        Sucursal sucursal = sucursalRepository.findById(rq.getIdSucursal())
                .orElseThrow(() -> new IllegalArgumentException("La sucursal indicada no existe"));

        // Validar cliente
        Cliente cliente = clienteRepository.findById(rq.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("El cliente indicado no existe"));

        // Validar empleado
        Empleado empleado = empleadoRepository.findById(rq.getIdEmpleado())
                .orElseThrow(() -> new IllegalArgumentException("El empleado indicado no existe"));

        // Validar producto + importante para luego validar en el builder
        Producto producto = productoRepository.findById(rq.getIdProducto())
                .orElseThrow(() -> new IllegalArgumentException("El producto indicado no existe"));

        // Validar total > 0
        BigDecimal total = rq.getTotal();
        if (total == null || total.signum() <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a cero");
        }
        Inventario inventario = inventarioRepository
                .findBySucursal_IdAndProducto_Id(rq.getIdSucursal(), rq.getIdProducto())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No hay inventario para este producto en la sucursal"));

        // Por ahora asumimos cantidad = 1 por venta
        int cantidadVenta = 1;

        // Verificar stock suficiente
        if (inventario.getStock() < cantidadVenta) {
            throw new IllegalArgumentException("Stock insuficiente para el producto");
        }

        // Descontar stock
        inventario.setStock(inventario.getStock() - cantidadVenta);
        inventarioRepository.save(inventario);

        // Construimos la venta
        Venta venta = Venta.builder()
                .sucursal(sucursal)
                .cliente(cliente)
                .empleado(empleado)
                .producto(producto)   // <-- aquí ya NO se usa new Producto()
                .total(total)
                .build();

        Venta guardada = ventaRepository.save(venta);

        return mapToRs(guardada);
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

        // Validar que la sucursal exista
        Sucursal sucursal = sucursalRepository.findById(rq.getIdSucursal())
                .orElseThrow(() -> new IllegalArgumentException("La sucursal indicada no existe"));

        // Validar que el cliente exista
        Cliente cliente = clienteRepository.findById(rq.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("El cliente indicado no existe"));

        // Validar que el empleado exista
        Empleado empleado = empleadoRepository.findById(rq.getIdEmpleado())
                .orElseThrow(() -> new IllegalArgumentException("El empleado indicado no existe"));

        // Validar que el producto exista
        Producto producto = productoRepository.findById(rq.getIdProducto())
                .orElseThrow(() -> new IllegalArgumentException("El producto indicado no existe"));

        // Validar monto
        BigDecimal total = rq.getTotal();
        if (total == null || total.signum() <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a cero");
        }

        // Actualizar valores
        venta.setSucursal(sucursal);
        venta.setCliente(cliente);
        venta.setEmpleado(empleado);
        venta.setProducto(producto);   // <-- nuevo
        venta.setTotal(total);

        ventaRepository.save(venta);

        Venta actualizado = ventaRepository.findVentaWithRelations(idVenta);

        // Construimos la respuesta (o simplemente return mapToRs(actualizado);)
        return mapToRs(actualizado);
    }

    //Metodos privados
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

        return VentaRs.builder()
                .id(v.getId())
                .fecha(v.getFecha())
                .sucursalNombre(v.getSucursal().getNombre())
                .clienteNombre(clienteNombre)
                .empleadoNombre(empleadoNombre)
                .productoNombre(v.getProducto().getNombre())
                .total(v.getTotal())
                .build();
    }
}
