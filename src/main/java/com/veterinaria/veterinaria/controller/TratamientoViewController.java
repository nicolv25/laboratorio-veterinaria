package com.veterinaria.veterinaria.controller;

import com.veterinaria.veterinaria.entity.Tratamiento;
import com.veterinaria.veterinaria.repository.CitaRepository;
import com.veterinaria.veterinaria.repository.TratamientoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tratamientos")
public class TratamientoViewController {

    private final TratamientoRepository tratamientoRepository;
    private final CitaRepository citaRepository;

    public TratamientoViewController(TratamientoRepository tratamientoRepository, CitaRepository citaRepository) {
        this.tratamientoRepository = tratamientoRepository;
        this.citaRepository = citaRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tratamientos", tratamientoRepository.findAll());
        return "tratamientos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("tratamiento", new Tratamiento());
        model.addAttribute("citas", citaRepository.findAll());
        return "tratamientos/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return tratamientoRepository.findById(id).map(t -> {
            model.addAttribute("tratamiento", t);
            model.addAttribute("citas", citaRepository.findAll());
            return "tratamientos/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("error", "El tratamiento no existe.");
            return "redirect:/tratamientos";
        });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Tratamiento tratamiento, RedirectAttributes ra) {
        if (tratamiento.getCita() == null || tratamiento.getCita().getId() == null) {
            ra.addFlashAttribute("error", "Debes seleccionar una cita.");
            return "redirect:/tratamientos/nuevo";
        }
        Tratamiento destino = tratamiento.getId() != null
                ? tratamientoRepository.findById(tratamiento.getId()).orElse(new Tratamiento())
                : new Tratamiento();
        boolean esNuevo = destino.getId() == null;
        destino.setDescripcion(tratamiento.getDescripcion());
        destino.setDosis(tratamiento.getDosis());
        destino.setCita(citaRepository.findById(tratamiento.getCita().getId()).orElse(null));
        tratamientoRepository.save(destino);
        ra.addFlashAttribute("success", esNuevo ? "Tratamiento registrado correctamente." : "Tratamiento actualizado correctamente.");
        return "redirect:/tratamientos";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            tratamientoRepository.deleteById(id);
            ra.addFlashAttribute("success", "Tratamiento eliminado correctamente.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("error", "No se pudo eliminar el tratamiento.");
        }
        return "redirect:/tratamientos";
    }
}
