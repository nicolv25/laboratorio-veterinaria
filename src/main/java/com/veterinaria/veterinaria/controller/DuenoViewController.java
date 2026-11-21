package com.veterinaria.veterinaria.controller;

import com.veterinaria.veterinaria.entity.Dueno;
import com.veterinaria.veterinaria.repository.DuenoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/duenos")
public class DuenoViewController {

    private final DuenoRepository duenoRepository;

    public DuenoViewController(DuenoRepository duenoRepository) {
        this.duenoRepository = duenoRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("duenos", duenoRepository.findAll());
        return "duenos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("dueno", new Dueno());
        return "duenos/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return duenoRepository.findById(id).map(d -> {
            model.addAttribute("dueno", d);
            return "duenos/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("error", "El dueño no existe.");
            return "redirect:/duenos";
        });
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Dueno dueno, RedirectAttributes ra) {
        Dueno destino = dueno.getId() != null
                ? duenoRepository.findById(dueno.getId()).orElse(new Dueno())
                : new Dueno();
        boolean esNuevo = destino.getId() == null;
        destino.setNombre(dueno.getNombre());
        destino.setTelefono(dueno.getTelefono());
        destino.setCorreo(dueno.getCorreo());
        destino.setDireccion(dueno.getDireccion());
        duenoRepository.save(destino);
        ra.addFlashAttribute("success", esNuevo ? "Dueño registrado correctamente." : "Dueño actualizado correctamente.");
        return "redirect:/duenos";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            duenoRepository.deleteById(id);
            ra.addFlashAttribute("success", "Dueño eliminado correctamente.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("error", "No se pudo eliminar el dueño porque tiene datos relacionados.");
        }
        return "redirect:/duenos";
    }
}
