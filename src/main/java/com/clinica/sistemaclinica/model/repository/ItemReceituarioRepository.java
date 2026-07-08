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

import com.clinica.sistemaclinica.model.entity.ItemReceituario;
import com.clinica.sistemaclinica.model.entity.Medicamento;

@Repository
public class ItemReceituarioRepository implements com.clinica.sistemaclinica.model.repository.Repository<ItemReceituario, Integer> {

    private final DataSource dataSource;

    public ItemReceituarioRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SELECT_BASE =
        "select ir.codigo, ir.dosagem, ir.intervalo_entre_doses, ir.observacao, " +
        "       ir.receituario_codigo, ir.medicamento_codigo, " +
        "       med.nome as medicamento_nome, med.dosagem as medicamento_dosagem, " +
        "       med.tipo_dosagem as medicamento_tipo_dosagem, med.descricao as medicamento_descricao " +
        "from item_receituario ir " +
        "join medicamento med on med.codigo = ir.medicamento_codigo ";

    private ItemReceituario map(ResultSet rs) throws SQLException {
        ItemReceituario i = new ItemReceituario();
        i.setCodigo(rs.getInt("codigo"));
        i.setDosagem(rs.getInt("dosagem"));
        i.setIntervaloEntreDoses(rs.getInt("intervalo_entre_doses"));
        i.setObservacao(rs.getString("observacao"));
        i.setReceituarioCodigo(rs.getInt("receituario_codigo"));
        i.setMedicamentoCodigo(rs.getInt("medicamento_codigo"));

        Medicamento m = new Medicamento();
        m.setCodigo(rs.getInt("medicamento_codigo"));
        m.setNome(rs.getString("medicamento_nome"));
        m.setDosagem(rs.getString("medicamento_dosagem"));
        m.setTipoDosagem(rs.getString("medicamento_tipo_dosagem"));
        m.setDescricao(rs.getString("medicamento_descricao"));
        i.setMedicamento(m);

        return i;
    }

    @Override
    public void create(ItemReceituario i) throws SQLException {
        String sql = "insert into item_receituario (dosagem, intervalo_entre_doses, observacao, receituario_codigo, medicamento_codigo) " +
                     "values (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setInt(1, i.getDosagem());
            pstm.setInt(2, i.getIntervaloEntreDoses());
            pstm.setString(3, i.getObservacao());
            pstm.setInt(4, i.getReceituarioCodigo());
            pstm.setInt(5, i.getMedicamentoCodigo());

            pstm.execute();

            try (ResultSet keys = pstm.getGeneratedKeys()) {
                if (keys.next()) {
                    i.setCodigo(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public ItemReceituario read(Integer codigo) throws SQLException {
        String sql = SELECT_BASE + "where ir.codigo = ?";

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
    public void update(ItemReceituario i) throws SQLException {
        String sql = "update item_receituario set dosagem = ?, intervalo_entre_doses = ?, observacao = ?, medicamento_codigo = ? " +
                     "where codigo = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, i.getDosagem());
            pstm.setInt(2, i.getIntervaloEntreDoses());
            pstm.setString(3, i.getObservacao());
            pstm.setInt(4, i.getMedicamentoCodigo());
            pstm.setInt(5, i.getCodigo());

            pstm.execute();
        }
    }

    @Override
    public void delete(Integer codigo) throws SQLException {
        String sql = "delete from item_receituario where codigo = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, codigo);
            pstm.execute();
        }
    }

    @Override
    public List<ItemReceituario> readAll() throws SQLException {
        String sql = SELECT_BASE;
        List<ItemReceituario> itens = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                itens.add(map(rs));
            }
        }

        return itens;
    }

    /*
     * Filter Area
     */
    public List<ItemReceituario> findByReceituario(Integer receituarioCodigo) throws SQLException {
        String sql = SELECT_BASE + "where ir.receituario_codigo = ?";
        List<ItemReceituario> itens = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, receituarioCodigo);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    itens.add(map(rs));
                }
            }
        }

        return itens;
    }
}