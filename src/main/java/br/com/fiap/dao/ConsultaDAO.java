package br.com.fiap.dao;

import br.com.fiap.to.ConsultaTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
    public ConsultaTO findByDataHora(LocalDate data, LocalTime hora) {
        ConsultaTO consulta = new ConsultaTO();
        String sql = "SELECT * FROM CONSULTA WHERE DATA, HORA = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql))
        {
            ps.setDate(1, Date.valueOf(data));
            ps.setTime(2, Time.valueOf(hora));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                consulta.setMotivo(rs.getString("Motivo"));
                consulta.setData(rs.getDate("Data").toLocalDate());
                consulta.setHora(rs.getTime("Hora").toLocalTime());
                consulta.setLocal(rs.getString("Local"));
            }else{
                return null;
            }
        }catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        }finally {
            closeConnection();
        }
        return consulta;
    }
}


