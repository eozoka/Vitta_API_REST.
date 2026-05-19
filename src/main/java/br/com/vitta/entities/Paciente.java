package br.com.vitta.entities;

public class Paciente extends Pessoa {
    private int idade;
    private String telefone;

    public Paciente(String nome, String cpf, int idade, String telefone) {
        super(nome, cpf);
        this.idade = idade;
        this.telefone = telefone;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return super.toString() + " - Idade: " + idade + " - Telefone: " + telefone;
    }
}