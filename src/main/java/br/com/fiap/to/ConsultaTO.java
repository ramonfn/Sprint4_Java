package br.com.fiap.to;

import java.time.LocalDate;
import jakarta.json.bind.annotation.JsonbDateFormat;

public class ConsultaTO {
    private String motivo;
    @JsonbDateFormat("yyyy/MM/dd")
    private LocalDate data;
    private String hora;
    private String local;

    // Construtor vazio
    public ConsultaTO() {
    }

    // Construtor com parâmetros
    public ConsultaTO(String motivo, LocalDate data, String hora, String local) {
        this.motivo = motivo;
        this.data = data;
        this.hora = hora;
        this.local = local;
    }

    // Métodos getters e setters
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
