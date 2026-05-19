package br.com.vitta.resource;

import br.com.vitta.dao.PacienteDAO;
import br.com.vitta.entities.Paciente;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {

    @Inject
    PacienteDAO dao;

    // get, /pacientes, para listar todos os pacientes
    // 200 ok
    @GET
    public Response listar() {
        try {
            List<Paciente> pacientes = dao.listarTodos();
            return Response.ok(pacientes).build();
        } catch (Exception e) {
            return Response.status(500)
                    .entity("{\"erro\": \"Erro interno no servidor\"}")
                    .build();
        }
    }


    // get, /pacientes, para listar todos os pacientes
    // 200 ok ou 404 not found
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            List<Paciente> pacientes = dao.listarTodos();
            return pacientes.stream()
                    .filter(p -> p.getId() == id)
                    .findFirst()
                    .map(p -> Response.ok(p).build())
                    .orElse(Response.status(404)
                            .entity("{\"erro\": \"Paciente não encontrado\"}")
                            .build());
        } catch (Exception e) {
            return Response.status(500)
                    .entity("{\"erro\": \"Erro interno no servidor\"}")
                    .build();
        }
    }


    // post, /pacientes, para cadastrar um novo paciente
    // 201 created, 400 bad request ou 422 impossivel de processar a entidade
    @POST
    public Response criar(Paciente paciente) {
        try {
            // 400 - JSON quebrado ou campo nulo
            if (paciente == null) {
                return Response.status(400)
                        .entity("{\"erro\": \"Corpo da requisição inválido\"}")
                        .build();
            }
            // 422 - Validação de negócio
            if (paciente.getNome() == null || paciente.getNome().isEmpty()) {
                return Response.status(422)
                        .entity("{\"erro\": \"Nome é obrigatório\"}")
                        .build();
            }
            if (paciente.getCpf() == null || paciente.getCpf().isEmpty()) {
                return Response.status(422)
                        .entity("{\"erro\": \"CPF é obrigatório\"}")
                        .build();
            }
            if (paciente.getIdade() <= 0) {
                return Response.status(422)
                        .entity("{\"erro\": \"Idade deve ser maior que zero\"}")
                        .build();
            }
            if (paciente.getTelefone() == null || paciente.getTelefone().isEmpty()) {
                return Response.status(422)
                        .entity("{\"erro\": \"Telefone é obrigatório\"}")
                        .build();
            }
            dao.create(paciente);
            return Response.status(201).entity(paciente).build();
        } catch (Exception e) {
            return Response.status(500)
                    .entity("{\"erro\": \"Erro interno no servidor\"}")
                    .build();
        }
    }


    // put, /pacientes/{id} , para atualizar um paciente pelo id
    // 200 ok, 400 bad request ou 404 not found
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Paciente paciente) {
        try {
            if (paciente == null) {
                return Response.status(400)
                        .entity("{\"erro\": \"Corpo da requisição inválido\"}")
                        .build();
            }
            List<Paciente> pacientes = dao.listarTodos();
            boolean existe = pacientes.stream().anyMatch(p -> p.getId() == id);
            if (!existe) {
                return Response.status(404)
                        .entity("{\"erro\": \"Paciente não encontrado\"}")
                        .build();
            }
            dao.update(id, paciente);
            return Response.ok(paciente).build();
        } catch (Exception e) {
            return Response.status(500)
                    .entity("{\"erro\": \"Erro interno no servidor\"}")
                    .build();
        }
    }


    // delete,  /pacientes/{id} , para deletar um paciente pelo id dele
    // 204 no content ou 404 not found
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        try {
            List<Paciente> pacientes = dao.listarTodos();
            boolean existe = pacientes.stream().anyMatch(p -> p.getId() == id);
            if (!existe) {
                return Response.status(404)
                        .entity("{\"erro\": \"Paciente não encontrado\"}")
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