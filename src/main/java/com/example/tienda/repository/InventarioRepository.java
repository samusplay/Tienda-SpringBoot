package com.example.tienda.repository;

import com.example.tienda.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario,Long> {
    boolean existsBySucursal_NombreIgnoreCaseAndProducto_Sku(String nombreSucursal, String sku);

    //Listar todas las sucursalers usando el id del sucursal
    List<Inventario>findAllBySucursal_Id(Long idSucursal);

    //Filtros para buscar por nombre de sucursal
    List<Inventario> findAllBySucursal_NombreIgnoreCase(String nombreSucursal);

    //Filtros por sku
    List<Inventario>findAllByProducto_Sku(String sku);

}
