package com.example.tienda.service.impl;

import com.example.tienda.entity.Producto;
import com.example.tienda.models.ProductoRq;
import com.example.tienda.models.ProductoRs;
import com.example.tienda.repository.ProductoRepository;
import com.example.tienda.repository.SucursalRepository;
import com.example.tienda.service.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;

    @Override
    @Transactional
    public ProductoRs crear(ProductoRq rq) {

        //Validar si hay un sku duplicado
        if(rq.getSku() !=null&&productoRepository.existsBySku(rq.getSku())){
            throw new IllegalArgumentException("El sku ya existe");
        }
        // validar la sucursal por nombre
        if(rq.getSucursal() !=null&&!sucursalRepository.existsByNombreIgnoreCase(rq.getSucursal())){
            throw new IllegalArgumentException("la sucursal indicada no existe");
        }
        //Validar si esta en el inventario de la sucursal(luego)

        //mapeo directo sin mapper

        Producto p=new Producto();
        p.setNombre(rq.getNombre());
        p.setPrecio(rq.getPrecio());
        p.setSku(rq.getSku());
        p.setActivo(rq.getActivo() !=null ? rq.getActivo():true);

        Producto g=productoRepository.save(p);

        //Respuesta
        return ProductoRs.builder()
                .id(g.getId())
                .nombre(g.getNombre())
                .precio(g.getPrecio())
                .sku(g.getSku())
                .activo(g.getActivo())
                .createdAt(g.getCreatedAt())
                .build();
    }
}
