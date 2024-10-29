package br.com.fiap.bo;

import br.com.fiap.dao.MecanicaDAO;
import br.com.fiap.to.MecanicaTO;

import java.util.ArrayList;

public class MecanicaBO {
    private MecanicaDAO mecanicaDAO;

    public ArrayList<MecanicaTO> findAll() {
        mecanicaDAO = new MecanicaDAO();
        return mecanicaDAO.findAll();
    }

    public void addMecanico(MecanicaTO mecanico) throws IllegalArgumentException {
        validateMecanico(mecanico);
        mecanicaDAO = new MecanicaDAO();
    }

    private void validateMecanico(MecanicaTO mecanico) throws IllegalArgumentException {
        if (mecanico.getNm_mecanico() == null || mecanico.getNm_mecanico().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do mecânico não pode ser vazio.");
        }
        if (mecanico.getNr_logradouro() <= 0) {
            throw new IllegalArgumentException("Número do logradouro deve ser maior que zero.");
        }
        if (mecanico.getNm_logradouro() == null || mecanico.getNm_logradouro().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do logradouro não pode ser vazio.");
        }
        if (String.valueOf(mecanico.getNr_cep()).length() != 8) {
            throw new IllegalArgumentException("CEP inválido. Deve conter exatamente 8 dígitos.");
        }
    }
}
