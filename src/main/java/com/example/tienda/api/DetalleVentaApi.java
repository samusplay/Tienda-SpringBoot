package com.example.tienda.api;

import com.example.tienda.models.DetalleVentaRs;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping(path = "/detalle-venta", produces = MediaType.APPLICATION_JSON_VALUE)
public interface DetalleVentaApi {

    // Listar productos de una venta
    @GetMapping("/por-venta/{idVenta}")
    @ResponseStatus(HttpStatus.OK)
    List<DetalleVentaRs> listarPorVenta(@PathVariable Long idVenta);
}
