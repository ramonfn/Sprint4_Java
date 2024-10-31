package br.com.fiap.bo;

import br.com.fiap.dao.ConsultaDAO;
import br.com.fiap.to.ConsultaTO;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConsultaBO {
    private ConsultaDAO consultaDAO;

    public ArrayList<ConsultaTO> findAll() {
        consultaDAO = new ConsultaDAO();
        return consultaDAO.findAll();
    }

    public void addConsulta(ConsultaTO consulta) throws IllegalArgumentException {
        validateConsulta(consulta);
        consultaDAO = new ConsultaDAO();
        if (consultaDAO.findByDataHora(consulta.getData(), consulta.getHora()) != null) {
            throw new IllegalArgumentException("Já existe uma consulta agendada para a mesma data e hora.");
        }
        consultaDAO.save(consulta);  // Salva a consulta no banco de dados
    }

    private void validateConsulta(ConsultaTO consulta) throws IllegalArgumentException {
        if (consulta.getMotivo() == null || consulta.getMotivo().trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo da consulta não pode ser vazio.");
        }
        if (consulta.getData() == null) {
            throw new IllegalArgumentException("Data da consulta não pode ser nula.");
        }
        if (consulta.getHora() == null || consulta.getHora().trim().isEmpty()) {
            throw new IllegalArgumentException("Hora da consulta não pode ser nula ou vazia.");
        }
        if (consulta.getLocal() == null || consulta.getLocal().trim().isEmpty()) {
            throw new IllegalArgumentException("Local da consulta não pode ser vazio.");
        }
        if (consulta.getData().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data da consulta não pode ser no passado.");
        }
        // O formato da hora será validado na normalização
    }

    public ConsultaTO findByDataHora(LocalDate data, String hora) throws IllegalArgumentException {
        consultaDAO = new ConsultaDAO();

        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula.");
        }

        String normalizedHour = normalizeHour(hora); // Normaliza a hora

        // Chamando o DAO para encontrar a consulta
        ConsultaTO consulta = consultaDAO.findByDataHora(data, normalizedHour);
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada para a data e hora informadas.");
        }
        return consulta;
    }

    private String normalizeHour(String hora) {
        if (hora == null || hora.trim().isEmpty()) {
            throw new IllegalArgumentException("Hora não pode ser nula ou vazia.");
        }
        hora = hora.replaceAll("\\s+", "");

        if (hora.matches("\\d{2}:\\d{2}")) {
            return hora.replace(":", "") + "00"; // Remove ":" e adiciona segundos
        }

        return hora; // Retorna a hora se já estiver no formato correto
    }

    public ConsultaTO save(ConsultaTO consulta) {
        consultaDAO = new ConsultaDAO();
        validateConsulta(consulta);

        String normalizedHour = normalizeHour(consulta.getHora()); // Normaliza a hora antes de verificar

        if (consultaDAO.findByDataHora(consulta.getData(), normalizedHour) != null) {
            throw new IllegalArgumentException("Já existe uma consulta agendada para a mesma data e hora.");
        }

        ConsultaTO savedConsulta = consultaDAO.save(consulta);
        if (savedConsulta == null) {
            throw new IllegalArgumentException("Erro ao salvar a consulta. Tente novamente.");
        }
        return savedConsulta;
    }

    public static LocalDate convertStringToLocalDate(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(data, formatter);
    }
}
