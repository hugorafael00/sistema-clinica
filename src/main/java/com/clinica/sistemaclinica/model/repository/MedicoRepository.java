package com.clinica.sistemaclinica.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.clinica.sistemaclinica.model.entity.Medico;

@Repository
public class MedicoRepository implements com.clinica.sistemaclinica.model.repository.Repository<Medico, String> {

    private final DataSource dataSource;

    public MedicoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Medico map(ResultSet rs) throws SQLException {
        Medico m = new Medico();
        m.setCrm(rs.getString("crm"));
        m.setNome(rs.getString("nome"));
        m.setEspecialidade(rs.getString("especialidade"));
        m.setContato(rs.getString("contato"));
        m.setSenha(rs.getString("senha"));
        return m;
    }

    @Override
    public void create(Medico m) throws SQLException {
        String sql = "insert into medico (crm, nome, especialidade, contato, senha) values (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, m.getCrm());
            pstm.setString(2, m.getNome());
            pstm.setString(3, m.getEspecialidade());
            pstm.setString(4, m.getContato());
            pstm.setString(5, m.getSenha());

            pstm.execute();
        }
    }

    @Override
    public Medico read(String crm) throws SQLException {
        String sql = "select * from medico where crm = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, crm);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }

        return null;
    }

    @Override
    public void update(Medico m) throws SQLException {
        String sql = "update medico set nome = ?, especialidade = ?, contato = ?, senha = ? where crm = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, m.getNome());
            pstm.setString(2, m.getEspecialidade());
            pstm.setString(3, m.getContato());
            pstm.setString(4, m.getSenha());
            pstm.setString(5, m.getCrm());

            pstm.execute();
        }
    }

    @Override
    public void delete(String crm) throws SQLException {
        String sql = "delete from medico where crm = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, crm);
            pstm.execute();
        }
    }

    @Override
    public List<Medico> readAll() throws SQLException {
        String sql = "select * from medico";
        List<Medico> medicos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                medicos.add(map(rs));
            }
        }

        return medicos;
    }
    
    public Medico login(String crm, String senha) throws SQLException {
        String sql = "select * from medico where crm = ? and senha = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, crm);
            pstm.setString(2, senha);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }

        return null;
    }
}