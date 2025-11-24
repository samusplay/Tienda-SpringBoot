package com.example.tienda.service.impl;

import com.example.tienda.entity.Inventario;
import com.example.tienda.models.InventarioActualizarRq;
import com.example.tienda.models.InventarioRq;
import com.example.tienda.models.InventarioRs;
import com.example.tienda.repository.InventarioRepository;
import com.example.tienda.repository.ProductoRepository;
import com.example.tienda.repository.SucursalRepository;
import com.example.tienda.service.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    @Transactional
    public List<InventarioRs> listar(Long idSucursal) {
        //Validamos que la sucursal exista
        List<Inventario>data;
        if(idSucursal !=null){
            boolean existe=sucursalRepository.existsById(idSucursal);
            if(!existe){
                throw  new IllegalArgumentException("La Sucursal indicada no existe");
            }
            data=inventarioRepository.findAllBySucursal_Id(idSucursal);
        }else{
            //Sin filtro
            data=inventarioRepository.findAll();
        }
        //Devolvemos el Mapeo de respuesta
        return data.stream()
                .map(this::toRs)
                .toList();

    }

    @Override
    @Transactional
    public InventarioRs actualizar(InventarioActualizarRq rq) {

        //Verificamos que el inventario exista
        var inventario=inventarioRepository.findById(rq.getIdInventario())
                .orElseThrow(()->new IllegalArgumentException("El inventario indicado no existe"));
        //verificar que la sucursal existe
        var sucursal=sucursalRepository.findById(rq.getIdSucursal())
                .orElseThrow(()->new IllegalArgumentException("La Sucursal indicada no existe"));

        //Verificar que el producto existe
        var producto=productoRepository.findById(rq.getIdProducto())
                .orElseThrow(()->new IllegalArgumentException("El producto no existe"));

        //Validar el stock
        if(rq.getStock()==null ||rq.getStock() <0){
            throw  new IllegalArgumentException("El stock debe ser mayor o igual a cero");
        }


        //Validar la restriccion de la base de datos
        boolean existeDuplicado = inventarioRepository
                .existsBySucursal_IdAndProducto_IdAndIdInventarioNot(
                        rq.getIdSucursal(), rq.getIdProducto(), rq.getIdInventario());

        if(existeDuplicado){
            throw new IllegalArgumentException("Ya existe ese producto en esa sucursal");

        }
        //Actualizamos los campos de la solicitud
        inventario.setSucursal(sucursal);
        inventario.setProducto(producto);
        inventario.setStock(rq.getStock());

        //Guardamos en la base de datos
        inventarioRepository.save(inventario);

        //Retornamos la respuesta

        return InventarioRs.builder()
                .idInventario(inventario.getIdInventario())
                .idSucursal(sucursal.getId())
                .idProducto(producto.getId())
                .sucursalNombre(sucursal.getNombre())
                .productNombre(producto.getNombre())
                .stock(inventario.getStock())
                .build();
    }

    //Mapeamos Respecto a los DTOS
    private InventarioRs toRs(Inventario inv) {
        return InventarioRs.builder()
                .idInventario(inv.getIdInventario())
                .idSucursal(inv.getSucursal().getId())
                .idProducto(inv.getProducto().getId())
                .sucursalNombre(inv.getSucursal().getNombre())
                .productNombre(inv.getProducto().getNombre())
                .stock(inv.getStock())
                .build();
    }
}
