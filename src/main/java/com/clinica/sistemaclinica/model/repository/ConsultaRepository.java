package com.clinica.sistemaclinica.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.clinica.sistemaclinica.model.entity.Consulta;
import com.clinica.sistemaclinica.model.entity.Medico;
import com.clinica.sistemaclinica.model.entity.Paciente;

@Repository
public class ConsultaRepository implements com.clinica.sistemaclinica.model.repository.Repository<Consulta, Integer> {

    private final DataSource dataSource;

    public ConsultaRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // SELECT base usado por todos os metodos de leitura (com JOIN)
    private static final String SELECT_BASE =
        "select c.codigo, c.data_hora, c.data_hora_volta, c.observacao, " +
        "       c.paciente_cpf, c.medico_crm, " +
        "       p.nome as paciente_nome, p.endereco as paciente_endereco, " +
        "       p.contato as paciente_contato, p.plano_saude as paciente_plano_saude, " +
        "       m.nome as medico_nome, m.especialidade as medico_especialidade, " +
        "       m.contato as medico_contato " +
        "from consulta c " +
        "join paciente p on p.cpf = c.paciente_cpf " +
        "join medico m on m.crm = c.medico_crm ";

    private Consulta map(ResultSet rs) throws SQLException {
        Consulta c = new Consulta();
        c.setCodigo(rs.getInt("codigo"));
        c.setDataHora(rs.getString("data_hora"));
        c.setDataHoraVolta(rs.getString("data_hora_volta"));
        c.setObservacao(rs.getString("observacao"));
        c.setPacienteCpf(rs.getString("paciente_cpf"));
        c.setMedicoCrm(rs.getString("medico_crm"));

        Paciente p = new Paciente();
        p.setCpf(rs.getString("paciente_cpf"));
        p.setNome(rs.getString("paciente_nome"));
        p.setEndereco(rs.getString("paciente_endereco"));
        p.setContato(rs.getString("paciente_contato"));
        p.setPlanoSaude(rs.getString("paciente_plano_saude"));
        c.setPaciente(p);

        Medico m = new Medico();
        m.setCrm(rs.getString("medico_crm"));
        m.setNome(rs.getString("medico_nome"));
        m.setEspecialidade(rs.getString("medico_especialidade"));
        m.setContato(rs.getString("medico_contato"));
        c.setMedico(m);

        return c;
    }

    @Override
    public void create(Consulta c) throws SQLException {
        String sql = "insert into consulta (data_hora, data_hora_volta, observacao, paciente_cpf, medico_crm) " +
                     "values (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setString(1, c.getDataHora());
            pstm.setString(2, c.getDataHoraVolta());
            pstm.setString(3, c.getObservacao());
            pstm.setString(4, c.getPacienteCpf());
            pstm.setString(5, c.getMedicoCrm());

            pstm.execute();

            // captura o codigo gerado (necessario para criar o Prontuario em seguida)
            try (ResultSet keys = pstm.getGeneratedKeys()) {
                if (keys.next()) {
                    c.setCodigo(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public Consulta read(Integer codigo) throws SQLException {
        String sql = SELECT_BASE + "where c.codigo = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, codigo);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }

        return null;
    }

    @Override
    public void update(Consulta c) throws SQLException {
        String sql = "update consulta set data_hora = ?, data_hora_volta = ?, observacao = ?, " +
                     "paciente_cpf = ?, medico_crm = ? where codigo = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, c.getDataHora());
            pstm.setString(2, c.getDataHoraVolta());
            pstm.setString(3, c.getObservacao());
            pstm.setString(4, c.getPacienteCpf());
            pstm.setString(5, c.getMedicoCrm());
            pstm.setInt(6, c.getCodigo());

            pstm.execute();
        }
    }

    @Override
    public void delete(Integer codigo) throws SQLException {
        String sql = "delete from consulta where codigo = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, codigo);
            pstm.execute();
        }
    }

    @Override
    public List<Consulta> readAll() throws SQLException {
        String sql = SELECT_BASE;
        List<Consulta> consultas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                consultas.add(map(rs));
            }
        }

        return consultas;
    }

    /*
     * Filter Area
     */

    // Consultas pendentes de um medico = consultas SEM prontuario associado
    public List<Consulta> findPendentesByMedico(String crm) throws SQLException {
        String sql = SELECT_BASE +
            "where c.medico_crm = ? " +
            "and not exists (select 1 from prontuario pr where pr.consulta_codigo = c.codigo) " +
            "order by c.data_hora";

        List<Consulta> consultas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, crm);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    consultas.add(map(rs));
                }
            }
        }

        return consultas;
    }

    // Consultas realizadas de um medico = consultas COM prontuario associado
    public List<Consulta> findRealizadasByMedico(String crm) throws SQLException {
        String sql = SELECT_BASE +
            "where c.medico_crm = ? " +
            "and exists (select 1 from prontuario pr where pr.consulta_codigo = c.codigo) " +
            "order by c.data_hora desc";

        List<Consulta> consultas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, crm);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    consultas.add(map(rs));
                }
            }
        }

        return consultas;
    }
}