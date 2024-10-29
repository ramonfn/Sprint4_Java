package br.com.fiap.dao;

import br.com.fiap.to.MecanicaTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class MecanicaDAO extends Repository {
    public ArrayList<MecanicaTO> findAll() {
        ArrayList<MecanicaTO> mecanicas = new ArrayList<MecanicaTO>();
        String sql = "SELECT * FROM MECANICA ORDER BY NM_MECANICO";
        try (PreparedStatement ps = getConnection().prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    MecanicaTO mecanica = new MecanicaTO();
                    mecanica.setNm_mecanico(rs.getString("Nome"));
                    mecanica.setNr_logradouro(rs.getInt("Numero_logradouro"));
                    mecanica.setNm_logradouro(rs.getString("Nome_logradouro"));
                    mecanica.setNr_cep(rs.getInt("Numero_cep"));
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
}