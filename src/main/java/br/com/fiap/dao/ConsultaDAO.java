package br.com.fiap.dao;

import br.com.fiap.to.ConsultaTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;


public class ConsultaDAO extends Repository {
    public ArrayList<ConsultaTO> findAll() {
        ArrayList<ConsultaTO> consultas = new ArrayList<ConsultaTO>();
        String sql = "SELECT * FROM CONSULTA ORDER BY DATA, HORA";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    ConsultaTO consulta = new ConsultaTO();
                    consulta.setMotivo(rs.getString(1));
                    consulta.setData(rs.getDate(2).toLocalDate());
                    String horaConsulta = rs.getString(3);
                    consulta.setHora(horaConsulta);
                    consulta.setLocal(rs.getString(4));
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

    public ConsultaTO findByDataHora(LocalDate data, String hora) {
        ConsultaTO consulta = new ConsultaTO();
        String sql = "SELECT * FROM CONSULTA WHERE DATA = ? AND HORA = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(data));
            ps.setTime(2, Time.valueOf(hora));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                consulta.setMotivo(rs.getString(1));
                consulta.setData(rs.getDate(2).toLocalDate());

                String horaConsulta = rs.getString(3);
                consulta.setHora(horaConsulta);

                consulta.setLocal(rs.getString(4));
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return consulta;
    }
    public ConsultaTO save(ConsultaTO consulta) {
        String sql = "INSERT INTO CONSULTA (MOTIVO, DATA, HORA, LOCAL) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, consulta.getMotivo());
            ps.setDate(2, Date.valueOf(consulta.getData()));
            ps.setTime(3, Time.valueOf(consulta.getHora()));
            ps.setString(4, consulta.getLocal());
            if (ps.executeUpdate() > 0) {
                return consulta;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar consulta: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return null;
    }
}
