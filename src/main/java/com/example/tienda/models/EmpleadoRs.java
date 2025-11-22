package com.example.tienda.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
//Lo que Responde Angular
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoRs {
    private Long id;
    private String primerNombre;
    private String segundoNombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String cargo;
    private String sucursalNombre;
    private String email;
    private Boolean activo;
    private LocalDateTime createdAt;
}
