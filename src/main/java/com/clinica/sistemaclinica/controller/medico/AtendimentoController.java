package com.clinica.sistemaclinica.controller.medico;

import java.sql.SQLException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clinica.sistemaclinica.model.entity.Consulta;
import com.clinica.sistemaclinica.model.entity.ItemReceituario;
import com.clinica.sistemaclinica.model.entity.Medico;
import com.clinica.sistemaclinica.model.entity.Prontuario;
import com.clinica.sistemaclinica.model.entity.Receituario;
import com.clinica.sistemaclinica.model.repository.RepositoryFacade;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/medico/atendimento")
public class AtendimentoController {

    @Autowired
    private RepositoryFacade facade;

    @Autowired
    private HttpSession session;

    @GetMapping("/{codigo}")
    public String ver(@PathVariable Integer codigo, Model model) throws SQLException {

        if (session.getAttribute("medicoLogado") == null) {
            return "redirect:/medico/login";
        }

        Consulta consulta = carregarConsultaDoMedicoLogado(codigo);
        if (consulta == null) {
            return "redirect:/medico/painel";
        }

        model.addAttribute("consulta", consulta);

        Prontuario prontuario = facade.readProntuarioPorConsulta(codigo);

        if (prontuario != null) {
            model.addAttribute("prontuario", prontuario);

            Receituario receituario = facade.readReceituarioPorProntuario(prontuario.getCodigo());
            if (receituario != null) {
                model.addAttribute("receituario", receituario);
                model.addAttribute("itens", facade.readItensPorReceituario(receituario.getCodigo()));
            } else {
                model.addAttribute("itens", Collections.emptyList());
            }
        } else {
            model.addAttribute("prontuarioNovo", new Prontuario());
        }

        model.addAttribute("medicamentos", facade.readAllMedicamentos());
        model.addAttribute("itemReceituarioNovo", new ItemReceituario());

        return "medico/atendimento";
    }

    @PostMapping("/{codigo}/prontuario")
    public String criarProntuario(@PathVariable Integer codigo, Prontuario prontuario) throws SQLException {

        if (session.getAttribute("medicoLogado") == null) {
            return "redirect:/medico/login";
        }

        Consulta consulta = carregarConsultaDoMedicoLogado(codigo);
        if (consulta == null) {
            return "redirect:/medico/painel";
        }

        prontuario.setConsultaCodigo(codigo);
        facade.createProntuario(prontuario);

        return "redirect:/medico/atendimento/" + codigo;
    }

    @PostMapping("/{codigo}/item-receituario")
    public String adicionarItemReceituario(@PathVariable Integer codigo, ItemReceituario item) throws SQLException {

        if (session.getAttribute("medicoLogado") == null) {
            return "redirect:/medico/login";
        }

        Consulta consulta = carregarConsultaDoMedicoLogado(codigo);
        if (consulta == null) {
            return "redirect:/medico/painel";
        }

        Prontuario prontuario = facade.readProntuarioPorConsulta(codigo);
        if (prontuario == null) {
            return "redirect:/medico/atendimento/" + codigo;
        }

        Receituario receituario = facade.readReceituarioPorProntuario(prontuario.getCodigo());
        if (receituario == null) {
            receituario = new Receituario();
            receituario.setProntuarioCodigo(prontuario.getCodigo());
            receituario.setObservacao("");
            facade.createReceituario(receituario);
        }

        item.setReceituarioCodigo(receituario.getCodigo());
        facade.createItemReceituario(item);

        return "redirect:/medico/atendimento/" + codigo;
    }

    private Consulta carregarConsultaDoMedicoLogado(Integer codigo) throws SQLException {
        Medico medicoLogado = (Medico) session.getAttribute("medicoLogado");

        Consulta consulta = facade.readConsulta(codigo);

        if (consulta != null && consulta.getMedicoCrm().equals(medicoLogado.getCrm())) {
            return consulta;
        }

        return null;
    }
}