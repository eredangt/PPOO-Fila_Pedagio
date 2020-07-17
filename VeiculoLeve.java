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
class VeiculoLeve extends Veiculo{
	private boolean reboque;

	/**
     * @param isencao booleano que define se um veículo é
     * isento de tarifa.
     * @param reboque booleano que define se um veículo
     * possui reboque ou não.
     */
	public VeiculoLeve(boolean isencao, boolean reboque){
		super(isencao);
		this.reboque = reboque;
	}

    /**
     * Método que informa se um veículo possui reboque ou não.
     * @return boolean - true se o veículo possuir reboque 
     * ou false caso contrário.
     */
	public boolean getReboque(){
		return reboque;
	}

    /**
     * Método utilizado apenas para fins de debug.
     * @return String - uma cadeia de caracteres formatada
     * com os atributos da classe.
     */
	@Override
	public String toString(){
		return String.format("%s\nReboque: %s\n", super.toString(), ( getReboque() ) ? "Sim" : "Não");
	}

	/**
	 * Método que calcula a tarifa de um veiculo.
	 * @return double - representando o valor da tarifa a
	 * ser paga pelo veículo em questão.
	 */
	@Override
	public double calcularTarifa(){
		return 0.00;
	}
}
