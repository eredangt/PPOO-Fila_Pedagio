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
 * Classe que representa um veículo leve em uma fila de pedágio.
 * Sendo também uma subclasse de Veículo.
 */
class VeiculoLeve extends Veiculo {
	private static double tarifaReboque = -1d;
	private boolean reboque;

	/**
	 * Construtor da subclasse VeiculoLeve herda da classe Veiculo
	 * e atribui um booleano indicando se o veículo possui reboque ou não.
	 * @param automatico booleano que define se um veículo é
	 * automatico no atendimento da fila.
     * @param reboque booleano que define se um veículo
     * possui reboque ou não.
     */
	public VeiculoLeve(boolean automatico, boolean reboque) {
		super(automatico);
		this.reboque = reboque;
	}

	/**
	 * Método que define a tarifa de reboque utilizada pelo pedágio.
     * @param tarifaReboque um double que define a tarifa de reboque
     * que será utilizada no cálculo da tarifa do veículo.
	 */
    public static void setTarifaReboque(double tarifaReboque) {
		VeiculoLeve.tarifaReboque = tarifaReboque;
	}

	/**
     * Método que retorna o valor de tarifa de reboque utilizado pelo pedágio.
     * @return double - contendo o valor da tarifa de reboque do pedágio.
     */
     public static double getTarifaReboque() {
		return tarifaReboque;
	 }

    /**
     * Método que retorna se um veículo possui reboque ou não.
     * @return boolean - true se o veículo possuir reboque
     * ou false caso contrário.
     */
	public boolean getReboque() {
		return reboque;
	}

	/**
	 * Método que calcula a tarifa de um veículo.
	 * @return double - representando o valor da tarifa a
	 * ser paga pelo veículo em questão.
	 */
	@Override
	public double calcularTarifa() {
		if(getReboque()) {
			return getTarifaFixa() + getTarifaReboque();
		}
		else {
			return getTarifaFixa();
		}
	}
}
