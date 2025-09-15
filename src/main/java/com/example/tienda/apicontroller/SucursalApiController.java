package com.example.tienda.apicontroller;

import com.example.tienda.api.SucursalApi;
import com.example.tienda.models.SucursalRs;
import com.example.tienda.service.SucursalService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SucursalApiController implements SucursalApi {
    private final SucursalService sucursalService;

    @Override
    public ResponseEntity<List<SucursalRs>> listar(boolean soloActivas) {
        var entidades = sucursalService.listar(soloActivas);
        return ResponseEntity.ok(SucursalRs.of(entidades));
    }
}
