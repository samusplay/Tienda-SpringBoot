package com.example.tienda.api;

import com.example.tienda.models.SucursalRs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/sucursal")
public interface SucursalApi {
    @GetMapping("/listar")//Segunda base del path
    ResponseEntity<List<SucursalRs>> listar(
            @RequestParam(name = "soloActivas", defaultValue = "true") boolean soloActivas);

}
