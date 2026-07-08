package com.clinica.sistemaclinica.controller.atendente;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clinica.sistemaclinica.model.entity.Paciente;
import com.clinica.sistemaclinica.model.repository.RepositoryFacade;

@Controller
@RequestMapping("/atendente/paciente")
public class PacienteController {

    @Autowired
    private RepositoryFacade facade;

    @GetMapping({"", "/"})
    public String listar(Model model) throws SQLException {
        model.addAttribute("pacientes", facade.readAllPacientes());
        return "atendente/paciente/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "atendente/paciente/form";
    }

    @GetMapping("/editar/{cpf}")
    public String editar(@PathVariable String cpf, Model model) throws SQLException {
        Paciente paciente = facade.readPaciente(cpf);
        model.addAttribute("paciente", paciente != null ? paciente : new Paciente());
        return "atendente/paciente/form";
    }

    @PostMapping("/salvar")
    public String salvar(Paciente paciente) throws SQLException {
        if (facade.readPaciente(paciente.getCpf()) != null) {
            facade.updatePaciente(paciente);
        } else {
            facade.createPaciente(paciente);
        }
        return "redirect:/atendente/paciente";
    }

    @GetMapping("/excluir/{cpf}")
    public String excluir(@PathVariable String cpf) throws SQLException {
        facade.deletePaciente(cpf);
        return "redirect:/atendente/paciente";
    }
}