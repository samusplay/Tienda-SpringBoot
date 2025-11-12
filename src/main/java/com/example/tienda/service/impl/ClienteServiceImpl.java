package com.example.tienda.service.impl;

import com.example.tienda.entity.Cliente;
import com.example.tienda.models.ClienteRq;
import com.example.tienda.models.ClienteRs;
import com.example.tienda.repository.ClienteRepository;
import com.example.tienda.service.ClienteService;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServiceImpl  implements ClienteService {
    private final ClienteRepository clienteRepository;
    @Override
    public ClienteRs crear(ClienteRq rq) {
       //Validar campos requeridos
        if(rq.getNombre()==null||rq.getNombre().trim().isEmpty()){
            throw  new IllegalArgumentException("El nombre es obligatorio");
        }
        //valida el correo
        if(rq.getCorreo()==null||rq.getCorreo().trim().isEmpty()){
            throw  new IllegalArgumentException("El correo electronico es obligatorio");
        }
        //Normalizar los valores de entrada
        String nombre = rq.getNombre().trim();
        String apellidoPaterno = rq.getApellidoPaterno() != null ? rq.getApellidoPaterno().trim() : null;
        String apellidoMaterno = rq.getApellidoMaterno() != null ? rq.getApellidoMaterno().trim() : null;
        String telefono = rq.getTelefono() != null ? rq.getTelefono().trim() : null;
        String correo = rq.getCorreo().trim().toLowerCase();

        //validar el fromato exacto del correo
        if (!correo.contains("@") || !correo.contains(".")) {
            throw new IllegalArgumentException("El formato del correo electrónico no es válido");
        }

        //Verificar que no exista un cliente con ese correo
        if(clienteRepository.existsByCorreoIgnoreCase(correo)){
            throw  new IllegalArgumentException("Ya existe un cliente registrado con ese correo electronico");
        }

        //Crear el objeto cliente
        Cliente nuevo=Cliente.builder()
                .nombre(nombre)
                .apellidoPaterno(apellidoPaterno)
                .apellidoMaterno(apellidoMaterno)
                .telefono(telefono)
                .correo(correo)
                .build();

        //guardar en base de datos
        Cliente guardado=clienteRepository.save(nuevo);

        //Retornar la respuesta
        return ClienteRs.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .apellidoPaterno(guardado.getApellidoPaterno())
                .apellidoMaterno(guardado.getApellidoMaterno())
                .telefono(guardado.getTelefono())
                .correo(guardado.getCorreo())
                .createdAt(guardado.getCreatedAt())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteRs> listar() {
        return clienteRepository.findAll()
                .stream()
                .map(c -> ClienteRs.builder()
                        .id(c.getId())
                        .nombre(c.getNombre())
                        .apellidoPaterno(c.getApellidoPaterno())
                        .apellidoMaterno(c.getApellidoMaterno())
                        .telefono(c.getTelefono())
                        .correo(c.getCorreo())
                        .createdAt(c.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public ClienteRs actualizar(Long idCliente, ClienteRq rq) {
        //Buscar el cliente existente
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("El cliente indicado no existe"));
        //verificar el correo
        if (rq.getCorreo() != null && rq.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }
        //Validar el nombre
        if (rq.getNombre() != null && rq.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        //Normalizar los datos
        String nuevoNombre = rq.getNombre() != null ? rq.getNombre().trim() : cliente.getNombre();
        String nuevoApellidoPaterno = rq.getApellidoPaterno() != null ? rq.getApellidoPaterno().trim() : cliente.getApellidoPaterno();
        String nuevoApellidoMaterno = rq.getApellidoMaterno() != null ? rq.getApellidoMaterno().trim() : cliente.getApellidoMaterno();
        String nuevoTelefono = rq.getTelefono() != null ? rq.getTelefono().trim() : cliente.getTelefono();
        String nuevoCorreo = rq.getCorreo() != null ? rq.getCorreo().trim().toLowerCase() : cliente.getCorreo();

        //validar si el correo lo tiene otro cliente
        if (!nuevoCorreo.equalsIgnoreCase(cliente.getCorreo()) &&
                clienteRepository.existsByCorreoIgnoreCaseAndIdNot(nuevoCorreo, idCliente)) {
            throw new IllegalArgumentException("El correo ya está siendo usado por otro cliente");

        }
        //crear nuevos valores
        cliente.setNombre(nuevoNombre);
        cliente.setApellidoPaterno(nuevoApellidoPaterno);
        cliente.setApellidoMaterno(nuevoApellidoMaterno);
        cliente.setTelefono(nuevoTelefono);
        cliente.setCorreo(nuevoCorreo);

        //guardamos en la base de datos
        Cliente actualizado = clienteRepository.save(cliente);

        //Devolvemos Respuesta
        return ClienteRs.builder()
                .id(actualizado.getId())
                .nombre(actualizado.getNombre())
                .apellidoPaterno(actualizado.getApellidoPaterno())
                .apellidoMaterno(actualizado.getApellidoMaterno())
                .telefono(actualizado.getTelefono())
                .correo(actualizado.getCorreo())
                .createdAt(actualizado.getCreatedAt())
                .build();
    }

}
