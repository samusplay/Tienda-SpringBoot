package com.example.tienda.service.impl;

import com.example.tienda.entity.Sucursal;
import com.example.tienda.repository.SucursalRepository;
import com.example.tienda.service.SucursalService;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
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
}
