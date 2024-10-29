package br.com.fiap.bo;

import br.com.fiap.dao.ConsultaDAO;
import br.com.fiap.to.ConsultaTO;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

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
    }

    private void validateConsulta(ConsultaTO consulta) throws IllegalArgumentException {
        if (consulta.getMotivo() == null || consulta.getMotivo().trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo da consulta não pode ser vazio.");
        }
        if (consulta.getData() == null) {
            throw new IllegalArgumentException("Data da consulta não pode ser nula.");
        }
        if (consulta.getHora() == null ) {
            throw new IllegalArgumentException("Hora da consulta deve ser um número positivo.");
        }
        if (consulta.getLocal() == null || consulta.getLocal().trim().isEmpty()) {
            throw new IllegalArgumentException("Local da consulta não pode ser vazio.");
        }
        if (consulta.getData().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data da consulta não pode ser no passado.");
        }
        if (consulta.getData().isEqual(LocalDate.now()) && consulta.getHora().isBefore(LocalTime.now())) {
            throw new IllegalArgumentException("Hora da consulta não pode ser no passado.");
        }
    }
    public ConsultaTO findByDataHora(LocalDate data, LocalTime hora) throws IllegalArgumentException {
        consultaDAO = new ConsultaDAO();
        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula.");
        }
        if (hora == null) {
            throw new IllegalArgumentException("Hora não pode ser nula.");
        }
        ConsultaTO consulta = consultaDAO.findByDataHora(data, hora);
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada para a data e hora informadas.");
        }
        return consulta;
    }

}
