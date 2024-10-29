package br.com.fiap.dao;

import br.com.fiap.to.ClienteTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO extends Repository {
    public ArrayList<ClienteTO> findAll() {
        ArrayList<ClienteTO> clientes = new ArrayList<ClienteTO>();
        String sql = "SELECT * FROM CLIENTE ORDER BY NR_CPF";
        try (PreparedStatement ps = getConnection().prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    ClienteTO cliente = new ClienteTO();
                    cliente.setNm_cliente(rs.getString("Nome"));
                    cliente.setNr_cpf(rs.getString("CPF"));
                    cliente.setNr_rg(rs.getString("RG"));
                    cliente.setDt_nascimento(rs.getDate("Data_de_nascimento").toLocalDate());
                    clientes.add(cliente);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return clientes;
    }
    public ClienteTO findByNr_cpf(String nr_cpf) {
        ClienteTO cliente = new ClienteTO();
        String sql = "SELECT * FROM CLIENTE WHERE NR_CPF = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql))
        {
            ps.setString(1, nr_cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cliente.setNm_cliente(rs.getString("Nome"));
                cliente.setNr_cpf(rs.getString("CPF"));
                cliente.setNr_rg(rs.getString("RG"));
                cliente.setDt_nascimento(rs.getDate("Data_de_nascimento").toLocalDate());
            }else{
                return null;
            }
    }catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        }finally {
            closeConnection();
        }
        return cliente;
    }
}

