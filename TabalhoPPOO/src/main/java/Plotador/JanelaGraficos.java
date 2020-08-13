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

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;


/**
* Classe responsável pela construção e exibição da janela, a qual conterá
* os gráficos.
*/
public class JanelaGraficos {
	private JFrame janela;

	/**
	* Construtor da classe JanelaGraficos.
	*/
	public JanelaGraficos(String nomeArquivo) {
		janela = new JFrame();

		montarJanela(new GerenciadorDeGraficos(nomeArquivo).getGraficos());
	}

	/**
	 * Método que exibe a janela que contém os gráficos.
	 */
	public void exibir() {
		janela.setSize(1280, 720);
		janela.pack();
		janela.setLocationRelativeTo(null);
		janela.setDefaultCloseOperation(ChartFrame.EXIT_ON_CLOSE);
		janela.setVisible(true);
	}

	/**
	 * Método que renderiza um gráfico definindo fatores visuais.
	 * @param grafico contendo as estatísticas gerados pela simulação.
	 */
	private void inicializaGrafico(JFreeChart grafico) {
		XYPlot plot = grafico.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);
		plot.setForegroundAlpha(0.9f);
		plot.setRangeGridlinePaint(Color.red);
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.black);
		plot.setDomainGridlinesVisible(true);

		grafico.getLegend().setFrame(BlockBorder.NONE);
	}

	/**
	 * Método que monta a janela que conterá os gráficos, definindo o layout
	 * da mesma.
	 * @param graficos lista contendo os gráficos.
	 */
	private void montarJanela(ArrayList<JFreeChart> graficos) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));
		JScrollPane jScrollPane = new JScrollPane(panel);
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		for (JFreeChart grafico : graficos) {
			inicializaGrafico(grafico);
			panel.add(new ChartPanel(grafico));
		}

		janela.getContentPane().add(jScrollPane);
	}
}
