package com.example.tienda.service.impl;

import com.example.tienda.models.DetalleVentaReporteRs;
import com.example.tienda.models.DetalleVentaRq;
import com.example.tienda.models.DetalleVentaRs;
import com.example.tienda.service.DetalleVentaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class DetalleVentaServiceImpl implements DetalleVentaService {
    @Override
    public DetalleVentaRs crear(DetalleVentaRq rq) {
        return null;
    }

    @Override
    public List<DetalleVentaRs> listarPorVenta(Long idVenta) {
        return List.of();
    }

    @Override
    public DetalleVentaReporteRs generarReporteVenta(Long idVenta) {
        return null;
    }

    @Override
    public void enviarReporteVentaPorEmail(Long idVenta, String correoDestino) {

    }
}
