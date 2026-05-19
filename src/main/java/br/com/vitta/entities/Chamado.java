package br.com.vitta.entities;

public class Chamado {
    private static int contador = 1;

    private int id;
    private Paciente paciente;
    private Dentista dentista;
    private Formulario formulario;

    public Chamado(Paciente paciente, Dentista dentista, Formulario formulario) {
        this.id = contador++;
        this.paciente = paciente;
        this.dentista = dentista;
        this.formulario = formulario;
    }

    public int getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    @Override
    public String toString() {
        return "Chamado Numero: " + id + "\n" +
                "  Paciente: " + paciente.getNome() + "\n" +
                "  Dentista: " + dentista.getNome() + "\n" +
                "  Detalhes: " + formulario.toString() + "\n";
    }
}