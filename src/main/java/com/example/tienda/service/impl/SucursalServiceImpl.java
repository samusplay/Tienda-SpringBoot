package com.example.tienda.service.impl;

import com.example.tienda.entity.Sucursal;
import com.example.tienda.models.SucursalRq;
import com.example.tienda.models.SucursalRs;
import com.example.tienda.repository.SucursalRepository;
import com.example.tienda.service.SucursalService;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service

@AllArgsConstructor

public class SucursalServiceImpl implements SucursalService {

    private final SucursalRepository sucursalRepository;

    @Override
    public List<Sucursal> listar(boolean soloActivas) {
        if(soloActivas){
            return sucursalRepository.findByActivoTrueOrderByNombreAsc();
        }
        return sucursalRepository.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
    }

    @Override
    public SucursalRs crear(SucursalRq rq) {

        //validar que los datos no vengan vacios
        if(rq==null){
            throw new IllegalArgumentException("Los datos enviados son invalidos");
        }
        //Nombre Duplicado
        if (sucursalRepository.existsByNombreIgnoreCaseAndCiudadIgnoreCase(
                rq.getNombre().trim(),
                rq.getCiudad().trim()
        )) {
            throw new IllegalArgumentException("Ya existe una sucursal con ese nombre en esta ciudad.");
        }
        //Normalizar datos
        String nombre=rq.getNombre().trim().toUpperCase();
        String dirrecion=rq.getDireccion().trim();
        String ciudad=rq.getCiudad().trim();
        String telefono=rq.getTelefono().trim();

        //Si activo viene null
        Boolean activo=rq.getActivo() !=null ? rq.getActivo():true;

        //Creamos la entidad
        Sucursal s=new Sucursal();
        s.setNombre(nombre);
        s.setDireccion(dirrecion);
        s.setCiudad(ciudad);
        s.setTelefono(telefono);
        s.setActivo(activo);
        s.setCreatedAt(LocalDateTime.now());

        //Guardamos en la base de datos
        Sucursal guardar=sucursalRepository.save(s);

        //Devolvemos la respuesta
        return SucursalRs.of(guardar);
    }

    @Override
    public SucursalRs actualizar(Long idSucursal, SucursalRq rq) {
        //Validar que exista la sucursal
        Sucursal s=sucursalRepository.findById(idSucursal)
                .orElseThrow(()->new IllegalArgumentException("No existe la sucursal con el ID"+idSucursal));

        //Normalizar y validar si se envia ese nombre
        if(rq.getNombre() !=null && !rq.getNombre().trim().isEmpty()){
            String nuevoNombre=rq.getNombre().trim().toUpperCase();
            //validar duplicado
            if (!nuevoNombre.equalsIgnoreCase(s.getNombre()) &&
                    sucursalRepository.existsByNombreIgnoreCase(nuevoNombre)) {
                throw new IllegalArgumentException("Ya existe otra sucursal con ese nombre.");
            }
            s.setNombre(nuevoNombre);
        }
        //Actualizar otros campos enviados
        if(rq.getDireccion() !=null){
            s.setDireccion(rq.getDireccion().trim());
        }
        if(rq.getCiudad() !=null){
            s.setCiudad(rq.getCiudad().trim());
        }
        if(rq.getTelefono() !=null){
            s.setTelefono(rq.getTelefono().trim());
        }
        if(rq.getActivo() !=null){
            s.setActivo(rq.getActivo());
        }
        //Guardamos cambios
        Sucursal actualizada=sucursalRepository.save(s);

        //Devolver respuesta
        return SucursalRs.of(actualizada);


    }
}
