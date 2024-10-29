package br.com.fiap.bo;

import br.com.fiap.dao.ConsultaDAO;
import br.com.fiap.dao.MecanicaDAO;
import br.com.fiap.to.ConsultaTO;
import br.com.fiap.to.MecanicaTO;

import java.util.ArrayList;
import java.time.LocalDate;

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
        // Aqui você deve adicionar a lógica para salvar a consulta no banco de dados, se necessário.
    }

    private void validateConsulta(ConsultaTO consulta) throws IllegalArgumentException {
        if (consulta.getMotivo() == null || consulta.getMotivo().trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo da consulta não pode ser vazio.");
        }
        if (consulta.getData() == null) {
            throw new IllegalArgumentException("Data da consulta não pode ser nula.");
        }
        if (consulta.getHora() == null || consulta.getHora().trim().isEmpty()) {
            throw new IllegalArgumentException("Hora da consulta deve ser um número positivo.");
        }
        if (consulta.getLocal() == null || consulta.getLocal().trim().isEmpty()) {
            throw new IllegalArgumentException("Local da consulta não pode ser vazio.");
        }
        if (consulta.getData().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data da consulta não pode ser no passado.");
        }
    }

    public ConsultaTO findByDataHora(LocalDate data, String hora) throws IllegalArgumentException {
        consultaDAO = new ConsultaDAO();

        // Verificando se a data é nula
        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula.");
        }

        // Verificando se a hora é nula ou vazia
        if (hora == null || hora.trim().isEmpty()) {
            throw new IllegalArgumentException("Hora não pode ser nula ou vazia.");
        }

        int horaInt;
        try {
            // Removendo qualquer caractere que não seja dígito
            hora = hora.replaceAll("[^0-9]", "");

            // Verificando o comprimento da string para garantir que esteja no formato correto
            if (hora.length() != 6) {
                throw new IllegalArgumentException("Hora deve estar no formato HHMMSS.");
            }

            // Convertendo a string para um inteiro
            horaInt = Integer.parseInt(hora);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Hora deve ser um número válido.", e);
        }

        // Chamando o DAO para encontrar a consulta
        ConsultaTO consulta = consultaDAO.findByDataHora(data, hora);
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada para a data e hora informadas.");
        }
        return consulta;
    }
    public ConsultaTO save(ConsultaTO consulta){
        consultaDAO = new ConsultaDAO();
        validateConsulta(consulta);
        if (consultaDAO.findByDataHora(consulta.getData(), consulta.getHora()) != null) {
            throw new IllegalArgumentException("Já existe uma consulta agendada para a mesma data e hora.");
        }
        ConsultaTO savedConsulta = consultaDAO.save(consulta);
        if (savedConsulta == null) {
            throw new IllegalArgumentException("Erro ao salvar a consulta. Tente novamente.");
        }
        return savedConsulta;
    }
}