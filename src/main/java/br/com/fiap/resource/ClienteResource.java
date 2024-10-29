package br.com.fiap.resource;

import br.com.fiap.bo.ClienteBO;
import br.com.fiap.to.ClienteTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/Sprint4_java/cliente")
public class ClienteResource {
    private ClienteBO clienteBO = new ClienteBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(){
        ArrayList<ClienteTO> resultado = clienteBO.findAll();
        Response.ResponseBuilder response = null;
        if (resultado !=null) {
            response = Response.ok();
        } else {
            response = Response.status(404);

        }
        response.entity(resultado);
        return response.build();
    }
    @GET
    @Path("/{nr_cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByNr_cpf(@PathParam("nr_cpf")String nr_cpf) {
        ClienteTO resultado = clienteBO.findbyNr_cpf(nr_cpf);
        Response.ResponseBuilder response = null;
        if (resultado != null) {
            response = Response.ok();
        } else {
            response = Response.status(404);
        }
        response.entity(resultado);
        return response.build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(ClienteTO cliente) {
        ClienteTO resultado = clienteBO.save(cliente);
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
