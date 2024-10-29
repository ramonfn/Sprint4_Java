package br.com.fiap.resource;

import br.com.fiap.bo.VeiculoBO;
import br.com.fiap.to.VeiculoTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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
}

