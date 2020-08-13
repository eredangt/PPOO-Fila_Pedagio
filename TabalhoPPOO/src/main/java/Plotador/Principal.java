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

/**
 * Classe que instancia e exibe a janela contendo os gráficos com as
 * estatisticas.
 */
public class Principal {

	/**
	 * Método que inicia a criação do gráfico através das estatísticas coletadas
	 * na Fila de Pedágio.
	 */
	public static void main(String[] args) {
		JanelaGraficos janela = new JanelaGraficos("estatisticas.csv");

		janela.exibir();
	}
}
