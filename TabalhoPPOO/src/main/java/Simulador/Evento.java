/*
---------------------------------------------------------------------------------
Trabalho Prático - Práticas de Programação Orientada a Objetos - GCC178 - 2020/01
----------------Grupo 05 - Fila de veículos em pedágio rodoviário----------------
    Integrantes:
        Caio de Oliveira (10A - 201820267),
        Ismael Martins Silva (10A - 201820281),
        Layse Cristina Silva Garcia (10A - 201811177),
        Luiz Felipe Montuani e Silva (10A - 201920253).
---------------------------------------------------------------------------------
*/

package Simulador;

/**
* Classe que representa os eventos que acontecem em um pedágio.
* Podendo ser um evento do tipo de Chegada ou Saida.
*/
public abstract class Evento implements Comparable<Evento> {
    private int tempoEvento;
    private int idCabine;

    /**
     * Construtor da classe Evento.
     * Inicializa o ID da Cabine com -1, pois a Cabine não é selecionada
     * no momento que o objeto Evento (subclasse Chegada) é instanciado,
     * devido às diferentes abordagens do Simulador (podendo ser pela Cabine
     * com menor fila ou aleatoriamente).
     * Isso ocorre devido ao fato que no momento da criação dos eventos de
     * chegada as filas estão vazias, logo apenas a abordagem aleatória
     * funcionaria.
     * @param tempoEvento tempo atual do programa.
     */
    public Evento(int tempoEvento) {
        this.tempoEvento = tempoEvento;
        this.idCabine = -1;
    }

    /**
     * Método que informa o tempo atual do evento.
     * @return int - contendo número que representa o tempo atual do evento.
     */
    public int getTempoEvento() {
        return tempoEvento;
    }

    /**
     * Método que retorna o identificador da cabine do evento.
     * @return int - identificador da cabine.
     */
    public int getIdCabine() {
        return idCabine;
    }

    /**
     * Método que define o identificador da cabine do evento.
     * @param idCabine identificador da cabine.
     */
    public void setIdCabine(int idCabine) {
        this.idCabine = idCabine;
    }

    /**
     * Método que faz a comparação entre o tempo de Evento.
     * @param eventoComparado evento a ser comparado.
     * @return int - retorna um número inteiro negativo, zero, ou positivo
     * se o objeto Evento é menor que, igual que, ou maior que o objeto passado
     * por parâmetro.
     */
    @Override
    public int compareTo(Evento eventoComparado) {
        if (this.tempoEvento > eventoComparado.getTempoEvento()) {
            return 1;
        } else if (this.tempoEvento < eventoComparado.getTempoEvento()) {
            return -1;
        }
        return 0;
    }
}
