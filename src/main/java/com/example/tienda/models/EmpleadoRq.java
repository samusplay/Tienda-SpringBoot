package com.example.tienda.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoRq {
    // Validarores para nuestro Rq
    @NotBlank(message = "El primer nombre es obligatorio")
    @Size(max = 60, message = "El primer nombre no puede tener más de 60 caracteres")
    private String primerNombre;

    @Size(max = 60, message = "El segundo nombre no puede tener más de 60 caracteres")
    private String segundoNombre;

    @Size(max = 60, message = "El apellido paterno no puede tener más de 60 caracteres")
    private String apellidoPaterno;

    @Size(max = 60, message = "El apellido materno no puede tener más de 60 caracteres")
    private String apellidoMaterno;

    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @NotNull(message = "Debe indicar la sucursal del empleado")
    private Long idSucursal;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no es válido")
    private String email;

    private Boolean activo;
}
