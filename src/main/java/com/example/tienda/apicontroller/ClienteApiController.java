package com.example.tienda.apicontroller;

import com.example.tienda.api.ClienteApi;
import com.example.tienda.models.ClienteRq;
import com.example.tienda.models.ClienteRs;
import com.example.tienda.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClienteApiController implements ClienteApi {

    private final ClienteService clienteService;

    @Override
    public ClienteRs crear(ClienteRq rq) {
        return clienteService.crear(rq);
    }

    @Override
    public List<ClienteRs> listar() {
        return clienteService.listar();
    }

    @Override
    public ClienteRs actualizar(Long idCliente, ClienteRq rq) {
        return clienteService.actualizar(idCliente,rq);
    }
}
