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
import java.util.Collections;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Classe responsável pela criação do gráfico.
 */
public class Grafico {
	private String labelX;
	private String labelY;
	private HashMap<String, Linha> linhas;

	/**
	 * Construtor da classe Grafico.
	 * @param labelX String que representa a legenda do eixo X.
	 * @param labelY String que representa a legenda do eixo Y.
	 */
	public Grafico(String labelX, String labelY) {
		this.labelX = labelX;
		this.labelY = labelY;
		linhas = new HashMap<String, Linha>();
	}

	/**
	 * Método que adiciona um valor no eixo X de uma linha do gráfico.
	 * Se o grafico não possuir uma linha com este nome, ela é criada.
	 * @param nome String que representa o nome da linha.
	 * @param valor inteiro que representa o eixo Y.
	 */
	public void addValorLinha(String nome, int valor) {
		if (!linhas.containsKey(nome))
			putLinha(nome);

		linhas.get(nome).addValor(valor);
	}

	/**
	 * Método que cria o gráfico.
	 * @param titulo String que representa o título do gráfico.
	 * @return JFreeChart - o gráfico com as estatísticas inseridas.
	 */
	public JFreeChart creatJFreeChart(String titulo) {
		XYSeriesCollection dataSet = new XYSeriesCollection();
		ArrayList<String> nomes = new ArrayList<String>(linhas.keySet());
		Collections.sort(nomes);
		for (String nome: nomes) {
			Linha linha = linhas.remove(nome);
			dataSet.addSeries(linha.creatXYSeries(nome));
		}

		JFreeChart grafico = ChartFactory.createXYLineChart(
			titulo,
			labelX,
			labelY,
			dataSet,
			PlotOrientation.VERTICAL,
			true,
			true,
			false
		);

		return grafico;
	}

	/**
	 * Método que cria uma nova linha no gráfico.
	 * @param nome String que representa o nome da linha.
	 */
	private void putLinha(String nome) {
		linhas.put(nome, new Linha());
	}
}
