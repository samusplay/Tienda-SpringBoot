package com.example.tienda.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaActualizarRq {

    @NotNull(message = "Debe indicar la sucursal")
    private Long idSucursal;

    @NotNull(message = "Debe indicar el cliente")
    private Long idCliente;

    @NotNull(message = "Debe indicar el empleado")
    private Long idEmpleado;

    @NotNull(message = "El total no puede estar vacío")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0")
    private BigDecimal total;
}
