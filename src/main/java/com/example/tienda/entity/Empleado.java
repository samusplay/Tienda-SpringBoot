package com.example.tienda.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Long id;

    @Column(name = "primer_nombre", nullable = false, length = 60)
    private String primerNombre;

    @Column(name = "segundo_nombre", length = 60)
    private String segundoNombre;

    @Column(name = "apellido_paterno", length = 60)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 60)
    private String apellidoMaterno;

    @Column(name = "cargo", nullable = false)
    private String cargo;

    //Relacion con ID sucursal
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal;

    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

}
