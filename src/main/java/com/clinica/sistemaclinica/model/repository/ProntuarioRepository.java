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

import com.clinica.sistemaclinica.model.entity.Prontuario;

@Repository
public class ProntuarioRepository implements com.clinica.sistemaclinica.model.repository.Repository<Prontuario, Integer> {

    private final DataSource dataSource;

    public ProntuarioRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Prontuario map(ResultSet rs) throws SQLException {
        Prontuario p = new Prontuario();
        p.setCodigo(rs.getInt("codigo"));
        p.setDescricao(rs.getString("descricao"));
        p.setObservacao(rs.getString("observacao"));
        p.setConsultaCodigo(rs.getInt("consulta_codigo"));
        return p;
    }

    @Override
    public void create(Prontuario p) throws SQLException {
        String sql = "insert into prontuario (descricao, observacao, consulta_codigo) values (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setString(1, p.getDescricao());
            pstm.setString(2, p.getObservacao());
            pstm.setInt(3, p.getConsultaCodigo());

            pstm.execute();

            try (ResultSet keys = pstm.getGeneratedKeys()) {
                if (keys.next()) {
                    p.setCodigo(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public Prontuario read(Integer codigo) throws SQLException {
        String sql = "select * from prontuario where codigo = ?";

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
    public void update(Prontuario p) throws SQLException {
        String sql = "update prontuario set descricao = ?, observacao = ? where codigo = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, p.getDescricao());
            pstm.setString(2, p.getObservacao());
            pstm.setInt(3, p.getCodigo());

            pstm.execute();
        }
    }

    @Override
    public void delete(Integer codigo) throws SQLException {
        String sql = "delete from prontuario where codigo = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, codigo);
            pstm.execute();
        }
    }

    @Override
    public List<Prontuario> readAll() throws SQLException {
        String sql = "select * from prontuario";
        List<Prontuario> prontuarios = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                prontuarios.add(map(rs));
            }
        }

        return prontuarios;
    }

    
    public Prontuario findByConsulta(Integer consultaCodigo) throws SQLException {
        String sql = "select * from prontuario where consulta_codigo = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, consultaCodigo);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }

        return null;
    }
}