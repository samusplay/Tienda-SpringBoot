package com.example.tienda.service;

import com.example.tienda.models.InventarioRq;
import com.example.tienda.models.InventarioRs;

public interface InventarioService {
    //Firmamos el servicio
    InventarioRs crear(InventarioRq rq);
}
