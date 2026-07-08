package com.clinica.sistemaclinica.controller.medico;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clinica.sistemaclinica.model.entity.Medico;
import com.clinica.sistemaclinica.model.repository.RepositoryFacade;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/medico")
public class PainelMedicoController {

    @Autowired
    private RepositoryFacade facade;

    @Autowired
    private HttpSession session;

    @GetMapping("/painel")
    public String painel(Model model) throws SQLException {

        Medico medicoLogado = (Medico) session.getAttribute("medicoLogado");

        if (medicoLogado == null) {
            return "redirect:/medico/login";
        }

        model.addAttribute("medico", medicoLogado);
        model.addAttribute("consultas", facade.findConsultasPendentesPorMedico(medicoLogado.getCrm()));

        return "medico/painel";
    }

    @GetMapping("/historico")
    public String historico(Model model) throws SQLException {

        Medico medicoLogado = (Medico) session.getAttribute("medicoLogado");

        if (medicoLogado == null) {
            return "redirect:/medico/login";
        }

        model.addAttribute("medico", medicoLogado);
        model.addAttribute("consultas", facade.findConsultasRealizadasPorMedico(medicoLogado.getCrm()));

        return "medico/historico";
    }
}