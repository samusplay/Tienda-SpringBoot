package com.example.tienda.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    @NotNull(message = "El producto es obligatorio")
    private Long idProducto;

    @NotNull(message = "El total de la venta es obligatorio")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a cero")
    private BigDecimal total;
}
