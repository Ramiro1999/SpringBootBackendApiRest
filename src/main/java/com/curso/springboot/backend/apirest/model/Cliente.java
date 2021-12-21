package com.curso.springboot.backend.apirest.model;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;



@Entity
public class Cliente {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Min(8)
    @NotNull(message = "es obligatorio")
    @Column(nullable = false,unique = true)
    private Integer dni;

    @NotEmpty(message = "es obligatorio")
    @Column(nullable = false)
    @Size(min = 4, max = 13,message = "tiene que tener entre 4 y 13 caracteres")
    private String nombre;

    @NotEmpty(message = "es obligatorio")
    @Column(nullable = false)
    private String apellido;

    @NotEmpty(message = "es obligatorio")
    @Email
    @Column(nullable = false,unique = true)
    private String email;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @PrePersist
    private void prePersist(){
        createAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
