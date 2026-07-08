package com.clinica.sistemaclinica.controller.atendente;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clinica.sistemaclinica.model.entity.Medicamento;
import com.clinica.sistemaclinica.model.repository.RepositoryFacade;

@Controller
@RequestMapping("/atendente/medicamento")
public class MedicamentoController {

    @Autowired
    private RepositoryFacade facade;

    @GetMapping({"", "/"})
    public String listar(Model model) throws SQLException {
        model.addAttribute("medicamentos", facade.readAllMedicamentos());
        return "atendente/medicamento/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("medicamento", new Medicamento());
        return "atendente/medicamento/form";
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable Integer codigo, Model model) throws SQLException {
        Medicamento medicamento = facade.readMedicamento(codigo);
        model.addAttribute("medicamento", medicamento != null ? medicamento : new Medicamento());
        return "atendente/medicamento/form";
    }

    @PostMapping("/salvar")
    public String salvar(Medicamento medicamento) throws SQLException {
        if (medicamento.getCodigo() != null && facade.readMedicamento(medicamento.getCodigo()) != null) {
            facade.updateMedicamento(medicamento);
        } else {
            facade.createMedicamento(medicamento);
        }
        return "redirect:/atendente/medicamento";
    }

    @GetMapping("/excluir/{codigo}")
    public String excluir(@PathVariable Integer codigo) throws SQLException {
        facade.deleteMedicamento(codigo);
        return "redirect:/atendente/medicamento";
    }
}