package br.com.vitta.services;


import br.com.vitta.entities.Chamado;
import br.com.vitta.entities.Dentista;
import br.com.vitta.entities.Paciente;

import java.util.ArrayList;
import java.util.List;

public class SistemaChamados {

    private final List<Paciente> pacientes;
    private final List<Dentista> dentistas;
    private final List<Chamado> chamados;

    public SistemaChamados() {
        pacientes = new ArrayList<>();
        dentistas = new ArrayList<>();
        chamados = new ArrayList<>();
    }

    public void adicionarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    public void adicionarDentista(Dentista dentista) {
        dentistas.add(dentista);
    }

    public void adicionarChamado(Chamado chamado) {
        chamados.add(chamado);
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Dentista> getDentistas() {
        return dentistas;
    }

    public List<Chamado> getChamados() {
        return chamados;
    }
}