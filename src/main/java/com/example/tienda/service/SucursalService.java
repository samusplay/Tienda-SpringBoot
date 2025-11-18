package com.example.tienda.service;

import com.example.tienda.entity.Sucursal;
import com.example.tienda.models.SucursalRq;
import com.example.tienda.models.SucursalRs;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SucursalService {
    //funcionalidades

    //Listar Sucursal
    List<Sucursal>listar(boolean soloActivas);

    //Crear Sucursal
    SucursalRs crear (SucursalRq rq);

    //Actualizar Sucursal
    SucursalRs actualizar(Long idSucursal, SucursalRq rq);
}
