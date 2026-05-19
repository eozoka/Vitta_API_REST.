package br.com.vitta.entities;

public class Setor {
    private String nome;
    private String funcao;

    public Setor(String nome, String funcao) {
        this.nome = nome;
        this.funcao = funcao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Override
    public String toString() {
        return "Setor: " + nome + " - Função: " + funcao;
    }
}