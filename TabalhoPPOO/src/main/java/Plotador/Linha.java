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

package Plotador;

import java.util.ArrayList;

import org.jfree.data.xy.XYSeries;

public class Linha {
	private ArrayList<Integer> valoresEixoY;
	
	public Linha() {
		valoresEixoY = new ArrayList<Integer>();
	}
	
	public void addValor(int valor) {
		valoresEixoY.add(valor);
	}
	
	public XYSeries creatXYSeries(String nome) {
		XYSeries linha = new XYSeries(nome);
		int tamanho = valoresEixoY.size();
		for (int x = 0; x < tamanho; x ++) {
			int valor = valoresEixoY.remove(0);
			linha.add(x, valor);
		}
		
		return linha;
	}
}
