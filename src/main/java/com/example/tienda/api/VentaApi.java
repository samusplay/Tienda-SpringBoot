package com.example.tienda.api;

import com.example.tienda.models.VentaActualizarRq;
import com.example.tienda.models.VentaRq;
import com.example.tienda.models.VentaRs;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/venta", produces = MediaType.APPLICATION_JSON_VALUE)
public interface VentaApi {
    //Endpoints

    // Crear venta
    @PostMapping(path = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    VentaRs crear(@Valid @RequestBody VentaRq rq);

    // Listar ventas
    @GetMapping("/listar")
    List<VentaRs> listar();

    //Actualizar Ventas
    @PostMapping(path = "/actualizar/{idVenta}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    VentaRs actualizar(@PathVariable Long idVenta, @Valid @RequestBody VentaActualizarRq rq);
}
