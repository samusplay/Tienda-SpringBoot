package com.example.tienda.service.impl;

import com.example.tienda.entity.Inventario;
import com.example.tienda.models.InventarioRq;
import com.example.tienda.models.InventarioRs;
import com.example.tienda.repository.InventarioRepository;
import com.example.tienda.repository.ProductoRepository;
import com.example.tienda.repository.SucursalRepository;
import com.example.tienda.service.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventarioServiceImpl implements InventarioService {
//inyeccion
    private final InventarioRepository inventarioRepository;
    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public InventarioRs crear(InventarioRq rq) {
        //Validar que la sucursal exista
        var sucursal=sucursalRepository.findById(rq.getIdSucursal()).
                orElseThrow(()->new IllegalArgumentException("La Sucursal indicada no existe"));
        //validar que el producto exista
        var producto=productoRepository.findById(rq.getIdProducto()).
                orElseThrow(()->new IllegalArgumentException("El producto indicado no existe"));

        //Validar si el producto ya esta registrado en esta sucursal
        if (inventarioRepository.existsBySucursal_NombreIgnoreCaseAndProducto_Sku(
                sucursal.getNombre(), producto.getSku())) {
            throw new IllegalArgumentException(
                    "El producto ya está registrado en el inventario de esta sucursal");
        }

        //Crear una nueva instancia del inventario

        Inventario inventario=Inventario.builder()
                .sucursal(sucursal)
                .producto(producto)
                //Si el usuario no envia el stock
                .stock(rq.getStock() !=null ? rq.getStock():0)
                .build();
        //Guardar en la base de datos
        Inventario guardado=inventarioRepository.save(inventario);
        //Devolver Dto
        return InventarioRs.builder()
                .idInventario(guardado.getIdInventario())
                .idSucursal(guardado.getSucursal().getId())
                .idProducto(guardado.getProducto().getId())
                .sucursalNombre(guardado.getSucursal().getNombre())
                .productNombre(guardado.getProducto().getNombre())
                .stock(guardado.getStock())
                .build();
    }
}
