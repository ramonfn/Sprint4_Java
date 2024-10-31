package br.com.fiap.dao;

import br.com.fiap.to.ClienteTO;
import br.com.fiap.to.MecanicaTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class MecanicaDAO extends Repository {
    public ArrayList<MecanicaTO> findAll() {
        ArrayList<MecanicaTO> mecanicas = new ArrayList<MecanicaTO>();
        String sql = "SELECT * FROM MECANICA ORDER BY NM_MECANICO";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    MecanicaTO mecanica = new MecanicaTO();
                    mecanica.setNm_mecanico(rs.getString(1));
                    mecanica.setNr_logradouro(rs.getInt(2));
                    mecanica.setNm_logradouro(rs.getString(3));
                    mecanica.setNr_cep(rs.getInt(4));
                    mecanicas.add(mecanica);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return mecanicas;
    }

    public MecanicaTO findByNm_mecanico(String nm_mecanico) {
        MecanicaTO mecanica = null; // Inicializa como null
        String sql = "SELECT * FROM MECANICA WHERE NM_MECANICO LIKE ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, nm_mecanico.trim() + "%"); // Utiliza o padrão LIKE
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                mecanica = new MecanicaTO();
                mecanica.setNm_mecanico(rs.getString(1));
                mecanica.setNr_logradouro(rs.getInt(2));
                mecanica.setNm_logradouro(rs.getString(3));
                mecanica.setNr_cep(rs.getInt(4));
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return mecanica; // Retorna null se não encontrar
    }

    public MecanicaTO save(MecanicaTO mecanica) {
        String sql = "INSERT INTO MECANICA (NM_MECANICO, NR_LOGRADOURO, NM_LOGRADOURO, NR_CEP) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, mecanica.getNm_mecanico());
            ps.setInt(2, mecanica.getNr_logradouro());
            ps.setString(3, mecanica.getNm_logradouro());
            ps.setInt(4, mecanica.getNr_cep());
            if (ps.executeUpdate() > 0) {
                return mecanica;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar mecânica: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return null;
    }
}
