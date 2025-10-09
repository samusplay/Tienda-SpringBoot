package com.example.tienda.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioRs {
    private Long idInventario;
    private Long idSucursal;
    private Long idProducto;
    private String sucursalNombre;
    private String productNombre;
    private Integer stock;

}
