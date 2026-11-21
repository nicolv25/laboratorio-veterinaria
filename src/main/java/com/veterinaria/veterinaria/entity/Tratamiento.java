package com.veterinaria.veterinaria.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tratamientos")
public class Tratamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String dosis;

    @ManyToOne
    @JoinColumn(name = "cita_id")
    private Cita cita;
}
