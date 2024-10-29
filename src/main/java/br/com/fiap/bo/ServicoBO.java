package br.com.fiap.bo;

import br.com.fiap.dao.ServicoDAO;
import br.com.fiap.to.ServicoTO;

import java.util.ArrayList;

public class ServicoBO {
    private ServicoDAO servicoDAO;

    public ArrayList<ServicoTO> findAll() {
        servicoDAO = new ServicoDAO();
        return servicoDAO.findAll();
    }

    public void addServico(ServicoTO servico) throws IllegalArgumentException {
        validateServico(servico);
        servicoDAO = new ServicoDAO();
    }

    private void validateServico(ServicoTO servico) throws IllegalArgumentException {
        if (servico.getId_servico() == null || servico.getId_servico().trim().isEmpty()) {
            throw new IllegalArgumentException("ID do serviço não pode ser vazio.");
        }
        if (servico.getDc_servico() == null || servico.getDc_servico().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do serviço não pode ser vazia.");
        }
        if (servico.getPr_servico() <= 0) {
            throw new IllegalArgumentException("Preço do serviço deve ser maior que zero.");
        }
        if (servico.getDt_servico() == null) {
            throw new IllegalArgumentException("Data do serviço não pode ser nula.");
        }
    }
}

