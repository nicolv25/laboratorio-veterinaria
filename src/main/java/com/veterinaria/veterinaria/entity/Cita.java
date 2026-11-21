package com.veterinaria.veterinaria.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
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
}
