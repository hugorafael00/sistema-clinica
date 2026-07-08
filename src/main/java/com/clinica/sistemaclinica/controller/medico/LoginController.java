package com.clinica.sistemaclinica.controller.medico;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clinica.sistemaclinica.model.entity.Medico;
import com.clinica.sistemaclinica.model.repository.RepositoryFacade;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/medico")
public class LoginController {

    @Autowired
    private RepositoryFacade facade;

    @Autowired
    private HttpSession session;

    @GetMapping({"/login", ""})
    public String login() {
        if (session.getAttribute("medicoLogado") != null) {
            return "redirect:/medico/painel";
        }
        return "medico/login";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam String crm, @RequestParam String senha, Model model) throws SQLException {
        Medico medico = facade.login(crm, senha);

        if (medico != null) {
            session.setAttribute("medicoLogado", medico);
            return "redirect:/medico/painel";
        }

        model.addAttribute("erro", "CRM ou senha inválidos.");
        return "medico/login";
    }

    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("medicoLogado");
        return "redirect:/medico/login";
    }
}