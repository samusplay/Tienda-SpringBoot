package com.example.tienda.repository;

import com.example.tienda.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SucursalRepository extends JpaRepository<Sucursal,Long> {
    List<Sucursal> findByActivoTrueOrderByNombreAsc();
}
