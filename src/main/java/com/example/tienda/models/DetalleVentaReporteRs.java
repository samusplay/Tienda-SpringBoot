package com.example.tienda.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaReporteRs {
    // Datos de la venta
    private Long idVenta;
    private LocalDateTime fechaVenta;
    private String sucursalNombre;
    private String clienteNombre;
    private String empleadoNombre;

    // Datos del producto / detalle
    private Long idDetalle;
    private Long idProducto;
    private String productoNombre;
    private Integer cantidad;
    private BigDecimal precioUnit;
    private BigDecimal subtotal;
}
