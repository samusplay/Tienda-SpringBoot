package com.example.tienda.service;

import com.example.tienda.entity.Producto;
import com.example.tienda.models.ProductoRq;
import com.example.tienda.models.ProductoRs;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductoService {
    //Las funcionalidades firmamos con su Dto
    ProductoRs crear(ProductoRq rq);
    //Firma de listar productos
    List<ProductoRs>listar();

}
