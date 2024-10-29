package br.com.fiap.bo;

import br.com.fiap.dao.VeiculoDAO;
import br.com.fiap.to.VeiculoTO;

import java.util.ArrayList;

public class VeiculoBO {
    private VeiculoDAO veiculoDAO;

    public ArrayList<VeiculoTO> findAll() {
        veiculoDAO = new VeiculoDAO();
        return veiculoDAO.findAll();
    }

    public void addVeiculo(VeiculoTO veiculo) throws IllegalArgumentException {
        validateVeiculo(veiculo);
        veiculoDAO = new VeiculoDAO();
        if (veiculoDAO.findById_veiculo(veiculo.getId_veiculo()) != null) {
            throw new IllegalArgumentException("Já existe um veículo cadastrado com este ID.");
        }
    }

    private void validateVeiculo(VeiculoTO veiculo) throws IllegalArgumentException {
        if (veiculo.getId_veiculo() == null || veiculo.getId_veiculo().trim().isEmpty()) {
            throw new IllegalArgumentException("ID do veículo não pode ser vazio.");
        }
        if (veiculo.getMarca() == null || veiculo.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("Marca do veículo não pode ser vazia.");
        }
        if (veiculo.getModelo() == null || veiculo.getModelo().trim().isEmpty()) {
            throw new IllegalArgumentException("Modelo do veículo não pode ser vazio.");
        }
        if (veiculo.getAno_fabricacao() == null || String.valueOf(veiculo.getAno_fabricacao()).length() != 4) {
            throw new IllegalArgumentException("Ano de fabricação inválido. Deve conter exatamente 4 dígitos.");
        }
    }
    public VeiculoTO findById_veiculo(String id_veiculo){
        veiculoDAO = new VeiculoDAO();
        if (id_veiculo == null || id_veiculo.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do veículo não pode ser vazio.");
        }
        VeiculoTO veiculo = veiculoDAO.findById_veiculo(id_veiculo);
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não encontrado com o ID informado.");
        }
        return veiculo;
    }
    public VeiculoTO save(VeiculoTO veiculo){
        veiculoDAO = new VeiculoDAO();
        validateVeiculo(veiculo);
        if (veiculoDAO.findById_veiculo(veiculo.getId_veiculo()) != null) {
            throw new IllegalArgumentException("Já existe um veículo cadastrado com este ID.");
        }
        VeiculoTO savedVeiculo = veiculoDAO.save(veiculo);
        if (savedVeiculo == null) {
            throw new IllegalArgumentException("Erro ao salvar o veículo. Tente novamente.");
        }
        return savedVeiculo;
    }
}
