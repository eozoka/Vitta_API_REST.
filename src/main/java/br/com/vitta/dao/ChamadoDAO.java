package br.com.vitta.dao;

import br.com.vitta.entities.Chamado;
import br.com.vitta.entities.Dentista;
import br.com.vitta.entities.Formulario;
import br.com.vitta.entities.Paciente;

import br.com.vitta.infra.DatabaseConfig;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ChamadoDAO {

    public void create(Chamado chamado) {
        var sql = "INSERT INTO chamado (id_paciente, id_dentista, descricao_problema, data_atendimento, prioridade) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, chamado.getPaciente().getId());
            stmt.setInt(2, chamado.getDentista().getId());
            stmt.setString(3, chamado.getFormulario().getDescricaoProblema());
            stmt.setString(4, chamado.getFormulario().getData());
            stmt.setString(5, chamado.getFormulario().getPrioridade());
            stmt.execute();
            System.out.println("Chamado salvo com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Chamado> listarTodos() {
        var sql = """
                SELECT c.id, c.descricao_problema, c.data_atendimento, c.prioridade,
                       p.id as id_paciente, p.nome as nome_paciente, p.cpf as cpf_paciente, p.idade, p.telefone,
                       d.id as id_dentista, d.nome as nome_dentista, d.cpf as cpf_dentista, d.especialidade
                FROM chamado c
                JOIN paciente p ON c.id_paciente = p.id
                JOIN dentista d ON c.id_dentista = d.id
                """;

        List<Chamado> chamados = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getString("nome_paciente"),
                        rs.getString("cpf_paciente"),
                        rs.getInt("idade"),
                        rs.getString("telefone")
                );
                paciente.setId(rs.getInt("id_paciente"));

                Dentista dentista = new Dentista(
                        rs.getString("nome_dentista"),
                        rs.getString("cpf_dentista"),
                        rs.getString("especialidade")
                );
                dentista.setId(rs.getInt("id_dentista"));

                Formulario formulario = new Formulario(
                        rs.getString("descricao_problema"),
                        rs.getString("data_atendimento")
                );
                formulario.setPrioridade(rs.getString("prioridade"));

                chamados.add(new Chamado(paciente, dentista, formulario));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chamados;
    }

    public void update(int id, Chamado chamado) {
        var sql = "UPDATE chamado SET descricao_problema=?, data_atendimento=?, prioridade=? WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, chamado.getFormulario().getDescricaoProblema());
            stmt.setString(2, chamado.getFormulario().getData());
            stmt.setString(3, chamado.getFormulario().getPrioridade());
            stmt.setInt(4, id);
            stmt.execute();
            System.out.println("O chamado foi atualizado!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        var sql = "DELETE FROM chamado WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.execute();
            System.out.println("O chamado foi deletado!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}