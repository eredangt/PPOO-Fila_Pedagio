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
* Classe que representa o atendimento por COBRANÇA AUTOMÁTICA no pedágio.
* Sendo uma subclasse de Atendimento.
*/
public class Automatico extends Atendimento {
	private static int numeroAutomaticos;
	private int idAutomatico;
	
	/**
	* Construtor incrementa o contador de Cobrança Automática e 
	* define os IDs para cada cobrança automática a partir desse valor.
	* @param tempo atendimento double que define um tempo base 
	* para as operações naquele tipo de atendimento.
	*/
	public Automatico(double tempoAtendimento) {
		super(tempoAtendimento);
		
		numeroAutomaticos += 1;
		idAutomatico = numeroAutomaticos;
	}
	
	/**
	* Método utilizado apenas para fins de debug.
	* @return String - uma cadeia de caracteres formatada
	* com os atributos da classe.
	*/
	@Override
	public String toString() {
		return super.toString() + "Identificação Cobrança Automática: " + getIdAutomatico() + "\n";
	}
	
	/**
	* Método que informa o identificador único de uma cobrança automática.
	* @return int - contendo o número que representa o ID.
	*/
	protected int getIdAutomatico() {
		return idAutomatico;
	}
}
