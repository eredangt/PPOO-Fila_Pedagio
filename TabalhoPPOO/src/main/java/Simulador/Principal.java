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

package Simulador;

/**
* Classe Principal que instancia o Simulador de Eventos e inicia sua simulação.
*/
public class Principal {

	/**
	 * Método que inicia a simulação da fila de pedágio.
	 */
	public static void main(String[] args) {
        String nomeArquivoEntrada = "dadosEntrada.txt";
		String nomeArquivosSaida = "estatisticas";
		
		Simulador pedagio = new Simulador(nomeArquivoEntrada, nomeArquivosSaida);

		try {
			pedagio.iniciarSimulacao();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
