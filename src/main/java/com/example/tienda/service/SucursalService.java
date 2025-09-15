package com.example.tienda.service;

import com.example.tienda.entity.Sucursal;

import java.util.List;

public interface SucursalService {
    //funcionalidades
    List<Sucursal>listar(boolean soloActivas);
}
