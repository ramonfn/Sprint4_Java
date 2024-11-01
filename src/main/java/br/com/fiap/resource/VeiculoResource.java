package br.com.fiap.resource;

import br.com.fiap.bo.VeiculoBO;
import br.com.fiap.to.VeiculoTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/Sprint4_java/veiculo")
public class VeiculoResource {
    private VeiculoBO veiculoBO = new VeiculoBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<VeiculoTO> resultado = veiculoBO.findAll();
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
    @Path("/{id_veiculo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById_veiculo(@PathParam("id_veiculo")String id_veiculo) {
        VeiculoTO resultado = veiculoBO.findById_veiculo(id_veiculo);
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
    public Response save(VeiculoTO veiculo) {
        VeiculoTO resultado = veiculoBO.save(veiculo);
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
    @Path("/{id_veiculo}")
    public Response delete(@PathParam("id_veiculo") String id_veiculo) {
        try {
            veiculoBO.delete(id_veiculo);
            return Response.noContent().build(); // Retorna 204 No Content em caso de sucesso
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
    @PUT
    @Path("/{id_veiculo}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id_veiculo") String id_veiculo, VeiculoTO veiculo) {
        if (!id_veiculo.trim().equals(veiculo.getId_veiculo().trim())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID do veículo na URL não coincide com o ID no corpo da requisição.")
                    .build();
        }
        try {
            VeiculoTO resultado = veiculoBO.update(veiculo);
            return Response.ok(resultado).build();
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



}

