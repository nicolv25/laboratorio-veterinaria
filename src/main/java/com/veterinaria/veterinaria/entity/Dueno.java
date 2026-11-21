package com.veterinaria.veterinaria.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
@Table(name = "duenos")
public class Dueno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;

    @OneToMany(mappedBy = "dueno", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Mascota> mascotas;
}
