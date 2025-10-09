package com.example.tienda.repository;

import com.example.tienda.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario,Long> {
    boolean existsBySucursal_NombreIgnoreCaseAndProducto_Sku(String nombreSucursal, String sku);
}
