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
* Classe que representa a saída de um veículo do pedágio.
* Ela desenfileira o veículo da fila de uma cabine.
* Sendo ela uma subclasse da classe Evento.
*/
public class Saida extends Evento {

    /**
     * Construtor da classe Saida.
     * @param tempoEvento tempo em que o veículo saiu da fila da cabine.
     * @param idCabine inteiro identificando a cabine que será relacionado
     * com o evento.
     */
    public Saida(int tempoEvento, int idCabine) {
        super(tempoEvento);
        super.setIdCabine(idCabine);
    }
}
