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
* Ela enfileira cada veículo que chega a fila de uma cabine
* de maneira aleatória ou o encaminha à fila de menor tamanho
*/
public class Chegada extends Evento {
    private int idVeiculo;

	/*
     * Construtor da classe Chegada.
     * @param tempoEvento tempo inicial do atendimento.
     * @param cabine que receberá o veiculo.
     * @param veiculo que será enfileirado.
     */
    public Chegada(int tempoEvento, int idCabine, int idVeiculo) {
		super(tempoEvento, idCabine);
		this.idVeiculo = idVeiculo;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }
}
