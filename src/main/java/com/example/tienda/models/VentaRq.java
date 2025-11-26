package com.example.tienda.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaRq {
    @NotNull(message = "La sucursal es obligatoria")
    private Long idSucursal;

    @NotNull(message = "El cliente es obligatorio")
    private Long idCliente;

    @NotNull(message = "El empleado es obligatorio")
    private Long idEmpleado;


    @NotEmpty(message = "Debe agregar al menos un producto a la venta")
    private List<VentaItemRq> items;
}
