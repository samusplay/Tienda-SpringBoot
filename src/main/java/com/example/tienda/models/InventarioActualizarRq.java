package com.example.tienda.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioActualizarRq {
    private Long idInventario; //Registro que vamos a editar
    private Long idSucursal;
    private Long idProducto;
    private Integer stock;
}
