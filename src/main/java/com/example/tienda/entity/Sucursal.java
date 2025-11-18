package com.example.tienda.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="sucursal")
@Builder
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "direccion", length = 150)
    private String direccion;

    @Column(name = "ciudad", length = 80)
    private String ciudad;

    @Column(name = "telefono", length = 30)
    private String telefono;

    // tinyint(1) -> Boolean está OK
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    // lo genera MySQL con DEFAULT CURRENT_TIMESTAMP
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }


}
