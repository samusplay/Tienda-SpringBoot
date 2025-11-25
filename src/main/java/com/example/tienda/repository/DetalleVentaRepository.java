package com.example.tienda.repository;

import com.example.tienda.entity.DetalleVenta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetalleVentaRepository {
    // Para obtener los detalles de una venta por su id
    List<DetalleVenta> findByVentaId(Long idVenta);

    // Versión con JOIN FETCH para evitar LazyInitializationException en reportes
    @Query("""
           SELECT dv
           FROM DetalleVenta dv
           JOIN FETCH dv.venta v
           JOIN FETCH dv.producto p
           WHERE v.id = :idVenta
           """)
    List<DetalleVenta> findByVentaWithRelations(@Param("idVenta") Long idVenta);
}
