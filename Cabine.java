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

public class Cabine {
	private static int numeroCabines = 0;
	private int idCabine;
	private Queue<Veiculo> filaVeiculos;

	public Cabine() {
		numeroCabines += 1;
		idCabine = numeroCabines;
		filaVeiculos = new LinkedList<Veiculo>();
	}

	public int getNumeroCabines() {
		return numeroCabines;
	}

	public int getIdCabine() {
		return idCabine;
	}

	public void enfileirarVeiculo(Veiculo v) {
		filaVeiculos.add(v);
	}

	public Veiculo desenfileirarVeiculo() {
		return filaVeiculos.remove();
	}

	public int calcularTamanho() {
		return filaVeiculos.size();
	}

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
