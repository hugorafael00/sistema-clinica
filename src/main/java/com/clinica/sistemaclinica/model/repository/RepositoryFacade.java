package com.clinica.sistemaclinica.model.repository;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.clinica.sistemaclinica.model.entity.Consulta;
import com.clinica.sistemaclinica.model.entity.ItemReceituario;
import com.clinica.sistemaclinica.model.entity.Medicamento;
import com.clinica.sistemaclinica.model.entity.Medico;
import com.clinica.sistemaclinica.model.entity.Paciente;
import com.clinica.sistemaclinica.model.entity.Prontuario;
import com.clinica.sistemaclinica.model.entity.Receituario;

@Component
public class RepositoryFacade {

    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final ConsultaRepository consultaRepository;
    private final ProntuarioRepository prontuarioRepository;
    private final ReceituarioRepository receituarioRepository;
    private final ItemReceituarioRepository itemReceituarioRepository;

    public RepositoryFacade(PacienteRepository pacienteRepository,
                             MedicoRepository medicoRepository,
                             MedicamentoRepository medicamentoRepository,
                             ConsultaRepository consultaRepository,
                             ProntuarioRepository prontuarioRepository,
                             ReceituarioRepository receituarioRepository,
                             ItemReceituarioRepository itemReceituarioRepository) {
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.consultaRepository = consultaRepository;
        this.prontuarioRepository = prontuarioRepository;
        this.receituarioRepository = receituarioRepository;
        this.itemReceituarioRepository = itemReceituarioRepository;
    }

    // paciente
    public void createPaciente(Paciente p) throws SQLException { pacienteRepository.create(p); }
    public Paciente readPaciente(String cpf) throws SQLException { return pacienteRepository.read(cpf); }
    public void updatePaciente(Paciente p) throws SQLException { pacienteRepository.update(p); }
    public void deletePaciente(String cpf) throws SQLException { pacienteRepository.delete(cpf); }
    public List<Paciente> readAllPacientes() throws SQLException { return pacienteRepository.readAll(); }

    // medico
    public void createMedico(Medico m) throws SQLException { medicoRepository.create(m); }
    public Medico readMedico(String crm) throws SQLException { return medicoRepository.read(crm); }
    public void updateMedico(Medico m) throws SQLException { medicoRepository.update(m); }
    public void deleteMedico(String crm) throws SQLException { medicoRepository.delete(crm); }
    public List<Medico> readAllMedicos() throws SQLException { return medicoRepository.readAll(); }
    public Medico login(String crm, String senha) throws SQLException { return medicoRepository.login(crm, senha); }

    // medicamento
    public void createMedicamento(Medicamento m) throws SQLException { medicamentoRepository.create(m); }
    public Medicamento readMedicamento(Integer codigo) throws SQLException { return medicamentoRepository.read(codigo); }
    public void updateMedicamento(Medicamento m) throws SQLException { medicamentoRepository.update(m); }
    public void deleteMedicamento(Integer codigo) throws SQLException { medicamentoRepository.delete(codigo); }
    public List<Medicamento> readAllMedicamentos() throws SQLException { return medicamentoRepository.readAll(); }

    // consulta
    public void createConsulta(Consulta c) throws SQLException { consultaRepository.create(c); }
    public Consulta readConsulta(Integer codigo) throws SQLException { return consultaRepository.read(codigo); }
    public void updateConsulta(Consulta c) throws SQLException { consultaRepository.update(c); }
    public void deleteConsulta(Integer codigo) throws SQLException { consultaRepository.delete(codigo); }
    public List<Consulta> readAllConsultas() throws SQLException { return consultaRepository.readAll(); }
    public List<Consulta> findConsultasPendentesPorMedico(String crm) throws SQLException {
        return consultaRepository.findPendentesByMedico(crm);
    }
    public List<Consulta> findConsultasRealizadasPorMedico(String crm) throws SQLException {
        return consultaRepository.findRealizadasByMedico(crm);
    }

    // prontuario
    public void createProntuario(Prontuario p) throws SQLException { prontuarioRepository.create(p); }
    public Prontuario readProntuario(Integer codigo) throws SQLException { return prontuarioRepository.read(codigo); }
    public void updateProntuario(Prontuario p) throws SQLException { prontuarioRepository.update(p); }
    public void deleteProntuario(Integer codigo) throws SQLException { prontuarioRepository.delete(codigo); }
    public List<Prontuario> readAllProntuarios() throws SQLException { return prontuarioRepository.readAll(); }
    public Prontuario readProntuarioPorConsulta(Integer codigoConsulta) throws SQLException {
        return prontuarioRepository.findByConsulta(codigoConsulta);
    }

    // receituario
    public void createReceituario(Receituario r) throws SQLException { receituarioRepository.create(r); }
    public Receituario readReceituario(Integer codigo) throws SQLException { return receituarioRepository.read(codigo); }
    public void updateReceituario(Receituario r) throws SQLException { receituarioRepository.update(r); }
    public void deleteReceituario(Integer codigo) throws SQLException { receituarioRepository.delete(codigo); }
    public List<Receituario> readAllReceituarios() throws SQLException { return receituarioRepository.readAll(); }
    public Receituario readReceituarioPorProntuario(Integer codigoProntuario) throws SQLException {
        return receituarioRepository.findByProntuario(codigoProntuario);
    }

    // itemReceituario
    public void createItemReceituario(ItemReceituario i) throws SQLException { itemReceituarioRepository.create(i); }
    public ItemReceituario readItemReceituario(Integer codigo) throws SQLException { return itemReceituarioRepository.read(codigo); }
    public void updateItemReceituario(ItemReceituario i) throws SQLException { itemReceituarioRepository.update(i); }
    public void deleteItemReceituario(Integer codigo) throws SQLException { itemReceituarioRepository.delete(codigo); }
    public List<ItemReceituario> readAllItensReceituario() throws SQLException { return itemReceituarioRepository.readAll(); }
    public List<ItemReceituario> readItensPorReceituario(Integer codigoReceituario) throws SQLException {
        return itemReceituarioRepository.findByReceituario(codigoReceituario);
    }
}