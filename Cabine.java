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

import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
* Classe que representa a Cabine em um pedágio.
* Ela gera um Identificador único para cada cabine criada e 
* fornece métodos para a manipulação de seus atributos.
* Uma cabine utiliza apenas um tipo de atendimento.
*/
public class Cabine {
	private static int numeroCabines = 0;
	private int idCabine;
	private Queue<Veiculo> filaVeiculos;

	/**
	* Construtor incrementa o contador de Cabines e 
	* define os IDs de cada Cabine a partir do valor obtido.
	* O construtor é responsável por criar uma fila de Veículos.
	*/
	public Cabine() {
		numeroCabines += 1;
		idCabine = numeroCabines;
		filaVeiculos = new LinkedList<Veiculo>();
	}
	
	/**
	* Método que informa quantos objetos da classe Cabine
	* foram instanciados.
	* @return int - contendo o número que representa
	* quantas cabines existem no sistema.
	*/
	public int getNumeroCabines() {
		return numeroCabines;
	}
	
	/**
	* Método que informa o identificador único de uma cabine.
	* @return int - contendo o número que representa o ID da cabine.
	*/
	public int getIdCabine() {
		return idCabine;
	}

	/**
	* Método que coloca um veículo na fila.
	*/
	public void enfileirarVeiculo(Veiculo v) {
		filaVeiculos.offer(v);
	}
	/**
	* Método que retira um veículo da fila.
	* Caso ocorra a tentativa de retirar um veículo na fila vazia,
	* uma mensagem de erro é retornada.
	* @return Veiculo - remove o Veículo da fila com sucesso.
	* @return NoSuchElementException - mensagem de erro ao remover 
	* um objeto em uma fila vazia.
	*/
	public Veiculo desenfileirarVeiculo() {
		try {
			return filaVeiculos.remove();
		}
		catch (NoSuchElementException e) {
			throw e;
		}
	}
	
	/**
	* Método que calcula o tamanho da fila de veículos no pedágio.
	* @return int - contendo a quantidade de veículos na fila.
	*/
	public int calcularTamanho() {
		return filaVeiculos.size();
	}
	
	/**
	* Método utilizado apenas para fins de debug.
	* @return String - uma cadeia de caracteres formatada
	* com os atributos da classe.
	*/
	public String toString() {
		String dadosCompletos = "";
		dadosCompletos = String.format("Número da Cabine: %s\n", getIdCabine());
		dadosCompletos += "--------------------\n";
		
		for (Veiculo v : filaVeiculos) {
			dadosCompletos += v.toString();
			dadosCompletos += "--------------------\n";
		}
		
		return dadosCompletos;
	} 
}
