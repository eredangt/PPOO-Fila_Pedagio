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
 * Classe que representa um veículo em uma fila de pedágio.
 * Ela gera um Identificador único para cada veículo e
 * fornece métodos para a manipulação de seus atributos
 * pelas classes filhas.
 */
public abstract class Veiculo {
    private static int numeroVeiculos = 0;
    private static double tarifaFixa;
    private int idVeiculo;
    private boolean automatico;

    /**
     * Construtor incrementa o contador de veículos e
     * define os Id's de cada veículo a partir desse valor.
     * @param automatico booleano que define se um veículo é
     * automatico no atendimento da fila.
     */
    public Veiculo(boolean automatico) {
        numeroVeiculos += 1;
        idVeiculo = numeroVeiculos;
        this.automatico = automatico;
    }

	/**
	 * Método que define a tarifa fixa utilizada pelo pedágio.
     * @param tarifaFixa um double que define a tarifa que será
     * utilizada no calculo da tarifa do veículo.
	 */
    public void setTarifaFixa(double tarifaFixa) {
		this.tarifaFixa = tarifaFixa;
	}

    /**
     * Método utilizado apenas para fins de debug.
     * @return String - uma cadeia de caracteres formatada
     * com os atributos da classe.
     */
    public String toString() {
        return String.format("\nVeículo: %s \nAutomático: %s", getIdVeiculo(), ((getAutomatico()) ? "Sim" : "Não"));
    }

    /**
     * Método abstrato que será sobreescrito nas classes filhas.
     */
    public abstract double calcularTarifa();

    /**
     * Método que informa se um veículo é isento de tarifa.
     * @return boolean - true se o veículo for ser atendido de
     * forma automática ou false caso contrário.
     */
    protected boolean getAutomatico() {
        return automatico;
    }

    /**
     * Método que informa quantos objetos da classe veículo
     * foram instanciados.
     * @return int - contendo o número que representa
     * quantos veículos existem no sistema até o momento.
     */
    protected int getNumeroVeiculos() {
        return numeroVeiculos;
    }

    /**
     * Método que informa o identificador único de um veículo.
     * @return int - contendo o número que representa o Id.
     */
    protected int getIdVeiculo() {
        return idVeiculo;
    }

    /**
     * Método que informa o valor de tarifa fixa utilizado pelo pedágio.
     * @return double - contendo o valor da tarifa do pedágio.
     */
     protected double getTarifaFixa() {
		return tarifaFixa;
	 }
}
