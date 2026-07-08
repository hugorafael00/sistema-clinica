package com.clinica.sistemaclinica.controller.atendente;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clinica.sistemaclinica.model.entity.Consulta;
import com.clinica.sistemaclinica.model.repository.RepositoryFacade;

@Controller
@RequestMapping("/atendente/consulta")
public class ConsultaController {

    @Autowired
    private RepositoryFacade facade;

    @GetMapping({"", "/"})
    public String listar(Model model) throws SQLException {
        model.addAttribute("consultas", facade.readAllConsultas());
        return "atendente/consulta/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) throws SQLException {
        model.addAttribute("consulta", new Consulta());
        model.addAttribute("pacientes", facade.readAllPacientes());
        model.addAttribute("medicos", facade.readAllMedicos());
        return "atendente/consulta/form";
    }

    @PostMapping("/salvar")
    public String salvar(Consulta consulta) throws SQLException {
        facade.createConsulta(consulta);
        return "redirect:/atendente/consulta";
    }
}