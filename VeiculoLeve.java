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

/**
 * Classe que representa um veículo leve em uma fila de pedágio.
 * Sendo também uma subclasse de veículo.
 */
class VeiculoLeve extends Veiculo {
	private static double tarifaReboque = -1d;
	private boolean reboque;

	/**
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
     * que será utilizada no calculo da tarifa do veículo.
	 */
    public static void setTarifaReboque(double tarifaReboque) {
		VeiculoLeve.tarifaReboque = tarifaReboque;
	}

	/**
     * Método que informa o valor de tarifa de reboque utilizado pelo pedágio.
     * @return double - contendo o valor da tarifa de reboque do pedágio.
     */
     public static double getTarifaReboque() {
		return tarifaReboque;
	 }

    /**
     * Método que informa se um veículo possui reboque ou não.
     * @return boolean - true se o veículo possuir reboque
     * ou false caso contrário.
     */
	public boolean getReboque() {
		return reboque;
	}

    /**
     * Método utilizado apenas para fins de debug.
     * @return String - uma cadeia de caracteres formatada
     * com os atributos da classe.
     */
	@Override
	public String toString() {
		return String.format("%s\nReboque: %s\n", super.toString(), ( getReboque() ) ? "Sim" : "Não");
	}

	/**
	 * Método que calcula a tarifa de um veiculo.
	 * @return double - representando o valor da tarifa a
	 * ser paga pelo veículo em questão.
	 */
	@Override
	public double calcularTarifa() {
		if(getAutomatico()) {
			return 0d;
		}
		else if(getReboque()) {
			return getTarifaFixa() + getTarifaReboque();
		}
		else{
			return getTarifaFixa();
		}
	}
}
