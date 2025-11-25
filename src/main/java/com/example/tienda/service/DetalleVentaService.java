package com.example.tienda.service;

import com.example.tienda.models.DetalleVentaReporteRs;
import com.example.tienda.models.DetalleVentaRq;
import com.example.tienda.models.DetalleVentaRs;

import java.util.List;

public interface DetalleVentaService {
    // 1) Crear un detalle de venta (cuando guardes los productos de la venta)
    DetalleVentaRs crear(DetalleVentaRq rq);

    // 2) Listar los detalles de una venta específica
    List<DetalleVentaRs> listarPorVenta(Long idVenta);

    // 3) Generar el DTO de reporte (lo usaremos para PDF/Excel o para mandar por correo)
    DetalleVentaReporteRs generarReporteVenta(Long idVenta);

    // 4) Opción directa: generar el reporte y enviarlo por correo
    void enviarReporteVentaPorEmail(Long idVenta, String correoDestino);
}
