package com.curso.springboot.backend.apirest.service;

import com.curso.springboot.backend.apirest.model.Cliente;

import java.util.List;

public interface IClienteService {

    public List<Cliente> findAll();

    public Cliente findById(Long id);

    public Cliente save(Cliente cliente);

    public void delete(Long id);
}
