package com.example.tienda.api;

import com.example.tienda.models.InventarioRq;
import com.example.tienda.models.InventarioRs;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/inventario")
public interface InventarioApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    InventarioRs crear(@RequestBody InventarioRq rq);

    //Listar inventarios
    @GetMapping("/listar")
    List<InventarioRs>listar(@RequestParam(name = "idSucursal",required = false)Long idSucursal);
}
