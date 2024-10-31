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

        // A hora deve estar no formato HHmmss ao ser recebida
        String normalizedHour = normalizeHour(consulta.getHora());

        // Verifica se já existe uma consulta agendada para a mesma data e hora
        if (consultaDAO.findByDataHora(consulta.getData(), normalizedHour) != null) {
            throw new IllegalArgumentException("Já existe uma consulta agendada para a mesma data e hora.");
        }

        // Salva a consulta no banco de dados
        consultaDAO.save(consulta);
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

        // Verifica se a hora está no formato correto (HHmmss)
        if (!consulta.getHora().matches("\\d{6}")) {
            throw new IllegalArgumentException("Formato de hora inválido. Deve estar no formato HHmmss.");
        }
    }

    public ConsultaTO findByDataHora(LocalDate data, String hora) throws IllegalArgumentException {
        consultaDAO = new ConsultaDAO();

        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula.");
        }

        // Verifica se a hora está no formato correto (HHmmss)
        if (!hora.matches("\\d{6}")) {
            throw new IllegalArgumentException("Formato de hora inválido. Deve estar no formato HHmmss.");
        }

        // Chamando o DAO para encontrar a consulta
        ConsultaTO consulta = consultaDAO.findByDataHora(data, hora); // Mantendo hora como String
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada para a data e hora informadas.");
        }
        return consulta;
    }

    public ConsultaTO save(ConsultaTO consulta) {
        consultaDAO = new ConsultaDAO();

        // Valida a consulta antes de salvar
        validateConsulta(consulta);

        // Normaliza a hora para o formato HHmmss
        String normalizedHour = normalizeHour(consulta.getHora());

        // Log para verificar os valores
        System.out.println("Tentando salvar consulta com data: " + consulta.getData() + " e hora: " + normalizedHour);

        // Verifica se já existe uma consulta agendada para a mesma data e hora
        if (consultaDAO.findByDataHora(consulta.getData(), normalizedHour) != null) {
            throw new IllegalArgumentException("Já existe uma consulta agendada para a mesma data e hora.");
        }

        // Salva a consulta no banco de dados
        ConsultaTO savedConsulta = consultaDAO.save(consulta);
        if (savedConsulta == null) {
            throw new IllegalArgumentException("Erro ao salvar a consulta. Tente novamente.");
        }
        return savedConsulta;
    }

    public boolean delete(LocalDate data, String hora) {
        consultaDAO = new ConsultaDAO();

        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula.");
        }

        // Verifica se a hora está no formato correto (HHmmss)
        if (!hora.matches("\\d{6}")) {
            throw new IllegalArgumentException("Formato de hora inválido. Deve estar no formato HHmmss.");
        }

        ConsultaTO consulta = consultaDAO.findByDataHora(data, hora);
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada para a data e hora informadas.");
        }
        return consultaDAO.delete(data, hora);
    }

    public static LocalDate convertStringToLocalDate(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(data, formatter);
    }

    // Método para normalizar a hora (mantido apenas para validação)
    private String normalizeHour(String hora) {
        // Se houver lógica de normalização, pode ser implementada aqui
        return hora; // Retorna a hora recebida
    }
}
