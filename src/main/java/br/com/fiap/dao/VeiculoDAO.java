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
        if (id_veiculo == null || id_veiculo.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do veículo não pode ser nulo ou vazio.");
        }

        VeiculoTO veiculo = null; // Inicializa como null
        String sql = "SELECT * FROM VEICULO WHERE ID_VEICULO LIKE ?"; // Utiliza o padrão LIKE
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, id_veiculo.trim() + "%"); // Adiciona % para busca parcial
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                veiculo = new VeiculoTO();
                veiculo.setId_veiculo(rs.getString(1));
                veiculo.setMarca(rs.getString(2));
                veiculo.setModelo(rs.getString(3));
                veiculo.setAno_fabricacao(rs.getString(4));
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return veiculo; // Retorna null se não encontrar
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
    public boolean delete(String id_veiculo) {
        String sql = "DELETE FROM VEICULO WHERE ID_VEICULO = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, id_veiculo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return false;
    }public int deleteByCliente(String nm_cliente) {
        String sql = "DELETE FROM VEICULO WHERE NM_MECANICO IN (SELECT NM_MECANICO FROM MECANICA WHERE NM_CLIENTE = ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, nm_cliente);
            return ps.executeUpdate(); // Retorna o número de registros excluídos
        } catch (SQLException e) {
            System.out.println("Erro ao excluir veículo do cliente: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return 0;
    }

}
