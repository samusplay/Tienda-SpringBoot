package com.example.tienda.repository;

import com.example.tienda.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SucursalRepository extends JpaRepository<Sucursal,Long> {
    List<Sucursal> findByActivoTrueOrderByNombreAsc();

    //Creamos un metodo persoalizable para hacer crud
     boolean existsByNombreIgnoreCase(String nombre);
    Optional<Sucursal>findByNombreIgnoreCase(String nombre);

    //Para tener el mismo nombre de la sucursal pero diferente ciudad
    boolean existsByNombreIgnoreCaseAndCiudadIgnoreCase(String nombre, String ciudad);

}
