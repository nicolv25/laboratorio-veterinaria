package com.veterinaria.veterinaria.controller;

import com.veterinaria.veterinaria.entity.Cita;
import com.veterinaria.veterinaria.repository.CitaRepository;
import com.veterinaria.veterinaria.repository.MascotaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

@Controller
@RequestMapping("/citas")
public class CitaViewController {

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;

    public CitaViewController(CitaRepository citaRepository, MascotaRepository mascotaRepository) {
        this.citaRepository = citaRepository;
        this.mascotaRepository = mascotaRepository;
    }

    // El <input type="date"> envía yyyy-MM-dd
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null || text.isBlank() ? null : LocalDate.parse(text));
            }
        });
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("citas", citaRepository.findAll());
        return "citas/lista";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("mascotas", mascotaRepository.findAll());
        return "citas/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return citaRepository.findById(id).map(c -> {
            model.addAttribute("cita", c);
            model.addAttribute("mascotas", mascotaRepository.findAll());
            return "citas/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("error", "La cita no existe.");
            return "redirect:/citas";
        });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cita cita, RedirectAttributes ra) {
        if (cita.getMascota() == null || cita.getMascota().getId() == null) {
            ra.addFlashAttribute("error", "Debes seleccionar una mascota.");
            return "redirect:/citas/nueva";
        }
        Cita destino = cita.getId() != null
                ? citaRepository.findById(cita.getId()).orElse(new Cita())
                : new Cita();
        boolean esNueva = destino.getId() == null;
        destino.setFecha(cita.getFecha());
        destino.setMotivo(cita.getMotivo());
        destino.setVeterinario(cita.getVeterinario());
        destino.setMascota(mascotaRepository.findById(cita.getMascota().getId()).orElse(null));
        citaRepository.save(destino);
        ra.addFlashAttribute("success", esNueva ? "Cita registrada correctamente." : "Cita actualizada correctamente.");
        return "redirect:/citas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            citaRepository.deleteById(id);
            ra.addFlashAttribute("success", "Cita eliminada correctamente.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("error", "No se pudo eliminar la cita porque tiene datos relacionados.");
        }
        return "redirect:/citas";
    }
}
