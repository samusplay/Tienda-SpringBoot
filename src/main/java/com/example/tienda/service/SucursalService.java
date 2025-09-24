package com.example.tienda.service;

import com.example.tienda.entity.Sucursal;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SucursalService {
    //funcionalidades
    List<Sucursal>listar(boolean soloActivas);
}
