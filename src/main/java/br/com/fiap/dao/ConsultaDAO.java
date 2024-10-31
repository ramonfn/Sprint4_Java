package br.com.fiap.dao;

import br.com.fiap.to.ConsultaTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class ConsultaDAO extends Repository {
    public ArrayList<ConsultaTO> findAll() {
        ArrayList<ConsultaTO> consultas = new ArrayList<>();
        String sql = "SELECT * FROM CONSULTA ORDER BY DATA, HORA";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ConsultaTO consulta = new ConsultaTO();
                consulta.setMotivo(rs.getString(1));
                consulta.setData(rs.getDate(2).toLocalDate());
                consulta.setHora(rs.getString(3)); // Mantenha como String
                consulta.setLocal(rs.getString(4));
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return consultas;
    }

    public ConsultaTO findByDataHora(LocalDate data, String hora) {
        ConsultaTO consulta = null; // Inicialize como null
        String sql = "SELECT * FROM CONSULTA WHERE DATA = ? AND HORA = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            // Define os parâmetros da consulta
            ps.setDate(1, Date.valueOf(data));
            ps.setString(2, hora); // Passe a hora como String

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                consulta = new ConsultaTO();
                consulta.setMotivo(rs.getString(1)); // Supondo que o motivo é a primeira coluna
                consulta.setData(rs.getDate(2).toLocalDate()); // A segunda coluna é a data
                consulta.setHora(rs.getString(3)); // A terceira coluna é a hora
                consulta.setLocal(rs.getString(4)); // A quarta coluna é o local
            } else {
                // Caso não encontre a consulta
                System.out.println("Nenhuma consulta encontrada para a data e hora informadas.");
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

            // Converter a hora de String para um formato numérico
            int horas = Integer.parseInt(consulta.getHora().substring(0, 2)); // Extrair as horas
            int minutos = Integer.parseInt(consulta.getHora().substring(3, 5)); // Extrair os minutos
            int horaNumerica = horas * 100 + minutos; // Converter para formato HHmm

            ps.setInt(3, horaNumerica); // Passar a hora como inteiro
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
