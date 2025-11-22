package com.example.tienda.repository;

import com.example.tienda.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {
    //Querys
    //Si existe el Email
    boolean existsByEmail(String email);

    //para cargar la sucursal
    @Query("""
    SELECT e FROM Empleado e
    LEFT JOIN FETCH e.sucursal
""")
    List<Empleado> findAllWithSucursal();

    //Par hacer join a sucursal con empleado
    @Query("""
    SELECT e FROM Empleado e
    JOIN FETCH e.sucursal
    WHERE e.id = :id
""")
    Empleado findByIdWithSucursal(Long id);

}
