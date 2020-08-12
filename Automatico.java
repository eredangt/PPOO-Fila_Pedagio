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

import java.util.Random;

/**
* Classe que representa o atendimento por Cobrança Automática no pedágio.
* Sendo uma subclasse de Atendimento.
*/
public class Automatico extends Atendimento {
	/**
	* Construtor da classe Automática.
	* @param tempoAtendimento int que define um tempo base
	* para as operações naquele tipo de atendimento.
	*/
	public Automatico(int tempoAtendimento) {
		super(tempoAtendimento);
	}

	/**
	* Método que retorna o tempo de operação daquele atendimento.
	* @return int - contendo o número o tempo.
	*/
	@Override
	public int getTempoAtendimento() {
		return super.getTempoAtendimento() * (1 + Random.nextInt(10) / 20);
	}
}
