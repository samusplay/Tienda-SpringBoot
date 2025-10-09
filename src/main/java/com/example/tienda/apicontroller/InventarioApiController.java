package com.example.tienda.apicontroller;

import com.example.tienda.api.InventarioApi;
import com.example.tienda.models.InventarioRq;
import com.example.tienda.models.InventarioRs;
import com.example.tienda.service.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InventarioApiController implements InventarioApi {

    private final InventarioService service;
    @Override
    public InventarioRs crear(InventarioRq rq) {
        return service.crear(rq);
    }
}
