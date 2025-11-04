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

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;

    @Override
    @Transactional
    public ProductoRs crear(ProductoRq rq) {

        //Validar si hay un sku duplicado
        if (rq.getSku() != null && productoRepository.existsBySku(rq.getSku())) {
            throw new IllegalArgumentException("El sku ya existe");
        }
        // validar la sucursal por nombre
        if (rq.getSucursal() != null && !sucursalRepository.existsByNombreIgnoreCase(rq.getSucursal())) {
            throw new IllegalArgumentException("la sucursal indicada no existe");
        }
        //Validar si esta en el inventario de la sucursal(luego)

        //mapeo directo sin mapper

        Producto p = new Producto();
        p.setNombre(rq.getNombre());
        p.setPrecio(rq.getPrecio());
        p.setSku(rq.getSku());
        p.setActivo(rq.getActivo() != null ? rq.getActivo() : true);

        Producto g = productoRepository.save(p);

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

    @Override
    public List<ProductoRs> listar() {

        //Obtener todos los productos de la Db
        var productos = productoRepository.findAll();

        //mapear entidad para devolver la respuesta
        return productos.stream()
                .map(p -> ProductoRs.builder()
                        .id(p.getId())
                        .nombre(p.getNombre())
                        .precio(p.getPrecio())
                        .sku(p.getSku())
                        .activo(p.getActivo())
                        .createdAt(p.getCreatedAt())
                        .build()
                ).toList();
    }

    @Override
    public ProductoRs actualizar(Long idProducto, ProductoRq rq) {

        //validacion de si el producto existe
        var producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("El producto indicado no existe"));


        //validar si es nulo o vacio en el nombre
        String nuevoNombre = (rq.getNombre() == null || rq.getNombre().trim().isEmpty())
                ? producto.getNombre()
                : rq.getNombre().trim();


        //validar si es nulo o vacio en el precio
        BigDecimal nuevoPrecio = (rq.getPrecio() == null || rq.getPrecio().doubleValue() <= 0)
                ? producto.getPrecio()
                : rq.getPrecio();

        //validar si es nulo o vacio en el SKU
        String nuevoSku = (rq.getSku() == null || rq.getSku().trim().isEmpty())
                ? producto.getSku()
                : rq.getSku().trim();


        //validar si es nulo o vacio en el ACTIVO
        Boolean nuevoActivo = (rq.getActivo() == null)
                ? producto.getActivo()
                : rq.getActivo();

        //validar el sku
        if (!nuevoSku.equalsIgnoreCase(producto.getSku())
                && productoRepository.existsBySku(nuevoSku)) {
            throw new IllegalArgumentException("El SKU ingresado ya se encuentra en uso");
        }

        //Construimos actualizar
        producto.setNombre(nuevoNombre);
        producto.setPrecio(nuevoPrecio);
        producto.setSku(nuevoSku);
        producto.setActivo(nuevoActivo);

        //Guardamos en la base de datos
        var actualizado = productoRepository.save(producto);


        //Mapeamos Respuesta
        return ProductoRs.builder()
                .id(actualizado.getId())
                .nombre(actualizado.getNombre())
                .precio(actualizado.getPrecio())
                .sku(actualizado.getSku())
                .activo(actualizado.getActivo())
                .createdAt(actualizado.getCreatedAt())
                .build();

    }
}
