package br.com.fiap.bo;

import br.com.fiap.dao.ClienteDAO;
import br.com.fiap.to.ClienteTO;

import java.util.ArrayList;

public class ClienteBO {
    private ClienteDAO clienteDAO;

    public ArrayList<ClienteTO> findAll() {
        clienteDAO = new ClienteDAO();
        return clienteDAO.findAll();
    }

    public void addCliente(ClienteTO cliente) throws IllegalArgumentException {
        validateCliente(cliente);
        clienteDAO = new ClienteDAO();
    }

    private void validateCliente(ClienteTO cliente) throws IllegalArgumentException {
        if (cliente.getNm_cliente() == null || cliente.getNm_cliente().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio.");
        }
        if (cliente.getNr_cpf() == null || String.valueOf(cliente.getNr_cpf()).length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter exatamente 11 dígitos.");
        }
        if (cliente.getNr_rg() == null || String.valueOf(cliente.getNr_rg()).length() < 8 || String.valueOf(cliente.getNr_rg()).length() > 15) {
            throw new IllegalArgumentException("RG inválido. Deve conter entre 8 e 15 dígitos.");
        }
        if (cliente.getDt_nascimento() == null) {
            throw new IllegalArgumentException("Data de nascimento não pode ser nula.");
        }
    }
}
