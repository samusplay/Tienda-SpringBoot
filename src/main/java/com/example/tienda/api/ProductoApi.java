package com.example.tienda.api;

import com.example.tienda.models.ProductoRq;
import com.example.tienda.models.ProductoRs;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.awt.*;

@RequestMapping(path = "/producto",
        produces = MediaType.APPLICATION_JSON_VALUE)
public interface ProductoApi {
    //Definimos el contrato explicitamente
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
     ProductoRs crear(@Valid @RequestBody ProductoRq rq);
}
