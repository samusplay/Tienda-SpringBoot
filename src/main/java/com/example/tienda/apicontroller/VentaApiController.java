package com.example.tienda.apicontroller;

import com.example.tienda.api.VentaApi;
import com.example.tienda.models.VentaActualizarRq;
import com.example.tienda.models.VentaRq;
import com.example.tienda.models.VentaRs;
import com.example.tienda.service.VentaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class VentaApiController implements VentaApi {
    private final VentaService ventaService;

    @Override
    public VentaRs crear(VentaRq rq) {
        return ventaService.crear(rq);
    }

    @Override
    public List<VentaRs> listar() {
        return ventaService.listar();
    }

    @Override
    public VentaRs actualizar(Long idVenta, VentaActualizarRq rq) {
        return ventaService.actualizar(idVenta, rq);
    }
}
