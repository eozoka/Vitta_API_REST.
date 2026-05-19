package br.com.vitta.resource;

import br.com.vitta.dao.ChamadoDAO;
import br.com.vitta.entities.Chamado;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/chamados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChamadoResource {

    @Inject
    ChamadoDAO dao;

    // get , /chamados , lista todos chamados cadastrados
    // 200 OK
    @GET
    public Response listar() {
        try {
            List<Chamado> chamados = dao.listarTodos();
            return Response.ok(chamados).build();
        } catch (Exception e) {
            return Response.status(500)
                    .entity("{\"erro\": \"Erro interno no servidor\"}")
                    .build();
        }
    }

    // get , /chamados , lista todos chamados cadastrados
    // 200 OK ou 404 Not Found
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            List<Chamado> chamados = dao.listarTodos();
            return chamados.stream()
                    .filter(c -> c.getId() == id)
                    .findFirst()
                    .map(c -> Response.ok(c).build())
                    .orElse(Response.status(404)
                            .entity("{\"erro\": \"Chamado não encontrado\"}")
                            .build());
        } catch (Exception e) {
            return Response.status(500)
                    .entity("{\"erro\": \"Erro interno no servidor\"}")
                    .build();
        }
    }


    // post , /chamados , cadastra um novo chamado
    // 201 Created, 400 Bad Request ou 422 Unprocessable Entity
    @POST
    public Response criar(Chamado chamado) {
        try {
            if (chamado == null) {
                return Response.status(400)
                        .entity("{\"erro\": \"Corpo da requisição inválido\"}")
                        .build();
            }
            if (chamado.getPaciente() == null) {
                return Response.status(422)
                        .entity("{\"erro\": \"Paciente é obrigatório\"}")
                        .build();
            }
            if (chamado.getDentista() == null) {
                return Response.status(422)
                        .entity("{\"erro\": \"Dentista é obrigatório\"}")
                        .build();
            }
            if (chamado.getFormulario() == null) {
                return Response.status(422)
                        .entity("{\"erro\": \"Formulário é obrigatório\"}")
                        .build();
            }
            dao.create(chamado);
            return Response.status(201).entity(chamado).build();
        } catch (Exception e) {
            return Response.status(500)
                    .entity("{\"erro\": \"Erro interno no servidor\"}")
                    .build();
        }
    }


    // put , /chamados/{id} , atualiza algum chamado
    // 200 OK, 400 Bad Request ou 404 Not Found
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Chamado chamado) {
        try {
            if (chamado == null) {
                return Response.status(400)
                        .entity("{\"erro\": \"Corpo da requisição inválido\"}")
                        .build();
            }
            List<Chamado> chamados = dao.listarTodos();
            boolean existe = chamados.stream().anyMatch(c -> c.getId() == id);
            if (!existe) {
                return Response.status(404)
                        .entity("{\"erro\": \"Chamado não encontrado\"}")
                        .build();
            }
            dao.update(id, chamado);
            return Response.ok(chamado).build();
        } catch (Exception e) {
            return Response.status(500)
                    .entity("{\"erro\": \"Erro interno no servidor\"}")
                    .build();
        }
    }


    // delete , /chamados/{id} , para deletar algum chamado
    // 204 no content ou 404 not found
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        try {
            List<Chamado> chamados = dao.listarTodos();
            boolean existe = chamados.stream().anyMatch(c -> c.getId() == id);
            if (!existe) {
                return Response.status(404)
                        .entity("{\"erro\": \"Chamado não encontrado\"}")
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