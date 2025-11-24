package com.example.tienda.repository;

import com.example.tienda.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VentaRepository extends JpaRepository<Venta,Long> {

    // Para evitar LazyInitializationException al listar
    @Query("""
           SELECT v
           FROM Venta v
           JOIN FETCH v.sucursal
           JOIN FETCH v.cliente
           JOIN FETCH v.empleado
           """)
    List<Venta> findAllWithRelations();

    //conuslta paractualizar
    @Query("""
        SELECT v FROM Venta v
        JOIN FETCH v.sucursal
        JOIN FETCH v.cliente
        JOIN FETCH v.empleado
        WHERE v.id = :idVenta
    """)
    Venta findVentaWithRelations(@Param("idVenta") Long idVenta);
}
