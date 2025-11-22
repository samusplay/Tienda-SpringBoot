package com.example.tienda.api;

import com.example.tienda.models.EmpleadoRq;
import com.example.tienda.models.EmpleadoRs;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/empleado", produces = MediaType.APPLICATION_JSON_VALUE)
public interface EmpleadoApi {
    @PostMapping(path = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    EmpleadoRs crear(@Valid @RequestBody EmpleadoRq rq);

    @GetMapping("/listar")
    List<EmpleadoRs> listar();

    @PostMapping(path = "/actualizar/{idEmpleado}", consumes = MediaType.APPLICATION_JSON_VALUE)
    EmpleadoRs actualizar(@PathVariable Long idEmpleado,
                          @Valid @RequestBody EmpleadoRq rq);
}
