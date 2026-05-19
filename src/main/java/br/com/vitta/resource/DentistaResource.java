package br.com.vitta.resource;

import br.com.vitta.dao.DentistaDAO;
import br.com.vitta.entities.Dentista;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/dentistas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DentistaResource {

    @Inject
    DentistaDAO dao;


    // get,  /dentistas , para listar todos os dentistas
    // 200 ok
    @GET
    public Response listar() {
        try {
            List<Dentista> dentistas = dao.listarTodos();
            return Response.ok(dentistas).build();
        } catch (Exception e) {
            return Response.status(500)
                    .entity("{\"erro\": \"Erro interno no servidor\"}")
                    .build();
        }
    }


    // get,  /dentistas , para listar todos os dentistas
    // 200 ok ou 404 not found
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            List<Dentista> dentistas = dao.listarTodos();
            return dentistas.stream()
                    .filter(d -> d.getId() == id)
                    .findFirst()
                    .map(d -> Response.ok(d).build())
                    .orElse(Response.status(404)
                            .entity("{\"erro\": \"Dentista não encontrado\"}")
                            .build());
        } catch (Exception e) {
            return Response.status(500)
                    .entity("{\"erro\": \"Erro interno no servidor\"}")
                    .build();
        }
    }

    // post, /dentistas , para cadastrar novo dentista
    // 201 created, 400 bad request ou 422 impossível de
    @POST
    public Response criar(Dentista dentista) {
        try {
            if (dentista == null) {
                return Response.status(400)
                        .entity("{\"erro\": \"Corpo da requisição inválido\"}")
                        .build();
            }
            if (dentista.getNome() == null || dentista.getNome().isEmpty()) {
                return Response.status(422)
                        .entity("{\"erro\": \"Nome é obrigatório\"}")
                        .build();
            }
            if (dentista.getCpf() == null || dentista.getCpf().isEmpty()) {
                return Response.status(422)
                        .entity("{\"erro\": \"CPF é obrigatório\"}")
                        .build();
            }
            if (dentista.getEspecialidade() == null || dentista.getEspecialidade().isEmpty()) {
                return Response.status(422)
                        .entity("{\"erro\": \"Especialidade é obrigatória\"}")
                        .build();
            }
            dao.create(dentista);
            return Response.status(201).entity(dentista).build();
        } catch (Exception e) {
            return Response.status(500)
                    .entity("{\"erro\": \"Erro interno no servidor\"}")
                    .build();
        }
    }

    // put , /dentistas/{id} , para atualizar algum dentista pelo id
    // 200 ok, 400 bad request ou 404 not found
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Dentista dentista) {
        try {
            if (dentista == null) {
                return Response.status(400)
                        .entity("{\"erro\": \"Corpo da requisição inválido\"}")
                        .build();
            }
            List<Dentista> dentistas = dao.listarTodos();
            boolean existe = dentistas.stream().anyMatch(d -> d.getId() == id);
            if (!existe) {
                return Response.status(404)
                        .entity("{\"erro\": \"Dentista não encontrado\"}")
                        .build();
            }
            dao.update(id, dentista);
            return Response.ok(dentista).build();
        } catch (Exception e) {
            return Response.status(500)
                    .entity("{\"erro\": \"Erro interno no servidor\"}")
                    .build();
        }
    }


    // delete , /dentistas/{id} , deleta algum dentista pelo id
    // 204 no content ou 404 not found
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        try {
            List<Dentista> dentistas = dao.listarTodos();
            boolean existe = dentistas.stream().anyMatch(d -> d.getId() == id);
            if (!existe) {
                return Response.status(404)
                        .entity("{\"erro\": \"Dentista não encontrado\"}")
                        .build();
            }
            dao.delete(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(500)
                    .entity("{\"erro\": \"Erro interno no servidor\"}")
                    .build();
        }
    }
}