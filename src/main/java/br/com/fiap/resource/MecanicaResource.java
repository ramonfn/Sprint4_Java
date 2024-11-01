package br.com.fiap.resource;

import br.com.fiap.bo.MecanicaBO;
import br.com.fiap.to.ConsultaTO;
import br.com.fiap.to.MecanicaTO;
import jakarta.ws.rs.*;
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
        if (resultado != null) {
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
    public Response findByNm_mecanico(@PathParam("nm_mecanico") String nm_mecanico) {
        try {
            System.out.println("Buscando mecânico com nome: " + nm_mecanico);
            MecanicaTO resultado = mecanicaBO.findByNm_mecanico(nm_mecanico);
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(MecanicaTO mecanica) {
        MecanicaTO resultado = mecanicaBO.save(mecanica);
        Response.ResponseBuilder response = null;
        if (resultado != null) {
            response = Response.created(null);
        } else {
            response = Response.status(400);
        }
        response.entity(resultado);
        return response.build();
    }

    @DELETE
    @Path("/{nm_mecanico}")
    public Response deleteMecanica(@PathParam("nm_mecanico") String nm_mecanico) {
        try {
            boolean deleted = mecanicaBO.delete(nm_mecanico.trim());
            if (deleted) {
                return Response.noContent().build(); // Retorna 204 No Content
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Mecânico não encontrado.")
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
    @Path("/{nm_mecanico}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMecanica(@PathParam("nm_mecanico") String nm_mecanico, MecanicaTO mecanica) {
        try {
            mecanica.setNm_mecanico(nm_mecanico.trim()); // Define o nome do mecânico a ser atualizado
            boolean updated = mecanicaBO.updateMecanico(mecanica);

            if (updated) {
                return Response.ok().build(); // Retorna 200 OK
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Mecânico não encontrado.")
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
