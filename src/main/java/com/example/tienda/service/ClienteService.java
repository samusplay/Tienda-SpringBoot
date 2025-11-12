package com.example.tienda.service;

import com.example.tienda.models.ClienteRq;
import com.example.tienda.models.ClienteRs;

import java.util.List;

public interface ClienteService {
    //Firmas de las funcionalidades

    //Crear Cliente
    ClienteRs crear(ClienteRq rq);
    //Listar Todos los clientes
    List<ClienteRs> listar();

    //Actualizar cliente
    ClienteRs actualizar(Long idCliente, ClienteRq rq);
}
