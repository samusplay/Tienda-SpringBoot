package com.example.tienda.api;

import com.example.tienda.models.SucursalRq;
import com.example.tienda.models.SucursalRs;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sucursal")
public interface SucursalApi {

    @GetMapping("/listar")//Segunda base del path
    ResponseEntity<List<SucursalRs>> listar(
            @RequestParam(name = "soloActivas", defaultValue = "true") boolean soloActivas);

    //Crear Sucursal
    @PostMapping("/crear")
    ResponseEntity<SucursalRs>crear(@Valid @RequestBody SucursalRq rq);

    //actualizar
    @PostMapping("/actualizar/{idSucursal}")
    ResponseEntity<SucursalRs>actualizar(@PathVariable Long idSucursal,@Valid@ RequestBody SucursalRq rq);

}
