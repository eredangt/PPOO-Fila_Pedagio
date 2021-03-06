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
 * Classe que representa um veículo pesado em uma fila de pedágio.
 * Sendo também uma subclasse de Veículo.
 */
class VeiculoPesado extends Veiculo {
	private int numEixos;

	/**
	 * Construtor da subclasse VeiculoPesado herda da classe Veiculo.
	 * @param automatico booleano que define se um veículo é
	 * automatico no atendimento da fila.
     * @param numEixos inteiro que define quantos eixos tem
     * um veículo pesado.
     */
	public VeiculoPesado(boolean automatico, int numEixos) {
		super(automatico);
		this.numEixos = numEixos;
	}

    /**
     * Método que informa quantos eixos tem um veículo.
     * @return int - indicando quantos eixos tem um
     * veículo pesado.
     */
	public int getNumEixos() {
		return numEixos;
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
		else{
			return getTarifaFixa() + (getNumEixos() * getTarifaFixa() * 0.6);
		}
	}
}
