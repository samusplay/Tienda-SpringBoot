package com.example.tienda.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//Respuesta a la hora de Crear el producto
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoRs {

    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String sku;
    private Boolean activo;
    private LocalDateTime createdAt;
    private String sucursal;
}
