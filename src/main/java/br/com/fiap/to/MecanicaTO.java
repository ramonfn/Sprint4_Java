package br.com.fiap.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Pattern;

public class MecanicaTO {

    @NotBlank private String nm_mecanico;

    @NotNull @PositiveOrZero private int nr_logradouro;

    @NotBlank private String nm_logradouro;

    @NotNull @PositiveOrZero private int nr_cep;

    // Construtor vazio
    public MecanicaTO() {}

    // Construtor com parâmetros
    public MecanicaTO(@NotBlank String nm_mecanico, @NotNull @PositiveOrZero int nr_logradouro, @NotBlank String nm_logradouro, @NotNull @PositiveOrZero int nr_cep) {
        this.nm_mecanico = nm_mecanico;
        this.nr_logradouro = nr_logradouro;
        this.nm_logradouro = nm_logradouro;
        this.nr_cep = nr_cep;
    }

    // Getters e Setters
    public String getNm_mecanico() {
        return nm_mecanico;
    }

    public void setNm_mecanico(String nm_mecanico) {
        this.nm_mecanico = nm_mecanico;
    }

    public int getNr_logradouro() {
        return nr_logradouro;
    }

    public void setNr_logradouro(int nr_logradouro) {
        this.nr_logradouro = nr_logradouro;
    }

    public String getNm_logradouro() {
        return nm_logradouro;
    }

    public void setNm_logradouro(String nm_logradouro) {
        this.nm_logradouro = nm_logradouro;
    }

    public int getNr_cep() {
        return nr_cep;
    }

    public void setNr_cep(int nr_cep) {
        this.nr_cep = nr_cep;
    }
}
