package br.com.fiap.resource;

import br.com.fiap.bo.ConsultaBO;
import br.com.fiap.to.ConsultaTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
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
    @Path("/{motivo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByMotivo(@PathParam("motivo") String motivo) {
        try {
            System.out.println("Buscando consulta pelo motivo: " + motivo);
            ConsultaTO resultado = consultaBO.findByMotivo(motivo);
            return Response.ok(resultado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
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
    @Path("/{motivo}")
    public Response deleteByMotivo(@PathParam("motivo") String motivo) {
        Response.ResponseBuilder response;
        try {
            if (consultaBO.deleteByMotivo(motivo)) {
                response = Response.status(Response.Status.NO_CONTENT);
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
    private boolean isValidDateFormat(String date) {
        return date.matches("\\d{4}/\\d{2}/\\d{2}"); // yyyy/MM/dd
    }

    private boolean isValidTimeFormat(String time) {
        if (time == null || time.isEmpty()) return false;

        // Verifica se a hora está no formato HHmmss (6 dígitos)
        return time.matches("\\d{6}");
    }

    @PUT
    @Path("/{motivo}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("motivo") String motivo, ConsultaTO consultaAtualizada) {
        try {
            // Verifica se o motivo está presente
            if (motivo == null || motivo.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Motivo não pode ser nulo ou vazio.").build();
            }

            // Define o motivo na consulta atualizada
            consultaAtualizada.setMotivo(motivo);

            // Realiza a atualização
            boolean atualizado = consultaBO.updateConsulta(consultaAtualizada);
            if (atualizado) {
                return Response.ok(consultaAtualizada).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Consulta não encontrada para atualização.").build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro inesperado: " + e.getMessage()).build();
        }
    }

}
