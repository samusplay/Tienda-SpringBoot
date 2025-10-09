package com.example.tienda.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Respuesta que va consumir angular
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioRq {
    private Long idSucursal;
    private Long idProducto;
    private Integer stock;
}
