package br.com.fiap.resource;

import br.com.fiap.bo.ConsultaBO;
import br.com.fiap.to.ConsultaTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Time;

@Path("/Sprint4_java/consulta")
public class ConsultaResource {
    private ConsultaBO consultaBO = new ConsultaBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<ConsultaTO> resultado = consultaBO.findAll();
        Response.ResponseBuilder response = null;
        if (resultado != null) {
            response = Response.ok();
        } else {
            response = Response.status(404);

        }
        response.entity(resultado);
        return response.build();
    }

    @GET
    @Path("/{data}/{hora}")
    @Produces(MediaType.APPLICATION_JSON)

    public Response findByDataHora(@PathParam("data") String dataStr, @PathParam("hora") String horaStr) {
        try {
            LocalDate data = LocalDate.parse(dataStr); // Converte a string para LocalDate
            LocalTime hora = LocalTime.parse(horaStr); // Converte a string para LocalTime

            ConsultaTO resultado = consultaBO.findByDataHora(data, hora);
            Response.ResponseBuilder response;
            if (resultado != null) {
                response = Response.ok(resultado);
            } else {
                response = Response.status(404).entity("Consulta não encontrada.");
            }
            return response.build();
        } catch (Exception e) {
            return Response.status(400).entity("Formato de data ou hora inválido.").build();
        }
    }
}