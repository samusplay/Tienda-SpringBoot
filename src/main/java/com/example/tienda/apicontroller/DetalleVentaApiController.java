package com.example.tienda.apicontroller;

import com.example.tienda.api.DetalleVentaApi;
import com.example.tienda.models.DetalleVentaRs;
import com.example.tienda.service.DetalleVentaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@AllArgsConstructor
public class DetalleVentaApiController implements DetalleVentaApi {

    private final DetalleVentaService detalleVentaService;

    @Override
    public List<DetalleVentaRs> listarPorVenta(Long idVenta) {
        return detalleVentaService.listarPorVenta(idVenta);
    }
}
