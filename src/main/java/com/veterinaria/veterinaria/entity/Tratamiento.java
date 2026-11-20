package com.veterinaria.veterinaria.entity;

import jakarta.persistence.*;

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

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }
}
