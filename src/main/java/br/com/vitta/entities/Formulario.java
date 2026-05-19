package br.com.vitta.entities;

public class Formulario {
    private String descricaoProblema;
    private String data;
    private String prioridade;

    public Formulario(String descricaoProblema, String data) {
        this.descricaoProblema = descricaoProblema;
        this.data = data;
        this.prioridade = "Pendente";
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    @Override
    public String toString() {
        return "Descrição: " + descricaoProblema + " - Data: " + data + " - Prioridade: " + prioridade;
    }
}