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
                    veiculo.setId_veiculo(rs.getString(1));
                    veiculo.setMarca(rs.getString(2));
                    veiculo.setModelo(rs.getString(3));
                    veiculo.setAno_fabricacao(rs.getString(4));
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
    public VeiculoTO findById_veiculo(String id_veiculo) {
        VeiculoTO veiculo = new VeiculoTO();
        String sql = "SELECT * FROM VEICULO WHERE ID_VEICULO = ?";;
        try (PreparedStatement ps = getConnection().prepareStatement(sql))
        {
            ps.setString(1, id_veiculo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                veiculo.setId_veiculo(rs.getString(1));
                veiculo.setMarca(rs.getString(2));
                veiculo.setModelo(rs.getString(3));
                veiculo.setAno_fabricacao(rs.getString(4));
            }else{
                return null;
            }
        }catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        }finally {
            closeConnection();
        }
        return veiculo;
    }
    public VeiculoTO save(VeiculoTO veiculo) {
        String sql = "INSERT INTO VEICULO (ID_VEICULO, MARCA, MODELO, ANO_FABRICACAO) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, veiculo.getId_veiculo());
            ps.setString(2, veiculo.getMarca());
            ps.setString(3, veiculo.getModelo());
            ps.setString(4, veiculo.getAno_fabricacao());
            if (ps.executeUpdate() > 0) {
                return veiculo;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar veículo: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return null;
    }
}
