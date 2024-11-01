package br.com.fiap.bo;

import br.com.fiap.dao.ConsultaDAO;
import br.com.fiap.to.ConsultaTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ConsultaBO {
    private ConsultaDAO consultaDAO;

    public ArrayList<ConsultaTO> findAll() {
        consultaDAO = new ConsultaDAO();
        return consultaDAO.findAll();
    }

    public void addConsulta(ConsultaTO consulta) throws IllegalArgumentException {
        validateConsulta(consulta);
        consultaDAO = new ConsultaDAO();

        if (consultaDAO.findByMotivo(consulta.getMotivo()) != null) {
            throw new IllegalArgumentException("Já existe uma consulta com o mesmo motivo.");
        }

        consultaDAO.save(consulta);
    }

    public ConsultaTO findByMotivo(String motivo) throws IllegalArgumentException {
        consultaDAO = new ConsultaDAO();

        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo não pode ser nulo ou vazio.");
        }

        ConsultaTO consulta = consultaDAO.findByMotivo(motivo);
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada para o motivo informado: " + motivo);
        }
        return consulta;
    }

    public ConsultaTO save(ConsultaTO consulta) {
        consultaDAO = new ConsultaDAO();
        validateConsulta(consulta);

        if (consultaDAO.findByMotivo(consulta.getMotivo()) != null) {
            throw new IllegalArgumentException("Já existe uma consulta com o mesmo motivo.");
        }

        ConsultaTO savedConsulta = consultaDAO.save(consulta);
        if (savedConsulta == null) {
            throw new IllegalArgumentException("Erro ao salvar a consulta. Tente novamente.");
        }
        return savedConsulta;
    }

    public boolean deleteByMotivo(String motivo) {
        consultaDAO = new ConsultaDAO();

        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo não pode ser nulo ou vazio.");
        }

        ConsultaTO consulta = consultaDAO.findByMotivo(motivo);
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada para o motivo informado: " + motivo);
        }
        return consultaDAO.deleteByMotivo(motivo);
    }

    public boolean updateConsulta(ConsultaTO consulta) throws IllegalArgumentException {
        // Valida a consulta
        validateConsulta(consulta);
        consultaDAO = new ConsultaDAO();
        ConsultaTO existingConsulta = consultaDAO.findByMotivo(consulta.getMotivo());

        // Verifica se a consulta existe
        if (existingConsulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada com o motivo informado.");
        }

        // Chama o método update do DAO
        return consultaDAO.update(consulta);
    }

    private void validateConsulta(ConsultaTO consulta) throws IllegalArgumentException {
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não pode ser nula.");
        }

        if (consulta.getMotivo() == null || consulta.getMotivo().trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo da consulta não pode ser vazio.");
        }

        LocalDate data = consulta.getData(); // Já é LocalDate
        if (data == null || data.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data da consulta não pode ser nula ou no passado.");
        }

        if (consulta.getHora() == null || !isValidTimeFormat(consulta.getHora())) {
            throw new IllegalArgumentException("Formato de hora inválido. Deve estar no formato HHmmss.");
        }

        if (consulta.getLocal() == null || consulta.getLocal().trim().isEmpty()) {
            throw new IllegalArgumentException("Local da consulta não pode ser vazio.");
        }
    }

    // Método para validar o formato da hora
    private boolean isValidTimeFormat(String time) {
        return time != null && time.matches("\\d{6}"); // HHmmss
    }
}
