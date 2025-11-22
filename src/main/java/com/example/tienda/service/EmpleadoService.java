package com.example.tienda.service;

import com.example.tienda.models.EmpleadoRq;
import com.example.tienda.models.EmpleadoRs;

import java.util.List;

public interface EmpleadoService {
    //Firma de funcionalidades
    //Crear Empleados
    EmpleadoRs crear(EmpleadoRq rq);

    //Listar empleados
    List<EmpleadoRs> listar();

    //Actualizar Empleados
    EmpleadoRs actualizar(Long idEmpleado, EmpleadoRq rq);
}
