package com.example.tienda.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//Lo que Responde nuestro Backend
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRs {
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String correo;
    private LocalDateTime createdAt;
}
