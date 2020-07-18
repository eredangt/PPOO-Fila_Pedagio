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
* fornece métodos para a manipulação de seus atributos
* pelas classes filhas. 
* Os tipos são: atendimento por cliente e por cobrança automática.
*/
public abstract class Atendimento {
	private static int numeroAtendimentos;
	private int idAtendimento;
	private double tempoAtendimento;
	
	/**
	* Construtor incrementa o contador de atendimento e 
	* define os IDs de cada atendimento a partir desse valor.
	* @param tempo atendimento double que define um tempo base 
	* para as operações naquele tipo de atendimento.
	*/
	public Atendimento(double umTempoAtendimento) {	
		tempoAtendimento = umTempoAtendimento;
		numeroAtendimentos += 1;
		idAtendimento = numeroAtendimentos;
	}
	
	/**
	* Método utilizado apenas para fins de debug.
	* @return String - uma cadeia de caracteres formatada
	* com os atributos da classe.
	*/
	public String toString() {
		return "ID ATENDIMENTO: " + getIdAtendimento() + "\nTEMPO: " + getTempoAtendimento() + "\n";
	}
	
	/**
	* Método que informa quantos objetos da classe Atendimento
	* foram instanciados.
	* @return int - contendo o número que representa
	* quantos atendimentos existem no sistema até o momento.
	*/
	protected int getNumeroAtendimentos() {
		return numeroAtendimentos;
	}
	
	/**
	* Método que informa o identificador único de um atendimento.
	* @return int - contendo o número que representa o Id.
	*/
	protected int getIdAtendimento() {
		return idAtendimento;
	}
	
	 /**
     * Método que informa o tempo de operação daquele atendimento.
     * @return double - contendo o número o tempo.
     */
	protected double getTempoAtendimento() {
		return tempoAtendimento;
	}
}
