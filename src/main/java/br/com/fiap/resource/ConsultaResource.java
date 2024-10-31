package br.com.fiap.resource;

import br.com.fiap.bo.ConsultaBO;
import br.com.fiap.to.ConsultaTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


@Path("/Sprint4_java/consulta")
public class ConsultaResource {
    private ConsultaBO consultaBO = new ConsultaBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<ConsultaTO> resultado = consultaBO.findAll();
        if (resultado != null && !resultado.isEmpty()) {
            return Response.ok(resultado).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Nenhuma consulta encontrada.").build();
        }
    }

    @GET
    @Path("/{ano}/{mes}/{dia}/{hora}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByDataHora(@PathParam("ano") int ano, @PathParam("mes") int mes, @PathParam("dia") int dia, @PathParam("hora") String horaStr) {
        try {
            LocalDate data = LocalDate.of(ano, mes, dia); // Criando a data a partir dos parâmetros
            if (!isValidTimeFormat(horaStr)) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Formato de hora inválido.").build();
            }
            ConsultaTO resultado = consultaBO.findByDataHora(data, horaStr);
            if (resultado != null) {
                return Response.ok(resultado).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Consulta não encontrada.").build();
            }
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Formato de data inválido.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro inesperado: " + e.getMessage()).build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(ConsultaTO consulta) {
        System.out.println("Consulta recebida: " + consulta); // Log para depuração

        if (consulta == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Consulta não pode ser nula.").build();
        }

        // Verifique se a hora está no formato correto
        if (!isValidTimeFormat(consulta.getHora())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Formato de hora inválido. Deve estar no formato HHmmss.").build();
        }

        try {
            ConsultaTO resultado = consultaBO.save(consulta);
            return Response.status(Response.Status.CREATED).entity(resultado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro inesperado: " + e.getMessage())
                    .build();
        }
    }
    @DELETE
    @Path("/{ano}/{mes}/{dia}/{hora}")
    public Response delete(@PathParam("ano") int ano, @PathParam("mes") int mes, @PathParam("dia") int dia, @PathParam("hora") String horaStr) {
        Response.ResponseBuilder response;
        try {
            LocalDate data = LocalDate.of(ano, mes, dia);
            if (!isValidTimeFormat(horaStr)) {
                response = Response.status(Response.Status.BAD_REQUEST).entity("Formato de hora inválido.");
            } else if (consultaBO.delete(data, horaStr)) {
                response = Response.status(Response.Status.NO_CONTENT); // 204 No Content
            } else {
                response = Response.status(Response.Status.NOT_FOUND).entity("Consulta não encontrada.");
            }
        } catch (IllegalArgumentException e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());
        } catch (Exception e) {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro inesperado: " + e.getMessage());
        }
        return response.build();
    }




    private boolean isValidTimeFormat(String time) {
        if (time == null || time.isEmpty()) return false;

        // Verifica se a hora está no formato HHmmss (6 dígitos)
        return time.matches("\\d{6}");
    }
    }

