package com.example.tienda.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//lo que va responder el backend
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaRs {
    private Long id;
    private LocalDateTime fecha;
    private String sucursalNombre;
    private String clienteNombre;
    private String empleadoNombre;
    private BigDecimal total;
}
