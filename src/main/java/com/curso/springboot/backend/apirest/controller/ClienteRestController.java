package com.curso.springboot.backend.apirest.controller;

import com.curso.springboot.backend.apirest.model.Cliente;
import com.curso.springboot.backend.apirest.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {


    @Autowired
    public IClienteService clienteService;


    @GetMapping("/clientes") //GET, retorna todos los clientes
    public List<Cliente> index(){
        return clienteService.findAll();
    }


    @GetMapping("/clientes/{id}") //GET, retorna el cliente buscado.
    public Cliente show(@PathVariable Long id){
        return clienteService.findById(id);
    }

    @PostMapping("/clientes") //POST,crea un nuevo cliente, retorna el objeto creado.
    @ResponseStatus(HttpStatus.CREATED) //se retorna un 201 (creado)
    public Cliente create(@RequestBody Cliente cliente){  //se usa el @RequestBody porque viene en formato json dentro del cuerpo del request, entonces con requestBody spring mapea los datos al objeto cliente
        return clienteService.save(cliente);
    }


    @PutMapping("/clientes/{id}") //PUT, se actualiza el cliente, retorna el cliente actualizado.
    public Cliente update(@RequestBody Cliente cliente,@PathVariable Long id){
        Cliente clienteActual = clienteService.findById(id); //se modifica al cliente actual con los datos del cliente que se trajo como parametro
        clienteActual.setApellido(cliente.getApellido());
        clienteActual.setNombre(cliente.getNombre());
        clienteActual.setEmail(cliente.getEmail());
        clienteActual.setDni(cliente.getDni());
        return clienteService.save(clienteActual);
    }

    @DeleteMapping("/clientes/{id}") //DELETE, se elimina el cliente de la bd por el id.
    @ResponseStatus(HttpStatus.NO_CONTENT) //se retorna un 204 (no content)
    public void delete(@PathVariable Long id){
        clienteService.delete(id);
    }
}
