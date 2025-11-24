package com.example.tienda.api;

import com.example.tienda.models.InventarioActualizarRq;
import com.example.tienda.models.InventarioRq;
import com.example.tienda.models.InventarioRs;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/inventario", produces = MediaType.APPLICATION_JSON_VALUE)
public interface
InventarioApi {

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    InventarioRs crear(@RequestBody InventarioRq rq);

    //Listar inventarios
    @GetMapping("/listar")
    List<InventarioRs>listar(@RequestParam(name = "idSucursal",required = false)Long idSucursal);

    @PostMapping("/actualizar")
    InventarioRs actualizar(@RequestBody InventarioActualizarRq rq);

    //Hacer eliminar
}
