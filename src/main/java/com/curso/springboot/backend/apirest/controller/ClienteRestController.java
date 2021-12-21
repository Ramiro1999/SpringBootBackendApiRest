package com.curso.springboot.backend.apirest.controller;


import com.curso.springboot.backend.apirest.model.Cliente;
import com.curso.springboot.backend.apirest.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    public ResponseEntity<?> show(@PathVariable Long id){  //ResponseEntity maneja toda la respuesta HTTP incluyendo el cuerpo, cabecera y códigos de estado permitiéndonos total libertad de configurar la respuesta que queremos que se envié desde nuestros endpoints

        Cliente cliente= null;
        Map<String, Object> response = new HashMap<>();
        try{
           cliente= clienteService.findById(id);  //se va a intentar conectarse a la bd y obtener el cliente

        }catch(DataAccessException e){ //si ocurre algun error, se ejecutará la excepcion
            response.put("mensaje","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage()+": "+e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(cliente == null){ //si el cliente es nulo se devuelve un mensaje de error
            response.put("mensaje","El cliente ID: "+ id.toString()+ " no existe en la base de datos!");
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
    }


    @PostMapping("/clientes") //POST,crea un nuevo cliente, retorna el objeto creado.
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result){  //se usa el @RequestBody porque viene en formato json dentro del cuerpo del request, entonces con requestBody spring mapea los datos al objeto cliente

        Cliente cliente1=null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
        }

        try {
            cliente1= clienteService.save(cliente);

        }catch(DataAccessException e) {
            response.put("mensaje","Error al realizar el insert en la base de datos");
            response.put("error",e.getMessage()+": "+e.getMostSpecificCause().getMessage());
        }
        response.put("mensaje","El cliente a sido creado con éxito!");
        response.put("cliente",cliente1);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED); //se retorna un 201 (creado)
    }


    @PutMapping("/clientes/{id}") //PUT, se actualiza el cliente, retorna el cliente actualizado.
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente,BindingResult result,@PathVariable Long id){

        Cliente clienteActual = clienteService.findById(id); //se modifica al cliente actual con los datos del cliente que se trajo como parametro
        Cliente clienteUpdated = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
        }


        if(clienteActual == null){ //si el cliente es nulo se devuelve un mensaje de error
            response.put("mensaje","Error, no se pudo editar,el cliente ID: "+ id.toString()+ " no existe en la base de datos!");
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
        }

        try {
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setEmail(cliente.getEmail());
            clienteActual.setDni(cliente.getDni());
            clienteActual.setCreateAt(cliente.getCreateAt());
            clienteUpdated = clienteService.save(clienteActual);

        }catch(DataAccessException e) {
            response.put("mensaje","Error al actualizar el cliente en la base de datos");
            response.put("error",e.getMessage()+": "+e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente a sido actualizado con éxito!");
        response.put("cliente",clienteUpdated);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED); //se retorna un 201 (creado)
    }



    @DeleteMapping("/clientes/{id}") //DELETE, se elimina el cliente de la bd por el id.
   //se retorna un 204 (no content)
    public ResponseEntity<?> delete(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();
        //no es necesario validar si el cliente existe en la bd, ya que internamente se valida que el cliente exista por id
        try {
            clienteService.delete(id);
        }catch(DataAccessException e) {
            response.put("mensaje","Error al eliminar el cliente de la base de datos");
            response.put("error",e.getMessage()+": "+e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente a sido eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
    }
}
