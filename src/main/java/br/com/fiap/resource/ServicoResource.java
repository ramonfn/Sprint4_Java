package br.com.fiap.resource;

import br.com.fiap.bo.ServicoBO;
import br.com.fiap.to.MecanicaTO;
import br.com.fiap.to.ServicoTO;
import jakarta.ws.rs.*;
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
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(ServicoTO servico) {
        ServicoTO resultado = servicoBO.save(servico);
        Response.ResponseBuilder response = null;
        if (resultado != null){
            response = Response.created(null);
        } else {
            response = Response.status(400);
        }
        response.entity(resultado);
        return response.build();
    }
}
