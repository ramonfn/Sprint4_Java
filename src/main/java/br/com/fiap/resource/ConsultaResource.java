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
import java.time.LocalDate;

@Path("/Sprint4_java/consulta")
public class ConsultaResource {
    private ConsultaBO consultaBO = new ConsultaBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<ConsultaTO> resultado = consultaBO.findAll();
        Response.ResponseBuilder response;
        if (resultado != null && !resultado.isEmpty()) {
            response = Response.ok(resultado);
        } else {
            response = Response.status(404).entity("Nenhuma consulta encontrada.");
        }
        return response.build();
    }

    @GET
    @Path("/{data}/{hora}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByDataHora(@PathParam("data") String dataStr, @PathParam("hora") String horaStr) {
        try {
            LocalDate data = LocalDate.parse(dataStr);


            String hora = horaStr;

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
