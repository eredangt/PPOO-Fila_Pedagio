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
* Classe que representa o atendimento em um pedágio.
* Ela gera um Identificador único para cada tipo de atendimento e
* fornece métodos para a manipulação de seus atributos.
* Os tipos são: atendimento por cliente e por cobrança automática.
*/
public abstract class Atendimento {
	private static int numeroAtendimentos = 0;
	private int idAtendimento;
	private int tempoAtendimento;

	/**
	* Construtor incrementa o contador de atendimento e
	* define os IDs de cada atendimento a partir desse valor.
	* @param umTempoAtendimento int que define um tempo base
	* para as operações naquele tipo de atendimento.
	*/
	public Atendimento(int umTempoAtendimento) {
		tempoAtendimento = umTempoAtendimento;
		numeroAtendimentos += 1;
		idAtendimento = numeroAtendimentos;
	}

	/**
	* Método que retorna o tempo de operação daquele atendimento.
	* @return int - contendo o número o tempo.
	*/
	public int getTempoAtendimento() {
		return tempoAtendimento;
	}

	/**
	* Método que retorna quantos objetos da classe Atendimento
	* foram instanciados.
	* @return int - contendo o número que representa
	* quantos atendimentos existem no sistema até o momento.
	*/
	protected int getNumeroAtendimentos() {
		return numeroAtendimentos;
	}

	/**
	* Método que retorna o identificador único de um atendimento.
	* @return int - contendo o número que representa o Id.
	*/
	protected int getIdAtendimento() {
		return idAtendimento;
	}
}
