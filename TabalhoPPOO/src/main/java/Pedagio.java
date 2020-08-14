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

import Simulador.GerenciadorDeDados;
import Simulador.Simulador;
import Plotador.JanelaGraficos;

/**
 * Classe que instancia o Simulador de Eventos e inicia sua simulação.
 * Também instancia e exibe a janela contendo os gráficos com as
 * estatisticas.
 */
public class Pedagio {
	public static void main(String[] args) {
        String nomeArquivoEntrada = "dadosEntrada.txt";
		String nomeArquivosSaida = "estatisticas";
		
		Simulador pedagio = new Simulador(nomeArquivoEntrada, nomeArquivosSaida);

		try {
			GerenciadorDeDados.gerarArquivoDeFormatacao();
			pedagio.iniciarSimulacao();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
        }

		JanelaGraficos janela = new JanelaGraficos(nomeArquivosSaida + ".csv");

		janela.exibir();
	}
}
