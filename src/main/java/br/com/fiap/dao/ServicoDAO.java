package br.com.fiap.dao;

import br.com.fiap.to.ServicoTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class ServicoDAO extends Repository {
    public ArrayList<ServicoTO> findAll() {
        ArrayList<ServicoTO> servicos = new ArrayList<ServicoTO>();
        String sql = "SELECT * FROM SERVICO ORDER BY ID_SERVICO";
        try (PreparedStatement ps = getConnection().prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    ServicoTO servico = new ServicoTO();
                    servico.setId_servico(rs.getString("Id_servico"));
                    servico.setDc_servico(rs.getString("Descrição_servico"));
                    servico.setPr_servico(rs.getInt("Preço_servico"));
                    servico.setDt_servico(rs.getDate("Data_servico").toLocalDate());
                    servicos.add(servico);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return servicos;
    }
}