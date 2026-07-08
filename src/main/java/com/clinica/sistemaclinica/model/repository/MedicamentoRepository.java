package com.clinica.sistemaclinica.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.clinica.sistemaclinica.model.entity.Medicamento;

@Repository
public class MedicamentoRepository implements com.clinica.sistemaclinica.model.repository.Repository<Medicamento, Integer> {

    private final DataSource dataSource;

    public MedicamentoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Medicamento map(ResultSet rs) throws SQLException {
        Medicamento m = new Medicamento();
        m.setCodigo(rs.getInt("codigo"));
        m.setNome(rs.getString("nome"));
        m.setDosagem(rs.getString("dosagem"));
        m.setTipoDosagem(rs.getString("tipo_dosagem"));
        m.setDescricao(rs.getString("descricao"));
        m.setObservacao(rs.getString("observacao"));
        return m;
    }

    @Override
    public void create(Medicamento m) throws SQLException {
        String sql = "insert into medicamento (nome, dosagem, tipo_dosagem, descricao, observacao) values (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, m.getNome());
            pstm.setString(2, m.getDosagem());
            pstm.setString(3, m.getTipoDosagem());
            pstm.setString(4, m.getDescricao());
            pstm.setString(5, m.getObservacao());

            pstm.execute();
        }
    }

    @Override
    public Medicamento read(Integer codigo) throws SQLException {
        String sql = "select * from medicamento where codigo = ?";

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
    public void update(Medicamento m) throws SQLException {
        String sql = "update medicamento set nome = ?, dosagem = ?, tipo_dosagem = ?, descricao = ?, observacao = ? where codigo = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, m.getNome());
            pstm.setString(2, m.getDosagem());
            pstm.setString(3, m.getTipoDosagem());
            pstm.setString(4, m.getDescricao());
            pstm.setString(5, m.getObservacao());
            pstm.setInt(6, m.getCodigo());

            pstm.execute();
        }
    }

    @Override
    public void delete(Integer codigo) throws SQLException {
        String sql = "delete from medicamento where codigo = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, codigo);
            pstm.execute();
        }
    }

    @Override
    public List<Medicamento> readAll() throws SQLException {
        String sql = "select * from medicamento";
        List<Medicamento> medicamentos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                medicamentos.add(map(rs));
            }
        }

        return medicamentos;
    }
}