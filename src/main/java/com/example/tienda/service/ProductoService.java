package com.example.tienda.service;

import com.example.tienda.entity.Producto;
import com.example.tienda.models.ProductoRq;
import com.example.tienda.models.ProductoRs;
import org.springframework.stereotype.Service;


public interface ProductoService {
    //Las funcionalidades firmamos con su Dto
    ProductoRs crear(ProductoRq rq);
}
