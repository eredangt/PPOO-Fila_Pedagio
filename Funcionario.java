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
* Classe que representa o atendimento por Funcionário no pedágio.
* Sendo uma subclasse de Atendimento.
*/
public class Funcionario extends Atendimento {
	private static int numeroFuncionarios = 0;
	private int idFuncionario;

	/**
	* Construtor incrementa o contador de Funcionários e
	* define os IDs para cada funcionário a partir desse valor.
	* @param tempo atendimento double que define um tempo base
	* para as operações naquele tipo de atendimento.
	*/
	public Funcionario(double tempoAtendimento) {
		super(tempoAtendimento);

		numeroFuncionarios += 1;
		idFuncionario = numeroFuncionarios;
	}

	/**
	* Método utilizado apenas para fins de debug.
	* @return String - uma cadeia de caracteres formatada
	* com os atributos da classe.
	*/
	@Override
	public String toString() {
		return super.toString() + "Identificação Funcionário: " + getIdFuncionario() + "\n";
	}

	/**
	* Método que informa o identificador único de um funcionário.
	* @return int - contendo o número que representa o ID.
	*/
	protected int getIdFuncionario() {
		return idFuncionario;
	}

	/**
	* Método que informa quantos objetos da classe Funcionario
	* foram instanciados.
	* @return int - contendo o número que representa
	* quantos funcionario existem no sistema até o momento.
	*/
	protected int getNumeroFuncionarios() {
		return numeroFuncionarios;
	}
}
