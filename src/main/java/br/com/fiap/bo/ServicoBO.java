package br.com.fiap.bo;

import br.com.fiap.dao.ServicoDAO;
import br.com.fiap.dao.VeiculoDAO;
import br.com.fiap.to.ServicoTO;
import br.com.fiap.to.VeiculoTO;

import java.time.LocalDate;
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
        if (servicoDAO.findById_servico(servico.getId_servico()) != null) {
            throw new IllegalArgumentException("Já existe um serviço cadastrado com este ID.");
        }
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
        if (servico.getDt_servico().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data do serviço não pode ser no passado.");
        }
    }
    public ServicoTO findById_servico(String id_servico) {
        servicoDAO = new ServicoDAO();
        if (id_servico == null || id_servico.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do serviço não pode ser vazio.");
        }

        ServicoTO servico = servicoDAO.findById_servico(id_servico);
        if (servico == null) {
            throw new IllegalArgumentException("Serviço não encontrado com o ID informado.");
        }
        return servico;
    }
    public ServicoTO save(ServicoTO servico){
        servicoDAO = new ServicoDAO();
        validateServico(servico);
        if (servicoDAO.findById_servico(servico.getId_servico()) != null) {
            throw new IllegalArgumentException("Já existe um serviço cadastrado com este ID.");
        }
        ServicoTO savedServico = servicoDAO.save(servico);
        if (savedServico == null) {
            throw new IllegalArgumentException("Erro ao salvar o serviço. Tente novamente.");
        }
        return savedServico;
    }
}

