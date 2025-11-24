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
        List<String> cargosValidos = List.of("Gerente",
                "Subgerente",
                "Cajero",
                "Auxiliar de bodega",
                "Vendedor",
                "Supervisor",
                "Administrador");
        // comparamos ignorando mayúsculas/minúsculas
        boolean esValido = cargosValidos.stream()
                .anyMatch(c -> c.equalsIgnoreCase(cargo));

        if (!esValido) {
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
        // ===== VALIDACIONES BÁSICAS =====
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

        // ===== VALIDAR FORMATO CORREO =====
        String nuevoCorreo = rq.getEmail().trim().toLowerCase();
        if (!nuevoCorreo.contains("@") || !nuevoCorreo.contains(".")) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido");
        }

        // ===== VALIDAR CARGO PERMITIDO (lista nueva) =====
        // Validar cargo permitido
        String cargo = rq.getCargo() == null ? null : rq.getCargo().trim();

        List<String> cargosValidos = List.of(
                "Gerente",
                "Subgerente",
                "Cajero",
                "Auxiliar de bodega",
                "Vendedor",
                "Supervisor",
                "Administrador"
        );

    // comparamos ignorando mayúsculas/minúsculas
        boolean esValido = cargosValidos.stream()
                .anyMatch(c -> c.equalsIgnoreCase(cargo));

        if (!esValido) {
            throw new IllegalArgumentException("El cargo indicado no es válido");
        }


        // ===== BUSCAR EMPLEADO =====
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new IllegalArgumentException("El empleado indicado no existe"));

        // ===== VALIDAR CORREO ÚNICO SI CAMBIA =====
        if (!nuevoCorreo.equalsIgnoreCase(empleado.getEmail())
                && empleadoRepository.existsByEmail(nuevoCorreo)) {
            throw new IllegalArgumentException("Ese correo ya está en uso por otro empleado");
        }

        // ===== VALIDAR SUCURSAL =====
        Sucursal sucursal = sucursalRepository.findById(rq.getIdSucursal())
                .orElseThrow(() -> new IllegalArgumentException("La sucursal indicada no existe"));

        // ===== ACTUALIZAR CAMPOS =====
        empleado.setPrimerNombre(rq.getPrimerNombre().trim());
        empleado.setSegundoNombre(
                rq.getSegundoNombre() != null ? rq.getSegundoNombre().trim() : null
        );
        empleado.setApellidoPaterno(
                rq.getApellidoPaterno() != null ? rq.getApellidoPaterno().trim() : null
        );
        empleado.setApellidoMaterno(
                rq.getApellidoMaterno() != null ? rq.getApellidoMaterno().trim() : null
        );
        empleado.setCargo(cargo); // dejamos el texto tal cual lo envía el front
        empleado.setSucursal(sucursal);
        empleado.setEmail(nuevoCorreo);
        empleado.setActivo(rq.getActivo() != null ? rq.getActivo() : empleado.getActivo());

        empleadoRepository.save(empleado);

        // Si quieres recargar con fetch join:
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
