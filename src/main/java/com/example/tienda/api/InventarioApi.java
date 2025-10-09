package com.example.tienda.api;

import com.example.tienda.models.InventarioRq;
import com.example.tienda.models.InventarioRs;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("api/inventario")
public interface InventarioApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    InventarioRs crear(@RequestBody InventarioRq rq);
}
