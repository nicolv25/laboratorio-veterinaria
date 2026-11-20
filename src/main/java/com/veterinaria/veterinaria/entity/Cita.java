package com.veterinaria.veterinaria.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private String motivo;
    private String veterinario;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Tratamiento> tratamientos;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getVeterinario() { return veterinario; }
    public void setVeterinario(String veterinario) { this.veterinario = veterinario; }
    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota mascota) { this.mascota = mascota; }
    public List<Tratamiento> getTratamientos() { return tratamientos; }
    public void setTratamientos(List<Tratamiento> tratamientos) { this.tratamientos = tratamientos; }
}