package com.example.tienda.service.impl;

import com.example.tienda.entity.Cliente;
import com.example.tienda.entity.Empleado;
import com.example.tienda.entity.Sucursal;
import com.example.tienda.entity.Venta;
import com.example.tienda.models.VentaActualizarRq;
import com.example.tienda.models.VentaRq;
import com.example.tienda.models.VentaRs;
import com.example.tienda.repository.ClienteRepository;
import com.example.tienda.repository.EmpleadoRepository;
import com.example.tienda.repository.SucursalRepository;
import com.example.tienda.repository.VentaRepository;
import com.example.tienda.service.VentaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

        // Validar total > 0
        BigDecimal total = rq.getTotal();
        if (total == null || total.signum() <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a cero");
        }

        // Construimos la venta
        Venta venta = Venta.builder()
                .sucursal(sucursal)
                .cliente(cliente)
                .empleado(empleado)
                .total(total)
                .build();

        Venta guardada = ventaRepository.save(venta);

        return mapToRs(guardada);
    }

    @Override
    public List<VentaRs> listar() {
        return ventaRepository.findAllWithRelations()
                .stream()
                .map(this::mapToRs)
                .toList();
    }

    @Override
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

        // Validar monto
        if (rq.getTotal() == null || rq.getTotal().doubleValue() <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a cero");
        }

        // Actualizar valores
        venta.setSucursal(sucursal);
        venta.setCliente(cliente);
        venta.setEmpleado(empleado);
        venta.setTotal(rq.getTotal());
        //guardamos cambios
        ventaRepository.save(venta);

        Venta actualizado = ventaRepository.findVentaWithRelations(idVenta);


        //Construimos la respuesta
        return VentaRs.builder()
                .id(actualizado.getId())
                .fecha(actualizado.getFecha())
                .sucursalNombre(actualizado.getSucursal().getNombre())
                .clienteNombre(actualizado.getCliente().getNombre())
                .empleadoNombre(actualizado.getEmpleado().getPrimerNombre())
                .total(actualizado.getTotal())
                .build();
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
                .total(v.getTotal())
                .build();
    }
}
