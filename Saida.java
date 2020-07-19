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
* Classe que representa a saída de um veículo do pedágio.
*/
public class Saida extends Evento {

	/*
     * Construtor da classe Saida.
     * @param tempoEvento tempo final do evento.
     * @param cabine a qual o veiculo está.
     */
    public Saida(int tempoEvento, Cabine cabine) {
		super(tempoEvento, cabine);
    }
    
    /*
     * Método que desenfileira um veiculo de uma
     * determinada cabine passada por parâmetro.
     * @param idCabine cabine a qual terá o veiculo removido.
     */    
    public void desenfileirar(int idCabine) {
        filaVeiculos.remove(0);
    }
}
