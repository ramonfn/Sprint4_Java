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
    public Response findAll() {
        ArrayList<ClienteTO> resultado = clienteBO.findAll();
        if (resultado != null && !resultado.isEmpty()) {
            return Response.ok(resultado).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{nr_cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByNr_cpf(@PathParam("nr_cpf") String nr_cpf) {
        ClienteTO resultado = clienteBO.findByNr_cpf(nr_cpf);
        Response.ResponseBuilder response;
        if (resultado != null) {
            response = Response.ok(resultado);
        } else {
            response = Response.status(Response.Status.NOT_FOUND);
        }
        return response.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(ClienteTO cliente) {
        Response.ResponseBuilder response;
        try {
            clienteBO.addCliente(cliente); // Alterado para chamar o método addCliente
            response = Response.created(null).entity(cliente); // Retorna a entidade do cliente criado
        } catch (IllegalArgumentException e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()); // 400 Bad Request
        }
        return response.build();
    }

    @DELETE
    @Path("/{nr_cpf}")
    public Response delete(@PathParam("nr_cpf") String nr_cpf) {
        Response.ResponseBuilder response;
        try {
            if (clienteBO.delete(nr_cpf)) {
                response = Response.status(Response.Status.NO_CONTENT); // 204 No Content
            } else {
                response = Response.status(Response.Status.NOT_FOUND); // 404 Not Found
            }
        } catch (IllegalArgumentException e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()); // 400 Bad Request
        }
        return response.build();
    }
    @PUT
    @Path("/{nr_cpf}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("nr_cpf") String nr_cpf, ClienteTO cliente) {
        Response.ResponseBuilder response;
        try {
            // Garantimos que o CPF do cliente está correto antes de atualizar
            cliente.setNr_cpf(nr_cpf);
            boolean updated = clienteBO.updateCliente(cliente);

            if (updated) {
                response = Response.ok(cliente); // Retorna o cliente atualizado
            } else {
                response = Response.status(Response.Status.NOT_FOUND); // 404 Not Found, caso o cliente não exista
            }
        } catch (IllegalArgumentException e) {
            response = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()); // 400 Bad Request
        }
        return response.build();
    }


}
