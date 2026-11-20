package com.veterinaria.veterinaria.entity;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "mascotas")
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private Integer edad;

    @ManyToOne
    @JoinColumn(name = "dueno_id")
    private Dueno dueno;

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Cita> citas;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public Dueno getDueno() { return dueno; }
    public void setDueno(Dueno dueno) { this.dueno = dueno; }
    public List<Cita> getCitas() { return citas; }
    public void setCitas(List<Cita> citas) { this.citas = citas; }
}