package com.veterinaria.veterinaria.controller;

import com.veterinaria.veterinaria.repository.CitaRepository;
import com.veterinaria.veterinaria.repository.DuenoRepository;
import com.veterinaria.veterinaria.repository.MascotaRepository;
import com.veterinaria.veterinaria.repository.TratamientoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {

    private final MascotaRepository mascotaRepository;
    private final DuenoRepository duenoRepository;
    private final CitaRepository citaRepository;
    private final TratamientoRepository tratamientoRepository;

    public HomeViewController(MascotaRepository mascotaRepository, DuenoRepository duenoRepository,
                              CitaRepository citaRepository, TratamientoRepository tratamientoRepository) {
        this.mascotaRepository = mascotaRepository;
        this.duenoRepository = duenoRepository;
        this.citaRepository = citaRepository;
        this.tratamientoRepository = tratamientoRepository;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("totalMascotas", mascotaRepository.count());
        model.addAttribute("totalDuenos", duenoRepository.count());
        model.addAttribute("totalCitas", citaRepository.count());
        model.addAttribute("totalTratamientos", tratamientoRepository.count());
        return "index";
    }
}
