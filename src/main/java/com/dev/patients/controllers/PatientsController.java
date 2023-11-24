package com.dev.patients.controllers;

import com.dev.patients.entities.Patient;
import com.dev.patients.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSOutput;

import java.sql.SQLOutput;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientsController {
    private PatientRepository patientRepository;

    @GetMapping("/index")
    public String sayHello(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        // Page<Patient> pagePatients = patientRepository.findAll(PageRequest.of(page,size));
        Page<Patient> pagePatients = patientRepository.findByNomContains(keyword, PageRequest.of(page,size));

        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);

        return "patients";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/delete")
    public String deletePatient(Long id, int page, String keyword) {
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/listPatients")
    @ResponseBody
    public List<Patient> getListPatienst() {
        return patientRepository.findAll();
    }

    @GetMapping("/formPatient")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }

    @GetMapping("/editPatient")
    public String editPatient(Model model, Long id, int page, String keyword) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient == null) throw new RuntimeException("Patient not found");
        model.addAttribute("patient", patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "formPatient";
    }

    @PostMapping("/save")
    public String savePatient(Model model, @Valid Patient patient, BindingResult bindingResult, @RequestParam(defaultValue = "0" ) int page, @RequestParam(defaultValue = "" ) String keyword) {
        if(bindingResult.hasErrors()) return "formPatient";
        patientRepository.save(patient);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

}
