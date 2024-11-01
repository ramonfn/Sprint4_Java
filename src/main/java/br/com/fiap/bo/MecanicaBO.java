package br.com.fiap.bo;

import br.com.fiap.dao.MecanicaDAO;
import br.com.fiap.to.MecanicaTO;

import java.util.ArrayList;

public class MecanicaBO {
    private MecanicaDAO mecanicaDAO;

    public MecanicaBO() {
        this.mecanicaDAO = new MecanicaDAO();
    }

    public ArrayList<MecanicaTO> findAll() {
        return mecanicaDAO.findAll();
    }

    public void addMecanico(MecanicaTO mecanico) throws IllegalArgumentException {
        validateMecanico(mecanico);
        String nomeMecanico = mecanico.getNm_mecanico().trim();
        if (mecanicaDAO.findByNm_mecanico(nomeMecanico) != null) {
            throw new IllegalArgumentException("Mecânico já existe com o nome informado.");
        }
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
        if (!isValidCep(mecanico.getNr_cep())) {
            throw new IllegalArgumentException("CEP inválido. Verifique se está no formato correto.");
        }
    }

    public MecanicaTO findByNm_mecanico(String nm_mecanico) {
        if (nm_mecanico == null || nm_mecanico.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do mecânico não pode ser vazio.");
        }
        System.out.println("Buscando mecânico: '" + nm_mecanico.trim() + "'");
        MecanicaTO mecanica = mecanicaDAO.findByNm_mecanico(nm_mecanico.trim());
        if (mecanica == null) {
            throw new IllegalArgumentException("Mecânico não encontrado com o nome informado.");
        }
        return mecanica;
    }

    private boolean isValidCep(int cep) {
        String cepStr = String.valueOf(cep);

        if (cepStr.length() != 8) {
            return false;
        }

        if (!cepStr.matches("\\d{8}")) {
            return false;
        }

        if (cepStr.startsWith("0")) {
            return false;
        }

        return true;
    }

    public MecanicaTO save(MecanicaTO mecanico) {
        validateMecanico(mecanico);
        String nomeMecanico = mecanico.getNm_mecanico().trim(); // Trimming aqui também
        if (mecanicaDAO.findByNm_mecanico(nomeMecanico) != null) {
            throw new IllegalArgumentException("Mecânico já existe com o nome informado.");
        }
        MecanicaTO savedMecanico = mecanicaDAO.save(mecanico);
        if (savedMecanico == null) {
            throw new IllegalArgumentException("Erro ao salvar o mecânico. Tente novamente.");
        }
        return savedMecanico;
    }

    public boolean delete(String nomeMecanico) {
        if (nomeMecanico == null || nomeMecanico.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do mecânico não pode ser vazio.");
        }
        System.out.println("Tentando excluir o mecânico: " + nomeMecanico.trim());
        MecanicaTO mecanico = mecanicaDAO.findByNm_mecanico(nomeMecanico.trim());
        if (mecanico == null) {
            throw new IllegalArgumentException("Mecânico não encontrado com o nome informado.");
        }

        return mecanicaDAO.delete(nomeMecanico.trim());
    }
    public boolean updateMecanico(MecanicaTO mecanico) throws IllegalArgumentException {
        validateMecanico(mecanico); // Valida os dados do mecânico
        MecanicaTO existingMecanico = mecanicaDAO.findByNm_mecanico(mecanico.getNm_mecanico());

        if (existingMecanico == null) {
            throw new IllegalArgumentException("Mecânico não encontrado com o nome informado.");
        }

        return mecanicaDAO.update(mecanico);
    }
}