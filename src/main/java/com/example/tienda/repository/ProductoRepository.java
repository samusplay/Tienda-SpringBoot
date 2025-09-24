package com.example.tienda.repository;

import com.example.tienda.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    //CRUD
    //Buscar un producto por sku
    Optional<Producto>findBySku(String sku);

    //Valida el sku si es unico
    boolean  existsBySku(String sku);
}
