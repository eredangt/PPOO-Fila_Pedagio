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
public abstract class Veiculo{
    private static int numeroVeiculos;
    private int idVeiculo;
    private boolean isencao;

    /**
     * Construtor incrementa o contador de veículos e 
     * define os Id's de cada veículo a partir desse valor.
     * @param isencao booleano que define se um veículo é
     * isento de tarifa.
     */
    public Veiculo(boolean isencao){
        numeroVeiculos += 1;
        idVeiculo = numeroVeiculos;
        this.isencao = isencao;
    }

    /**
     * Método utilizado apenas para fins de debug.
     * @return String - uma cadeia de caracteres formatada
     * com os atributos da classe.
     */
    public String toString(){
        return String.format("\nVeículo: %s \nIsento: %s", getIdVeiculo(), ( (getIsencao() ) ? "Sim" : "Não"));
    }

    /**
     * Método que informa se um veículo é isento de tarifa.
     * @return boolean - true se o veículo for isento de tarifa
     * ou false caso contrário.
     */
    protected boolean getIsencao(){
        return isencao;
    }

    /**
     * Método que informa quantos objetos da classe veículo
     * foram instanciados.
     * @return int - contendo o número que representa
     * quantos veículos existem no sistema até o momento.
     */
    protected int getNumeroVeiculos(){
        return numeroVeiculos;
    }

    /**
     * Método que informa o identificador único de um veículo.
     * @return int - contendo o número que representa o Id.
     */
    protected int getIdVeiculo(){
        return idVeiculo;
    }

    /**
     * Método abstrato que será sobreescrito nas classes filhas.
     */
    protected abstract double calcularTarifa();
}
