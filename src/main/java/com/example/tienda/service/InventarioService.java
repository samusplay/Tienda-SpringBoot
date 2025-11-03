package com.example.tienda.service;

import com.example.tienda.models.InventarioActualizarRq;
import com.example.tienda.models.InventarioRq;
import com.example.tienda.models.InventarioRs;

import java.util.List;

public interface InventarioService {
    //Firmamos el servicio
    InventarioRs crear(InventarioRq rq);

    //firmamos el servicio de listar Inventarios
    List<InventarioRs>listar(Long idSucursal);

    //Firma de actualizar inventarios
    InventarioRs actualizar(InventarioActualizarRq rq);





}
