package com.example.tienda.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoRq {

    private String nombre;

    private BigDecimal precio;

    private String sku;

    private Boolean activo;

    private String sucursal; //nombre que se bsuca en el repo
}
