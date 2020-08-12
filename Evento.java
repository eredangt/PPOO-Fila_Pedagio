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

/**
* Classe que representa os eventos que acontecem em um pedágio.
* Podendo ser um evento do tipo de Chegada ou Saida, sendo extendido por essas
* subclasses.
*/
public abstract class Evento implements Comparable<Evento> {
    private int tempoEvento;
    protected int idCabine;

    /**
     * Construtor da classe Evento.
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
     * @return int - retorna o número para ordenar os dados.
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
