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
                consulta.setHora(rs.getString(3)); // Mantém como String
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

    public ConsultaTO findByDataHora(LocalDate data, String hora) { // Mudança para String
        ConsultaTO consulta = null; // Inicialize como null
        String sql = "SELECT * FROM CONSULTA WHERE DATA = ? AND HORA = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            // Define os parâmetros da consulta
            ps.setDate(1, Date.valueOf(data));
            ps.setString(2, hora); // Passa a hora como String

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
            ps.setString(3, consulta.getHora()); // Passa a hora como String
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

    public boolean delete(LocalDate data, String hora) {
        String sql = "DELETE FROM CONSULTA WHERE DATA = ? AND HORA = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(data));
            ps.setString(2, hora); // Mantém como String
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return false;
    }

    public int deleteByCliente(String nm_cliente) {
        String sql = "DELETE FROM CONSULTA WHERE NR_CPF_CLIENTE = ?"; // Ajuste o nome da coluna conforme necessário
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, nm_cliente);
            ps.executeUpdate(); // Executa a exclusão das consultas associadas
        } catch (SQLException e) {
            System.out.println("Erro ao excluir consultas do cliente: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return 0;
    }
}
