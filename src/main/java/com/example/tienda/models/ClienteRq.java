package com.example.tienda.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Lo que ponemos en nuestra solicitud en postman
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRq {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "El nombre no puede tener más de 120 caracteres")
    private String nombre;

    @Size(max = 100, message = "El apellido paterno no puede tener más de 100 caracteres")
    private String apellidoPaterno;

    @Size(max = 100, message = "El apellido materno no puede tener más de 100 caracteres")
    private String apellidoMaterno;

    @Pattern(
            regexp = "^(\\+\\d{1,3}[- ]?)?\\d{7,15}$",
            message = "El número de teléfono no es válido"
    )
    @Size(max = 30, message = "El teléfono no puede tener más de 30 caracteres")
    private String telefono;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no tiene un formato válido")
    @Size(max = 120, message = "El correo no puede tener más de 120 caracteres")
    private String correo;
}

