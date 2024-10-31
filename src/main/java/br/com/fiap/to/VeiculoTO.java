package br.com.fiap.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class VeiculoTO {

    @NotBlank private String id_veiculo;

    @NotBlank private String marca;

    @NotBlank private String modelo;

    @NotBlank private String ano_fabricacao;

    // Construtor vazio
    public VeiculoTO() {}

    // Construtor com parâmetros
    public VeiculoTO(@NotBlank String id_veiculo, @NotBlank String marca, @NotBlank String modelo, @NotBlank String ano_fabricacao) {
        this.id_veiculo = id_veiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.ano_fabricacao = ano_fabricacao;
    }

    // Getters e Setters
    public String getId_veiculo() {
        return id_veiculo;
    }

    public void setId_veiculo(String id_veiculo) {
        this.id_veiculo = id_veiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno_fabricacao() {
        return ano_fabricacao;
    }

    public void setAno_fabricacao(String ano_fabricacao) {
        this.ano_fabricacao = ano_fabricacao;
    }
}
