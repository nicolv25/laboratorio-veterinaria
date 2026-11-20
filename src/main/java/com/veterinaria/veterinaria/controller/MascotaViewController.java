package com.veterinaria.veterinaria.controller;

import com.veterinaria.veterinaria.entity.Mascota;
import com.veterinaria.veterinaria.repository.DuenoRepository;
import com.veterinaria.veterinaria.repository.MascotaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mascotas")
public class MascotaViewController {

    private final MascotaRepository mascotaRepository;
    private final DuenoRepository duenoRepository;

    public MascotaViewController(MascotaRepository mascotaRepository, DuenoRepository duenoRepository) {
        this.mascotaRepository = mascotaRepository;
        this.duenoRepository = duenoRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("mascotas", mascotaRepository.findAll());
        return "mascotas/lista";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("duenos", duenoRepository.findAll());
        return "mascotas/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return mascotaRepository.findById(id).map(m -> {
            model.addAttribute("mascota", m);
            model.addAttribute("duenos", duenoRepository.findAll());
            return "mascotas/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("error", "La mascota no existe.");
            return "redirect:/mascotas";
        });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Mascota mascota, RedirectAttributes ra) {
        if (mascota.getDueno() == null || mascota.getDueno().getId() == null) {
            ra.addFlashAttribute("error", "Debes seleccionar un dueño.");
            return "redirect:/mascotas/nueva";
        }
        Mascota destino = mascota.getId() != null
                ? mascotaRepository.findById(mascota.getId()).orElse(new Mascota())
                : new Mascota();
        boolean esNueva = destino.getId() == null;
        destino.setNombre(mascota.getNombre());
        destino.setEspecie(mascota.getEspecie());
        destino.setRaza(mascota.getRaza());
        destino.setEdad(mascota.getEdad());
        destino.setDueno(duenoRepository.findById(mascota.getDueno().getId()).orElse(null));
        mascotaRepository.save(destino);
        ra.addFlashAttribute("success", esNueva ? "Mascota registrada correctamente." : "Mascota actualizada correctamente.");
        return "redirect:/mascotas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            mascotaRepository.deleteById(id);
            ra.addFlashAttribute("success", "Mascota eliminada correctamente.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("error", "No se pudo eliminar la mascota porque tiene datos relacionados.");
        }
        return "redirect:/mascotas";
    }
}
