package br.com.vitta.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL =
            "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "rm567593";
    private static final String PASSWORD = "230505";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initialize() {
        criarTabelaPaciente();
        criarTabelaDentista();
        criarTabelaChamado();
    }

    //cria a tabela para o paciente
    private static void criarTabelaPaciente() {
        var sql = """
                CREATE TABLE paciente (
                    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    nome VARCHAR2(255) NOT NULL,
                    cpf VARCHAR2(14) NOT NULL,
                    idade NUMBER NOT NULL,
                    telefone VARCHAR2(20) NOT NULL
                )
                """;
        executarTryCatch(sql, "paciente");
    }
    //cria a tabela para o dentista
    private static void criarTabelaDentista() {
        var sql = """
                CREATE TABLE dentista (
                    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    nome VARCHAR2(255) NOT NULL,
                    cpf VARCHAR2(14) NOT NULL,
                    especialidade VARCHAR2(100) NOT NULL
                )
                """;
        executarTryCatch(sql, "dentista");
    }
    //cria a tabela para o chamado
    private static void criarTabelaChamado() {
        var sql = """
                CREATE TABLE chamado (
                    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    id_paciente NUMBER NOT NULL,
                    id_dentista NUMBER NOT NULL,
                    descricao_problema VARCHAR2(500) NOT NULL,
                    data_atendimento VARCHAR2(20) NOT NULL,
                    prioridade VARCHAR2(50) NOT NULL,
                    FOREIGN KEY (id_paciente) REFERENCES paciente(id),
                    FOREIGN KEY (id_dentista) REFERENCES dentista(id)
                )
                """;
        executarTryCatch(sql, "chamado");
    }

    // isso aqui é pra nao ficar fazendo o try/catch em qualquer metodo que eu chamar
    private static void executarTryCatch(String sql, String tabela) {
        try (var conn = getConnection()) {
            conn.prepareStatement(sql).execute();
            System.out.println("A tabela " + tabela + " foi criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("A tabela " + tabela + " já existe ou aconteceu o erro: " + e.getMessage());
        }
    }
}