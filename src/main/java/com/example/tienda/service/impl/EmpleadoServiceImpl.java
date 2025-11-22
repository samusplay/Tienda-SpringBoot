package com.example.tienda.service.impl;

import com.example.tienda.entity.Empleado;
import com.example.tienda.entity.Sucursal;
import com.example.tienda.models.EmpleadoRq;
import com.example.tienda.models.EmpleadoRs;
import com.example.tienda.repository.EmpleadoRepository;
import com.example.tienda.repository.SucursalRepository;
import com.example.tienda.service.EmpleadoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {
    //Inyectar
    private final EmpleadoRepository empleadoRepository;
    private final SucursalRepository sucursalRepository;

    @Override
    public EmpleadoRs crear(EmpleadoRq rq) {
        // ========== VALIDACIONES ==========
        if (rq.getPrimerNombre() == null || rq.getPrimerNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El primer nombre es obligatorio");
        }

        if (rq.getCargo() == null || rq.getCargo().trim().isEmpty()) {
            throw new IllegalArgumentException("El cargo es obligatorio");
        }

        if (rq.getEmail() == null || rq.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio");
        }

        if (rq.getIdSucursal() == null) {
            throw new IllegalArgumentException("Debe indicar la sucursal del empleado");
        }

        // Validar formato email
        String correo = rq.getEmail().trim().toLowerCase();
        if (!correo.contains("@") || !correo.contains(".")) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido");
        }

        // Validar cargo permitido
        String cargo = rq.getCargo().trim().toUpperCase();
        List<String> cargosValidos = List.of("CAJERO", "BODEGA", "GERENTE", "ADMIN");

        if (!cargosValidos.contains(cargo)) {
            throw new IllegalArgumentException("El cargo indicado no es válido");
        }

        // Validar sucursal
        Sucursal sucursal = sucursalRepository.findById(rq.getIdSucursal())
                .orElseThrow(() -> new IllegalArgumentException("La sucursal indicada no existe"));

        // Validar email único
        if (empleadoRepository.existsByEmail(correo)) {
            throw new IllegalArgumentException("Ya existe un empleado con ese correo electrónico");
        }


        // ========== GUARDAR EN BASE DE DATOS ==========
        Empleado empleado = Empleado.builder()
                .primerNombre(rq.getPrimerNombre().trim())
                .segundoNombre(rq.getSegundoNombre() != null ? rq.getSegundoNombre().trim() : null)
                .apellidoPaterno(rq.getApellidoPaterno() != null ? rq.getApellidoPaterno().trim() : null)
                .apellidoMaterno(rq.getApellidoMaterno() != null ? rq.getApellidoMaterno().trim() : null)
                .cargo(cargo)
                .sucursal(sucursal)
                .email(correo)
                .activo(rq.getActivo() != null ? rq.getActivo() : true)
                .build();

        Empleado guardado = empleadoRepository.save(empleado);


        // ========== RESPUESTA ==========
        return EmpleadoRs.builder()
                .id(guardado.getId())
                .primerNombre(guardado.getPrimerNombre())
                .segundoNombre(guardado.getSegundoNombre())
                .apellidoPaterno(guardado.getApellidoPaterno())
                .apellidoMaterno(guardado.getApellidoMaterno())
                .cargo(guardado.getCargo())
                .sucursalNombre(guardado.getSucursal().getNombre())
                .email(guardado.getEmail())
                .activo(guardado.getActivo())
                .createdAt(guardado.getCreatedAt())
                .build();
    }

    @Override
    public List<EmpleadoRs> listar() {
        //Listamos todos los Empleados
        return empleadoRepository.findAllWithSucursal()   //Carga lazy
                .stream()
                .map(e -> EmpleadoRs.builder()
                        .id(e.getId())
                        .primerNombre(e.getPrimerNombre())
                        .segundoNombre(e.getSegundoNombre())
                        .apellidoPaterno(e.getApellidoPaterno())
                        .apellidoMaterno(e.getApellidoMaterno())
                        .cargo(e.getCargo())
                        .sucursalNombre(e.getSucursal() != null ? e.getSucursal().getNombre() : null)
                        .email(e.getEmail())
                        .activo(e.getActivo())
                        .createdAt(e.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public EmpleadoRs actualizar(Long idEmpleado, EmpleadoRq rq) {
        // ===== VALIDACIONES =====

        if (rq.getPrimerNombre() == null || rq.getPrimerNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El primer nombre es obligatorio");
        }

        if (rq.getCargo() == null || rq.getCargo().trim().isEmpty()) {
            throw new IllegalArgumentException("El cargo es obligatorio");
        }

        if (rq.getEmail() == null || rq.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio");
        }

        if (rq.getIdSucursal() == null) {
            throw new IllegalArgumentException("Debe indicar la sucursal del empleado");
        }

        // Validar formato del correo
        String nuevoCorreo = rq.getEmail().trim().toLowerCase();
        if (!nuevoCorreo.contains("@") || !nuevoCorreo.contains(".")) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido");
        }

        // Validar cargo permitido
        String cargo = rq.getCargo().trim().toUpperCase();
        List<String> cargosValidos = List.of("CAJERO", "BODEGA", "GERENTE", "ADMIN");

        if (!cargosValidos.contains(cargo)) {
            throw new IllegalArgumentException("El cargo indicado no es válido");
        }

        // Buscar empleado por ID
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new IllegalArgumentException("El empleado indicado no existe"));

        // Validar correo único si cambia
        if (!nuevoCorreo.equals(empleado.getEmail()) &&
                empleadoRepository.existsByEmail(nuevoCorreo)) {
            throw new IllegalArgumentException("Ese correo ya está en uso por otro empleado");
        }

        // Validar sucursal existente
        Sucursal sucursal = sucursalRepository.findById(rq.getIdSucursal())
                .orElseThrow(() -> new IllegalArgumentException("La sucursal indicada no existe"));

        // ===== ACTUALIZAR CAMPOS =====

        empleado.setPrimerNombre(rq.getPrimerNombre().trim());
        empleado.setSegundoNombre(rq.getSegundoNombre() != null ? rq.getSegundoNombre().trim() : null);
        empleado.setApellidoPaterno(rq.getApellidoPaterno());
        empleado.setApellidoMaterno(rq.getApellidoMaterno());
        empleado.setCargo(cargo);
        empleado.setSucursal(sucursal);
        empleado.setEmail(nuevoCorreo);
        empleado.setActivo(rq.getActivo());
        //Guardamos
        empleadoRepository.save(empleado);

        // Volvemos cargar
        Empleado actualizado = empleadoRepository.findByIdWithSucursal(idEmpleado);

        // ===== RESPUESTA =====

        return EmpleadoRs.builder()
                .id(actualizado.getId())
                .primerNombre(actualizado.getPrimerNombre())
                .segundoNombre(actualizado.getSegundoNombre())
                .apellidoPaterno(actualizado.getApellidoPaterno())
                .apellidoMaterno(actualizado.getApellidoMaterno())
                .cargo(actualizado.getCargo())
                .sucursalNombre(actualizado.getSucursal().getNombre())
                .email(actualizado.getEmail())
                .activo(actualizado.getActivo())
                .createdAt(actualizado.getCreatedAt())
                .build();
    }
}
