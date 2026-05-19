package br.com.vitta.services;

import br.com.vitta.entities.Chamado;
import java.util.ArrayList;
import java.util.List;


public class TriagemIA {

    // esse metodo ele analisa e define a prioridade do caso
    public String analisarSintoma(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            return "Indeterminada";
        }

        String analise = descricao.toLowerCase().trim();

        if (analise.contains("dor forte") || analise.contains("sangramento") || analise.contains("hemorragia")) {
            return "Alta prioridade";
        } else if (analise.contains("desconforto") || analise.contains("inchaço") || analise.contains("dor")) {
            return "Média prioridade";
        } else {
            return "Baixa prioridade";
        }
    }
    // esse metodo analisa o paciente pela idade e coloca ele em determinada prioridade
    public String classificarRiscoPorIdade(int idade) {
        if (idade >= 60) {
            return "Paciente idoso - Atendimento prioritário";
        } else if (idade <= 12) {
            return "Paciente infantil - Requer cuidado especial";
        } else {
            return "Paciente adulto - Atendimento padrão";
        }
    }

    // esse metodo chama e lista todos os atendimentos que sao urgentes
    public List<Chamado> filtrarChamadosUrgentes(List<Chamado> chamados) {
        List<Chamado> urgentes = new ArrayList<>();
        for (Chamado c : chamados) {
            if (c.getFormulario().getPrioridade().equals("Alta prioridade")) {
                urgentes.add(c);
            }
        }
        return urgentes;
    }

    // esse metodo mostra e conta quantos atendimentos o dentista tem
    public int contarChamadosPorDentista(List<Chamado> chamados, String nomeDentista) {
        int contador = 0;
        for (Chamado c : chamados) {
            if (c.getDentista().getNome().equalsIgnoreCase(nomeDentista)) {
                contador++;
            }
        }
        return contador;
    }




}
