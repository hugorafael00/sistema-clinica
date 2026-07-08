package com.clinica.sistemaclinica.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.clinica.sistemaclinica.model.entity.Paciente;

@Repository
public class PacienteRepository implements com.clinica.sistemaclinica.model.repository.Repository<Paciente, String> {

    private final DataSource dataSource;

    public PacienteRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Paciente map(ResultSet rs) throws SQLException {
        Paciente p = new Paciente();
        p.setCpf(rs.getString("cpf"));
        p.setNome(rs.getString("nome"));
        p.setEndereco(rs.getString("endereco"));
        p.setContato(rs.getString("contato"));
        p.setPlanoSaude(rs.getString("plano_saude"));
        return p;
    }

    @Override
    public void create(Paciente p) throws SQLException {
        String sql = "insert into paciente (cpf, nome, endereco, contato, plano_saude) values (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, p.getCpf());
            pstm.setString(2, p.getNome());
            pstm.setString(3, p.getEndereco());
            pstm.setString(4, p.getContato());
            pstm.setString(5, p.getPlanoSaude());

            pstm.execute();
        }
    }

    @Override
    public Paciente read(String cpf) throws SQLException {
        String sql = "select * from paciente where cpf = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, cpf);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }

        return null;
    }

    @Override
    public void update(Paciente p) throws SQLException {
        String sql = "update paciente set nome = ?, endereco = ?, contato = ?, plano_saude = ? where cpf = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, p.getNome());
            pstm.setString(2, p.getEndereco());
            pstm.setString(3, p.getContato());
            pstm.setString(4, p.getPlanoSaude());
            pstm.setString(5, p.getCpf());

            pstm.execute();
        }
    }

    @Override
    public void delete(String cpf) throws SQLException {
        String sql = "delete from paciente where cpf = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, cpf);
            pstm.execute();
        }
    }

    @Override
    public List<Paciente> readAll() throws SQLException {
        String sql = "select * from paciente";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                pacientes.add(map(rs));
            }
        }

        return pacientes;
    }
}