package com.curso.springboot.backend.apirest.repository;

import com.curso.springboot.backend.apirest.model.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente,Long> {
}
