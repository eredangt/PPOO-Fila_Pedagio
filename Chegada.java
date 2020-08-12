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
* Classe que representa a chegada de um veículo ao pedágio.
* Ela enfileira cada veículo que chega a fila de uma cabine,
* de maneira aleatória ou o encaminha à fila de menor tamanho.
* Sendo ela uma subclasse da classe Evento.
*/
public class Chegada extends Evento {
    private int idVeiculo;

    /**
     * Construtor da classe Chegada.
     * @param tempoEvento tempo em que o veículo chegou na fila da cabine.
     * @param idVeiculo inteiro identificando o veículo que será relacionado
     * com o evento.
     */
    public Chegada(int tempoEvento, int idVeiculo) {
        super(tempoEvento);
        this.idVeiculo = idVeiculo;
    }

    /**
     * Método que retorna o identificador do veículo do evento.
     * @return int - identificador do veículo.
     */
    public int getIdVeiculo() {
        return idVeiculo;
    }
}
