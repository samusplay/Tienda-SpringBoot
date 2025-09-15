package com.example.tienda.models;

import com.example.tienda.entity.Sucursal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SucursalRs {
    private Long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String telefono;
    private Boolean activo;
    private LocalDateTime createdAt;

    //Mapeo desde la indentidad
    public static SucursalRs of(Sucursal s) {
        //devuelve un objeto
        return SucursalRs.builder()
                .id(s.getId())
                .nombre(s.getNombre())
                .direccion(s.getDireccion())
                .ciudad(s.getCiudad())
                .telefono(s.getTelefono())
                .activo(s.getActivo())
                .createdAt(s.getCreatedAt())
                .build();
    }
    public static List<SucursalRs> of(List<Sucursal> sucursales) {
        return sucursales.stream().map(SucursalRs::of).toList();
    }

}
