package com.example.tienda.service;

import com.example.tienda.models.VentaActualizarRq;
import com.example.tienda.models.VentaRq;
import com.example.tienda.models.VentaRs;

import java.util.List;

public interface VentaService {
    //Crear venta
    VentaRs crear(VentaRq rq);

    //Listar Venta
    List<VentaRs> listar();

    //Actualizar Venta
    VentaRs actualizar(Long idVenta, VentaActualizarRq rq);

}
