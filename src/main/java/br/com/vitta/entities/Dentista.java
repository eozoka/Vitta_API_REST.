package br.com.vitta.entities;

public class Dentista extends Pessoa {
    private String especialidade;

    public Dentista(String nome, String cpf, String especialidade) {
        super(nome, cpf);
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return super.toString() + " - Especialidade: " + especialidade;
    }
}