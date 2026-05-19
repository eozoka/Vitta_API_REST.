package br.com.vitta.dao;

import br.com.vitta.entities.Paciente;
import br.com.vitta.infra.DatabaseConfig;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PacienteDAO    {


    // create ( inserir informaçoes no banco de dados )

    public void create(Paciente paciente) {
        var sql = "INSERT INTO paciente (nome, cpf, idade, telefone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setInt(3, paciente.getIdade());
            stmt.setString(4, paciente.getTelefone());
            stmt.execute();
            System.out.println("Paciente salvo com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // read  ler tudo

    public List<Paciente> listarTodos() {
        var sql = "SELECT * FROM paciente";
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paciente p = new Paciente(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getInt("idade"),
                        rs.getString("telefone")
                );
                p.setId(rs.getInt("id"));
                pacientes.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }


    // update

    public void update(int id, Paciente paciente) {
        var sql = "UPDATE paciente SET nome=?, cpf=?, idade=?, telefone=? WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setInt(3, paciente.getIdade());
            stmt.setString(4, paciente.getTelefone());
            stmt.setInt(5, id);
            stmt.execute();
            System.out.println("Paciente atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // delete

    public void delete(int id) {
        var sql = "DELETE FROM paciente WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.execute();
            System.out.println("Paciente deletado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}