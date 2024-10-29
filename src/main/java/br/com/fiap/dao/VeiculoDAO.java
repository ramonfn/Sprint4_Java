package br.com.fiap.dao;

import br.com.fiap.to.VeiculoTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class VeiculoDAO extends Repository {
    public ArrayList<VeiculoTO> findAll() {
        ArrayList<VeiculoTO> veiculos = new ArrayList<VeiculoTO>();
        String sql = "SELECT * FROM VEICULO ORDER BY ID_VEICULO";
        try (PreparedStatement ps = getConnection().prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    VeiculoTO veiculo = new VeiculoTO();
                    veiculo.setId_veiculo(rs.getString("Id_veiculo"));
                    veiculo.setMarca(rs.getString("Marca"));
                    veiculo.setModelo(rs.getString("Modelo"));
                    veiculo.setAno_fabricacao(rs.getDate("Ano_de_fabricacao").toLocalDate());
                    veiculos.add(veiculo);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return veiculos;
    }
}