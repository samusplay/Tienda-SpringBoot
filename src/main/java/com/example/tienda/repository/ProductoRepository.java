package com.example.tienda.repository;

import com.example.tienda.entity.Producto;
import com.example.tienda.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    //CRUD
    //Buscar un producto por sku
    Optional<Producto>findBySku(String sku);

    //Valida el sku si es unico
    boolean  existsBySku(String sku);

    //Buscar la sucursal
    boolean existsByNombreIgnoreCase(String nombre);

    Optional<Sucursal> findByNombreIgnoreCase(String nombre);

    // 👉 Para listar productos cargando también la sucursal (evitar LazyInitializationException)
    @Query("""
           SELECT p
           FROM Producto p
           JOIN FETCH p.sucursal
           """)
    List<Producto> findAllWithSucursal();
}
