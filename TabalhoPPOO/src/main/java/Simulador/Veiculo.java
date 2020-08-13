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
 * Classe que representa um veículo em uma fila de pedágio.
 * Ela gera um Identificador único para cada veículo e
 * fornece métodos para a manipulação de seus atributos.
 * Podendo ser um VeiculoLeve ou VeiculoPesado, sendo estendido por
 * essas classes.
 */
public abstract class Veiculo {
    private static int numeroVeiculos = 0;
    private static double tarifaFixa = -1d;
    private int idVeiculo;
    private boolean automatico;
    private int tempoEspera;

    /**
     * Construtor incrementa o contador de veículos e
     * define os ID's de cada veículo a partir desse valor, inicializando
     * os atributos próprios da classe.
     * @param automatico boolean que define se um veículo é
     * automático no atendimento da fila.
     */
    public Veiculo(boolean automatico) {
        numeroVeiculos += 1;
        idVeiculo = numeroVeiculos;
        this.automatico = automatico;
        this.tempoEspera = 0;
    }

    /**
     * Método abstrato que será sobreescrito nas classes filhas.
     * @return double - representando o valor da tarifa a
	 * ser paga pelo veículo em questão.
     */
    public abstract double calcularTarifa();

     /**
     * Método que define a tarifa fixa utilizada pelo pedágio.
     * @param tarifaFixa um double que define a tarifa que será
     * utilizada no cálculo da tarifa do veículo.
     */
    public static void setTarifaFixa(double tarifaFixa) {
        Veiculo.tarifaFixa = tarifaFixa;
    }

    /**
    * Método que define o tempo de espera do Veículo.
    * @param tempo valor para definir o tempo de espera.
    */
    public void setTempoEspera(int tempo) {
        tempoEspera = tempo;
    }

    /**
     * Método que retorna o valor de tarifa fixa utilizado pelo pedágio.
     * @return double - contendo o valor da tarifa do pedágio.
     */
    public static double getTarifaFixa() {
        return tarifaFixa;
     }

     /**
     * Método que retorna o tempo de espera de um determinado veículo na fila.
     * @return int - contendo o tempo de espera na fila.
     */
    public int getTempoEspera() {
        return tempoEspera;
    }

    /**
     * Método que retorna se um veículo tem acesso ao atendimento automático.
     * @return boolean - true se o veículo for ser atendido de
     * forma automática ou false caso contrário.
     */
    protected boolean getAutomatico() {
        return automatico;
    }

    /**
     * Método que retorna quantos objetos da classe veículo
     * foram instanciados.
     * @return int - contendo o número que representa
     * quantos veículos existem no sistema até o momento.
     */
    protected int getNumeroVeiculos() {
        return numeroVeiculos;
    }

    /**
     * Método que retorna o identificador único de um veículo.
     * @return int - contendo o número que representa o ID.
     */
    protected int getIdVeiculo() {
        return idVeiculo;
    }
}
