package br.com.fiap.to;

import java.time.LocalDate;

public class ServicoTO {
    private String id_servico;
    private String dc_servico;
    private int pr_servico;
    private LocalDate dt_servico;

    // Construtor vazio
    public ServicoTO() {
    }

    // Construtor com parâmetros
    public ServicoTO(String id_servico, String dc_servico, int pr_servico, LocalDate dt_servico) {
        this.id_servico = id_servico;
        this.dc_servico = dc_servico;
        this.pr_servico = pr_servico;
        this.dt_servico = dt_servico;
    }

    // Getters e Setters
    public String getId_servico() {
        return id_servico;
    }

    public void setId_servico(String id_servico) {
        this.id_servico = id_servico;
    }

    public String getDc_servico() {
        return dc_servico;
    }

    public void setDc_servico(String dc_servico) {
        this.dc_servico = dc_servico;
    }

    public int getPr_servico() {
        return pr_servico;
    }

    public void setPr_servico(int pr_servico) {
        this.pr_servico = pr_servico;
    }

    public LocalDate getDt_servico() {
        return dt_servico;
    }

    public void setDt_servico(LocalDate dt_servico) {
        this.dt_servico = dt_servico;
    }
}
