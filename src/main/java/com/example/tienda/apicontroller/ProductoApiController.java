package com.example.tienda.apicontroller;

import com.example.tienda.api.ProductoApi;
import com.example.tienda.models.ProductoRq;
import com.example.tienda.models.ProductoRs;
import com.example.tienda.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductoApiController implements ProductoApi {
    //Inyectar el service
    private final ProductoService productoService;

    @Override
    public ProductoRs crear(ProductoRq rq) {
        return productoService.crear(rq);//ya el service valida la logica
    }

    @Override
    public List<ProductoRs> listar() {
        return productoService.listar();
    }

    @Override
    public ProductoRs actualizar(Long idProducto, ProductoRq rq) {
        return productoService.actualizar(idProducto, rq);
    }
}
