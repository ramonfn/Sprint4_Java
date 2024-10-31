package br.com.fiap.to;

import java.time.LocalDate;

public class ClienteTO {
    private String nm_cliente;
    private String nr_cpf;
    private String nr_rg;
    private LocalDate dt_nascimento;

    // Construtor vazio
    public ClienteTO() {
    }

    // Construtor com parâmetros
    public ClienteTO(String nm_cliente, String nr_cpf, String nr_rg, LocalDate dt_nascimento) {
        this.nm_cliente = nm_cliente;
        this.nr_cpf = nr_cpf;
        this.nr_rg = nr_rg;
        this.dt_nascimento = dt_nascimento;
    }

    // Getters e Setters
    public String getNm_cliente() {
        return nm_cliente;
    }

    public void setNm_cliente(String nm_cliente) {
        this.nm_cliente = nm_cliente;
    }

    public String getNr_cpf() {
        return nr_cpf;
    }

    public void setNr_cpf(String nr_cpf) {
        this.nr_cpf = nr_cpf;
    }

    public String getNr_rg() {
        return nr_rg;
    }

    public void setNr_rg(String nr_rg) {
        this.nr_rg = nr_rg;
    }

    public LocalDate getDt_nascimento() {
        return dt_nascimento;
    }

    public void setDt_nascimento(LocalDate dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
    }
}
