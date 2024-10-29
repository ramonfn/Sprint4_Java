package br.com.fiap.dao;

import br.com.fiap.to.ConsultaTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class ConsultaDAO extends Repository {
    public ArrayList<ConsultaTO> findAll() {
        ArrayList<ConsultaTO> consultas = new ArrayList<ConsultaTO>();
        String sql = "SELECT * FROM CONSULTA ORDER BY DATA, HORA";
        try (PreparedStatement ps = getConnection().prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    ConsultaTO consulta = new ConsultaTO();
                    consulta.setMotivo(rs.getString("Motivo"));
                    consulta.setData(rs.getDate("Data").toLocalDate());
                    consulta.setHora(rs.getTime("Hora").toLocalTime());
                    consulta.setLocal(rs.getString("Local"));
                    consultas.add(consulta);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return consultas;
    }
}
