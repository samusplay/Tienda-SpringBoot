package com.example.tienda.apicontroller;

import com.example.tienda.api.EmpleadoApi;
import com.example.tienda.models.EmpleadoRq;
import com.example.tienda.models.EmpleadoRs;
import com.example.tienda.service.EmpleadoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class EmpleadoApiController implements EmpleadoApi {
    private final EmpleadoService empleadoService;

    @Override
    public EmpleadoRs crear(EmpleadoRq rq) {
        return empleadoService.crear(rq);
    }

    @Override
    public List<EmpleadoRs> listar() {
        return empleadoService.listar();
    }

    @Override
    public EmpleadoRs actualizar(Long idEmpleado, EmpleadoRq rq) {
        return empleadoService.actualizar(idEmpleado, rq);
    }
}
