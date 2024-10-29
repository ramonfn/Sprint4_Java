package br.com.fiap.bo;

import br.com.fiap.dao.ConsultaDAO;
import br.com.fiap.to.ConsultaTO;

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
    }
}
