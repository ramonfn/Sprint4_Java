package br.com.fiap.dao;

import br.com.fiap.to.ClienteTO;

import java.sql.Date;
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
                    cliente.setNm_cliente(rs.getString(1));
                    cliente.setNr_cpf(rs.getString(2));
                    cliente.setNr_rg(rs.getString(3));
                    cliente.setDt_nascimento(rs.getDate(4).toLocalDate());
                    clientes.add(cliente);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta (ClienteDAO): " + e.getMessage());
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
                cliente.setNm_cliente(rs.getString(1));
                cliente.setNr_cpf(rs.getString(2));
                cliente.setNr_rg(rs.getString(3));
                cliente.setDt_nascimento(rs.getDate(4).toLocalDate());
            }else{
                return null;
            }
    }catch (SQLException e) {
            System.out.println("Erro na consulta (ClienteDAO): " + e.getMessage());
        }finally {
            closeConnection();
        }
        return cliente;
    }
    public ClienteTO save(ClienteTO cliente) {
        String sql = "INSERT INTO CLIENTE (NM_CLIENTE, NR_CPF, NR_RG, DT_NASCIMENTO) values( ?, ?, ?, ?) ";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, cliente.getNm_cliente());
            ps.setString(2, cliente.getNr_cpf());
            ps.setString(3, cliente.getNr_rg());
            ps.setDate(4, Date.valueOf(cliente.getDt_nascimento()));
            if (ps.executeUpdate() > 0) {
                return cliente;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return null;
    }
}

