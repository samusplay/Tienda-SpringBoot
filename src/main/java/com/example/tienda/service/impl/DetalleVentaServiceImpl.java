package com.example.tienda.service.impl;

import com.example.tienda.entity.DetalleVenta;
import com.example.tienda.models.DetalleVentaReporteRs;
import com.example.tienda.models.DetalleVentaRq;
import com.example.tienda.models.DetalleVentaRs;
import com.example.tienda.repository.DetalleVentaRepository;
import com.example.tienda.service.DetalleVentaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@AllArgsConstructor
public class DetalleVentaServiceImpl implements DetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;

    @Override
    @Transactional
    public DetalleVentaRs crear(DetalleVentaRq rq) {
      return null;
    }

    @Override
    public List<DetalleVentaRs> listarPorVenta(Long idVenta) {
        return detalleVentaRepository.findByVentaWithRelations(idVenta)
                .stream()
                .map(this::mapToRs)
                .toList();

    }

    @Override
    public DetalleVentaReporteRs generarReporteVenta(Long idVenta) {
        return null;
    }

    @Override
    public void enviarReporteVentaPorEmail(Long idVenta, String correoDestino) {

    }
    //metodos privados
    private DetalleVentaRs mapToRs(DetalleVenta dv) {
        return DetalleVentaRs.builder()
                .idDetalle(dv.getId())
                .idProducto(dv.getProducto().getId())
                .productoNombre(dv.getProducto().getNombre())
                .cantidad(dv.getCantidad())
                .precioUnit(dv.getPrecioUnit())
                .subtotal(dv.getSubtotal())
                .build();
    }
}
