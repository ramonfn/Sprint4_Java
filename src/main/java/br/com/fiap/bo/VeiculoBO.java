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
}
