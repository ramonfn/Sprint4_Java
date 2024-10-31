package br.com.fiap.to;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.PastOrPresent;

public class ServicoTO {

    @NotBlank private String id_servico;

    @NotBlank private String dc_servico;

    @NotNull @PositiveOrZero private int pr_servico;

    @NotNull @PastOrPresent private LocalDate dt_servico;

    // Construtor vazio
    public ServicoTO() {}

    // Construtor com parâmetros
    public ServicoTO(@NotBlank String id_servico, @NotBlank String dc_servico, @NotNull @PositiveOrZero int pr_servico, @NotNull @PastOrPresent LocalDate dt_servico) {
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
