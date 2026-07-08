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

import com.clinica.sistemaclinica.model.entity.Receituario;

@Repository
public class ReceituarioRepository implements com.clinica.sistemaclinica.model.repository.Repository<Receituario, Integer> {

    private final DataSource dataSource;

    public ReceituarioRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Receituario map(ResultSet rs) throws SQLException {
        Receituario r = new Receituario();
        r.setCodigo(rs.getInt("codigo"));
        r.setObservacao(rs.getString("observacao"));
        r.setProntuarioCodigo(rs.getInt("prontuario_codigo"));
        return r;
    }

    @Override
    public void create(Receituario r) throws SQLException {
        String sql = "insert into receituario (observacao, prontuario_codigo) values (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setString(1, r.getObservacao());
            pstm.setInt(2, r.getProntuarioCodigo());

            pstm.execute();

            try (ResultSet keys = pstm.getGeneratedKeys()) {
                if (keys.next()) {
                    r.setCodigo(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public Receituario read(Integer codigo) throws SQLException {
        String sql = "select * from receituario where codigo = ?";

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
    public void update(Receituario r) throws SQLException {
        String sql = "update receituario set observacao = ? where codigo = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, r.getObservacao());
            pstm.setInt(2, r.getCodigo());

            pstm.execute();
        }
    }

    @Override
    public void delete(Integer codigo) throws SQLException {
        String sql = "delete from receituario where codigo = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, codigo);
            pstm.execute();
        }
    }

    @Override
    public List<Receituario> readAll() throws SQLException {
        String sql = "select * from receituario";
        List<Receituario> receituarios = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                receituarios.add(map(rs));
            }
        }

        return receituarios;
    }

    
    public Receituario findByProntuario(Integer prontuarioCodigo) throws SQLException {
        String sql = "select * from receituario where prontuario_codigo = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, prontuarioCodigo);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }

        return null;
    }
}