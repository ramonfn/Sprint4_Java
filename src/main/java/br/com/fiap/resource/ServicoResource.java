package br.com.fiap.resource;

import br.com.fiap.bo.ServicoBO;
import br.com.fiap.to.ServicoTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/Sprint4_java/servico")
public class ServicoResource {
    private ServicoBO servicoBO = new ServicoBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<ServicoTO> resultado = servicoBO.findAll();
        Response.ResponseBuilder response = null;
        if (resultado !=null) {
            response = Response.ok();
        } else {
            response = Response.status(404);

        }
        response.entity(resultado);
        return response.build();
    }

}
