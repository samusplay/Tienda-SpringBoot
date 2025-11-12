package com.example.tienda.api;

import com.example.tienda.models.ClienteRq;
import com.example.tienda.models.ClienteRs;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/cliente", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ClienteApi {
    //API crear un cliente
    @PostMapping(path = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ClienteRs crear(@Valid @RequestBody ClienteRq rq);

    //Listar cliente
    @GetMapping("/listar")
    List<ClienteRs> listar();

    //Actualizar Cliente
    @PostMapping(path = "/actualizar/{idCliente}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ClienteRs actualizar(@PathVariable Long idCliente, @Valid @RequestBody ClienteRq rq);

}
