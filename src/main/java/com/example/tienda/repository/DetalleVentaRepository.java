package com.example.tienda.repository;

import com.example.tienda.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta,Long> {
    // Obtener detalles de una venta por su id (usando la FK venta.id)
    List<DetalleVenta> findByVenta_Id(Long idVenta);

    // Versión con JOIN FETCH para reportes
    @Query("""
           SELECT dv
           FROM DetalleVenta dv
           JOIN FETCH dv.venta v
           JOIN FETCH dv.producto p
           WHERE v.id = :idVenta
           """)
    List<DetalleVenta> findByVentaWithRelations(@Param("idVenta") Long idVenta);
}
