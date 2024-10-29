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
        ServicoTO servico = new ServicoTO();
        String sql = "SELECT * FROM SERVICO WHERE ID_SERVICO = ?";;
        try (PreparedStatement ps = getConnection().prepareStatement(sql))
        {
            ps.setString(1, id_servico);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                servico.setId_servico(rs.getString(1));
                servico.setDc_servico(rs.getString(2));
                servico.setPr_servico(rs.getInt(3));
                servico.setDt_servico(rs.getDate(4).toLocalDate());
            }else{
                return null;
            }
        }catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        }finally {
            closeConnection();
        }
        return servico;
    }
}
