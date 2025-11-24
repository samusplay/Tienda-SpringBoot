package com.example.tienda.service.impl;

import com.example.tienda.entity.Producto;
import com.example.tienda.entity.Sucursal;
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

        // Validar SKU duplicado
        if (rq.getSku() != null && productoRepository.existsBySku(rq.getSku().trim())) {
            throw new IllegalArgumentException("El sku ya existe");
        }

        // Validar que venga sucursal
        if (rq.getSucursal() == null || rq.getSucursal().trim().isEmpty()) {
            throw new IllegalArgumentException("La sucursal es obligatoria");
        }

        String nombreSucursal = rq.getSucursal().trim();

        // Validar que la sucursal exista y traerla
        Sucursal sucursal = sucursalRepository.findByNombreIgnoreCase(nombreSucursal)
                .orElseThrow(() -> new IllegalArgumentException("La sucursal indicada no existe"));

        // Mapeo directo sin mapper
        Producto p = new Producto();
        p.setNombre(rq.getNombre().trim());
        p.setPrecio(rq.getPrecio());
        p.setSku(rq.getSku().trim());
        p.setActivo(rq.getActivo() != null ? rq.getActivo() : true);
        p.setSucursal(sucursal); // 👈 AQUÍ ES DONDE FALTABA

        Producto g = productoRepository.save(p);

        // Respuesta
        return ProductoRs.builder()
                .id(g.getId())
                .nombre(g.getNombre())
                .precio(g.getPrecio())
                .sku(g.getSku())
                .activo(g.getActivo())
                .createdAt(g.getCreatedAt())
                .sucursal(g.getSucursal().getNombre()) // 👈 devolvemos solo el nombre
                .build();

    }

    @Override
    public List<ProductoRs> listar() {
        // Obtener todos los productos con su sucursal cargada
        var productos = productoRepository.findAllWithSucursal();

        // Mapear entidad → DTO
        return productos.stream()
                .map(p -> ProductoRs.builder()
                        .id(p.getId())
                        .nombre(p.getNombre())
                        .precio(p.getPrecio())
                        .sku(p.getSku())
                        .activo(p.getActivo())
                        .createdAt(p.getCreatedAt())
                        .sucursal(p.getSucursal().getNombre())   // para traernos la sucursal
                        .build()
                )
                .toList();
    }

    @Override
    @Transactional
    public ProductoRs actualizar(Long idProducto, ProductoRq rq) {

        var producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("El producto indicado no existe"));

        // Nombre
        String nuevoNombre = (rq.getNombre() == null || rq.getNombre().trim().isEmpty())
                ? producto.getNombre()
                : rq.getNombre().trim();

        // Precio
        BigDecimal nuevoPrecio = (rq.getPrecio() == null || rq.getPrecio().doubleValue() <= 0)
                ? producto.getPrecio()
                : rq.getPrecio();

        // SKU
        String nuevoSku = (rq.getSku() == null || rq.getSku().trim().isEmpty())
                ? producto.getSku()
                : rq.getSku().trim();

        // Activo
        Boolean nuevoActivo = (rq.getActivo() == null)
                ? producto.getActivo()
                : rq.getActivo();

        // Validar SKU único
        if (!nuevoSku.equalsIgnoreCase(producto.getSku())
                && productoRepository.existsBySku(nuevoSku)) {
            throw new IllegalArgumentException("El SKU ingresado ya se encuentra en uso");
        }

        // 👉 Sucursal (si viene en la request)
        if (rq.getSucursal() != null && !rq.getSucursal().trim().isEmpty()) {
            String nombreSucursal = rq.getSucursal().trim();
            Sucursal sucursal = sucursalRepository.findByNombreIgnoreCase(nombreSucursal)
                    .orElseThrow(() -> new IllegalArgumentException("La sucursal indicada no existe"));
            producto.setSucursal(sucursal);
        }

        producto.setNombre(nuevoNombre);
        producto.setPrecio(nuevoPrecio);
        producto.setSku(nuevoSku);
        producto.setActivo(nuevoActivo);

        var actualizado = productoRepository.save(producto);

        return ProductoRs.builder()
                .id(actualizado.getId())
                .nombre(actualizado.getNombre())
                .precio(actualizado.getPrecio())
                .sku(actualizado.getSku())
                .activo(actualizado.getActivo())
                .createdAt(actualizado.getCreatedAt())
                .sucursal(actualizado.getSucursal().getNombre())
                .build();
    }
}
