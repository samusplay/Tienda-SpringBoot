package com.example.tienda.apicontroller;

import com.example.tienda.api.SucursalApi;
import com.example.tienda.models.SucursalRq;
import com.example.tienda.models.SucursalRs;
import com.example.tienda.service.SucursalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
public class SucursalApiController implements SucursalApi {
    private final SucursalService sucursalService;

    @Override
    public ResponseEntity<List<SucursalRs>> listar(boolean soloActivas) {
        var entidades = sucursalService.listar(soloActivas);
        return ResponseEntity.ok(SucursalRs.of(entidades));
    }

    @Override
    public ResponseEntity<SucursalRs> crear(@Valid @RequestBody SucursalRq rq) {
        var creada=sucursalService.crear(rq);
        return ResponseEntity.ok(creada);
    }

    @Override
    public ResponseEntity<SucursalRs> actualizar(Long idSucursal, SucursalRq rq) {
        var actualizada=sucursalService.actualizar(idSucursal,rq);
        return ResponseEntity.ok(actualizada);
    }
}
