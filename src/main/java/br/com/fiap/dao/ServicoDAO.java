package br.com.fiap.dao;

import br.com.fiap.to.ClienteTO;
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
                    servico.setId_servico(rs.getString(1));
                    servico.setDc_servico(rs.getString(2));
                    servico.setPr_servico(rs.getInt(3));
                    servico.setDt_servico(rs.getDate(4).toLocalDate());
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
    public ServicoTO findById_servico(String id_servico) {
        ServicoTO servico = null;
        String sql = "SELECT * FROM SERVICO WHERE ID_SERVICO LIKE ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, id_servico.trim() + "%"); // Utilizando trim aqui também
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                servico = new ServicoTO();
                servico.setId_servico(rs.getString(1));
                servico.setDc_servico(rs.getString(2));
                servico.setPr_servico(rs.getInt(3));
                servico.setDt_servico(rs.getDate(4).toLocalDate());
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return servico;
    }
    public ServicoTO save(ServicoTO servico) {
        String sql = "INSERT INTO SERVICO (ID_SERVICO, DC_SERVICO, PR_SERVICO, DT_SERVICO) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, servico.getId_servico());
            ps.setString(2, servico.getDc_servico());
            ps.setInt(3, servico.getPr_servico());
            ps.setDate(4, java.sql.Date.valueOf(servico.getDt_servico()));
            if (ps.executeUpdate() > 0) {
                return servico;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar serviço: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return null;
    }
    public boolean delete(String id_servico) {
        String sql = "DELETE FROM SERVICO WHERE TRIM(ID_SERVICO) = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, id_servico.trim());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return false;
    }
}
