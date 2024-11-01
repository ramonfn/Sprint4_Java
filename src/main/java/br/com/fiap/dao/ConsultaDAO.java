package br.com.fiap.dao;

import br.com.fiap.to.ConsultaTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
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

    public ConsultaTO findByMotivo(String motivo) {
        ConsultaTO consulta = null;
        String sql = "SELECT * FROM CONSULTA WHERE TRIM(MOTIVO) = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, motivo);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                consulta = new ConsultaTO();
                consulta.setMotivo(rs.getString(1));
                consulta.setData(rs.getDate(2).toLocalDate());
                consulta.setHora(rs.getString(3));
                consulta.setLocal(rs.getString(4));
            } else {
                System.out.println("Nenhuma consulta encontrada para o motivo informado: " + motivo);
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
            ps.setInt(3, Integer.parseInt(consulta.getHora())); // Converte a String para int
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



    public boolean deleteByMotivo(String motivo) {
        String sql = "DELETE FROM CONSULTA WHERE TRIM(MOTIVO) = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, motivo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean update(ConsultaTO consulta) {
        String sql = "UPDATE CONSULTA SET DATA = ?, HORA = ?, LOCAL = ? WHERE MOTIVO = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(consulta.getData()));
            ps.setInt(2, Integer.parseInt(consulta.getHora())); // Converte a String para int
            ps.setString(3, consulta.getLocal());
            ps.setString(4, consulta.getMotivo());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar consulta: " + e.getMessage());
        }
        return false;
    }


    private boolean isValidDateFormat(String date) {
        return date.matches("\\d{4}/\\d{2}/\\d{2}");
    }
    private boolean isValidTimeFormat(String time) {
        return time.matches("\\d{6}");
    }
    public int deleteByCliente(String nm_cliente) {
        String sql = "DELETE FROM CONSULTA WHERE NR_CPF_CLIENTE = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, nm_cliente);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao excluir consultas do cliente: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return 0;
    }
}