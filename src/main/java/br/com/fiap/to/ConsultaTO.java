package br.com.fiap.to;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import jakarta.json.bind.annotation.JsonbDateFormat;

public class ConsultaTO {

    @NotBlank private String motivo;

    @NotNull @PastOrPresent @JsonbDateFormat("yyyy/MM/dd") private LocalDate data;

    @NotBlank private String hora;

    @NotBlank private String local;

    public ConsultaTO() {}

    public ConsultaTO(@NotBlank String motivo, @NotNull @PastOrPresent LocalDate data, @NotBlank  String hora, @NotBlank String local) {
        this.motivo = motivo;
        this.data = data;
        this.hora = hora;
        this.local = local;
    }

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
