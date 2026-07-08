package com.clinica.sistemaclinica.controller.atendente;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clinica.sistemaclinica.model.entity.Medico;
import com.clinica.sistemaclinica.model.repository.RepositoryFacade;

@Controller
@RequestMapping("/atendente/medico")
public class MedicoController {

    @Autowired
    private RepositoryFacade facade;

    @GetMapping({"", "/"})
    public String listar(Model model) throws SQLException {
        model.addAttribute("medicos", facade.readAllMedicos());
        return "atendente/medico/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("medico", new Medico());
        return "atendente/medico/form";
    }

    @GetMapping("/editar/{crm}")
    public String editar(@PathVariable String crm, Model model) throws SQLException {
        Medico medico = facade.readMedico(crm);
        model.addAttribute("medico", medico != null ? medico : new Medico());
        return "atendente/medico/form";
    }

    @PostMapping("/salvar")
    public String salvar(Medico medico) throws SQLException {
        if (facade.readMedico(medico.getCrm()) != null) {
            facade.updateMedico(medico);
        } else {
            facade.createMedico(medico);
        }
        return "redirect:/atendente/medico";
    }

    @GetMapping("/excluir/{crm}")
    public String excluir(@PathVariable String crm) throws SQLException {
        facade.deleteMedico(crm);
        return "redirect:/atendente/medico";
    }
}