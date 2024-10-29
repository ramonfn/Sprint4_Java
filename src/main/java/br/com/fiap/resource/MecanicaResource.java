package br.com.fiap.resource;

import br.com.fiap.bo.MecanicaBO;
import br.com.fiap.to.MecanicaTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/Sprint4_java/mecanica")
public class MecanicaResource {
    private MecanicaBO mecanicaBO = new MecanicaBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<MecanicaTO> resultado = mecanicaBO.findAll();
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
    @Path("/{nm_mecanico}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByNm_mecanico(@PathParam("nm_mecanico") String nm_mecanico){
        MecanicaTO resultado = mecanicaBO.findByNm_mecanico(nm_mecanico);
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

