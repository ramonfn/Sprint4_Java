package br.com.fiap.dao;

import br.com.fiap.to.ClienteTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO extends Repository {
    public ArrayList<ClienteTO> findAll() {
        ArrayList<ClienteTO> clientes = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTE ORDER BY NR_CPF";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ClienteTO cliente = new ClienteTO();
                cliente.setNm_cliente(rs.getString(1));
                cliente.setNr_cpf(rs.getString(2));
                cliente.setNr_rg(rs.getString(3));
                cliente.setDt_nascimento(rs.getDate(4).toLocalDate());
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta (ClienteDAO): " + e.getMessage());
        } finally {
            closeConnection();
        }
        return clientes;
    }

    public ClienteTO findByNr_cpf(String nr_cpf) {
        ClienteTO cliente = null;
        String sql = "SELECT * FROM CLIENTE WHERE NR_CPF = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, nr_cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cliente = new ClienteTO();
                cliente.setNm_cliente(rs.getString(1));
                cliente.setNr_cpf(rs.getString(2));
                cliente.setNr_rg(rs.getString(3));
                cliente.setDt_nascimento(rs.getDate(4).toLocalDate());
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta (ClienteDAO): " + e.getMessage());
        } finally {
            closeConnection();
        }
        return cliente;
    }

    public ClienteTO save(ClienteTO cliente) {
        // Validação do cliente
        if (cliente == null ||
                cliente.getNm_cliente() == null ||
                cliente.getNm_cliente().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio.");
        }

        String sql = "INSERT INTO CLIENTE (NM_CLIENTE, NR_CPF, NR_RG, DT_NASCIMENTO) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            System.out.println("Tentando salvar cliente: " + cliente.getNm_cliente());

            ps.setString(1, cliente.getNm_cliente());
            ps.setString(2, cliente.getNr_cpf());
            ps.setString(3, cliente.getNr_rg());
            ps.setDate(4, java.sql.Date.valueOf(cliente.getDt_nascimento()));

            if (ps.executeUpdate() > 0) {
                return cliente;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar cliente: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return null;
    }

    public boolean delete(String nr_cpf) {
        String sql = "DELETE FROM CLIENTE WHERE NR_CPF = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, nr_cpf);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return false;
    }
    public boolean update(ClienteTO cliente) {
        if (cliente == null ||
                cliente.getNr_cpf() == null ||
                cliente.getNr_cpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF do cliente não pode ser vazio.");
        }

        String sql = "UPDATE CLIENTE SET NM_CLIENTE = ?, NR_RG = ?, DT_NASCIMENTO = ? WHERE NR_CPF = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, cliente.getNm_cliente());
            ps.setString(2, cliente.getNr_rg());
            ps.setDate(3, java.sql.Date.valueOf(cliente.getDt_nascimento()));
            ps.setString(4, cliente.getNr_cpf());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return false;
    }


}
