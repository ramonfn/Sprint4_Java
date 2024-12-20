package br.com.fiap.resource;

import br.com.fiap.bo.ServicoBO;
import br.com.fiap.to.ServicoTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/servico")
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
    @GET
    @Path("/{id_servico}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByIdServico(@PathParam("id_servico") String idServico) {
        try {
            ServicoTO servico = servicoBO.findById_servico(idServico);
            return Response.ok(servico).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro inesperado: " + e.getMessage())
                    .build();
        }

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
    @DELETE
    @Path("/{id_servico}")
    public Response deleteServico(@PathParam("id_servico") String id_servico) {
        try {
            boolean deleted = servicoBO.delete(id_servico.trim());
            if (deleted) {
                return Response.noContent().build(); // Retorna 204 No Content
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Serviço não encontrado.")
                        .build(); // Retorna 404 Not Found
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build(); // Retorna 400 Bad Request
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro inesperado: " + e.getMessage())
                    .build(); // Retorna 500 Internal Server Error
        }
    }
    @PUT
    @Path("/{id_servico}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateServico(@PathParam("id_servico") String id_servico, ServicoTO servico) {
        try {
            servico.setId_servico(id_servico.trim());
            boolean updated = servicoBO.update(servico);
            if (updated) {
                return Response.ok().build(); // Retorna 200 OK
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Serviço não encontrado.")
                        .build(); // Retorna 404 Not Found
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build(); // Retorna 400 Bad Request
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro inesperado: " + e.getMessage())
                    .build(); // Retorna 500 Internal Server Error
        }
    }

}
