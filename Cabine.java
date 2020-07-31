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

import java.util.Set;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Collections;

/**
* Classe que representa a Cabine em um pedágio.
* Ela gera um Identificador único para cada cabine criada e
* fornece métodos para a manipulação de seus atributos.
* Uma cabine utiliza apenas um tipo de atendimento.
*/
public class Cabine {
    private static int numeroCabines = 0;
    private int idCabine;
    private Queue<Integer> filaVeiculos;
    private HashMap<Integer,Integer> tamanhosFila;
    private int idAtendimento;
    private LinkedList<Integer> tempoEsperaLeve;
    private LinkedList<Integer> tempoEsperaPesado;
    private String estatisticas;

    /**
    * Construtor incrementa o contador de Cabines e
    * define os IDs de cada Cabine a partir do valor obtido.
    * O construtor é responsável por criar uma fila de Veículos.
    * @param atendimento - objeto que representa um tipo de
    * atendimento.
    */
    public Cabine(int idAtendimento) {
        numeroCabines += 1;
        idCabine = numeroCabines;
        filaVeiculos = new LinkedList<Integer>();
        tamanhosFila = new HashMap<Integer,Integer>();
        tempoEsperaLeve = new LinkedList<Integer>();
        tempoEsperaPesado = new LinkedList<Integer>();
        this.idAtendimento = idAtendimento;
        estatisticas = "Cabine," + idCabine + "\n" +
                       "Tempo,Tempo médio de espera dos veiculos leves," + 
                       "Tempo médio de espera dos veiculos pesados," +
                       "Tempo médio de espera dos veiculos," +
                       "Tamanho médio da fila da cabine\n";
    }

    /**
    * Método utilizado apenas para fins de debug.
    * @return String - uma cadeia de caracteres formatada
    * com os atributos da classe.
    */
    public String toString() {
        String dadosCompletos = "";
        dadosCompletos = String.format("Número da Cabine: %s\n", getIdCabine());
        dadosCompletos += "--------------------\n";

        for (int veiculoAtual : filaVeiculos) {
            dadosCompletos += veiculoAtual;
            dadosCompletos += "--------------------\n";
        }

        return dadosCompletos;
    }

    public void concatenarEstatisticas(int tempo) {
        int TMVL, TMVP, TMV, TMFC;
        try {
            TMVL = getMediaTempoEspera("Leve");
            TMVP = getMediaTempoEspera("Pesado");
            TMV = getMediaTempoEspera("Total");
            TMFC = getTamanhoMaxFila();
            
            estatisticas += String.format("%d,%d,%d,%d,%d\n", tempo, TMVL, TMVP, TMV, TMFC);
        }
        catch (Exception e) {
            System.out.println("Ta aqui " + e.getMessage());
        }
    }

    public String getEstatisticas() {
        return estatisticas;
    }

    /**
    * Método que retorna o objeto de Atendimento.
    * @return Atendimento - retorna o objeto do Atendimento.
    */
    public int getIdAtendimento(){
        return idAtendimento;
    }

    /**
    * Método que informa o identificador único de uma cabine.
    * @return int - contendo o número que representa o ID da cabine.
    */
    public int getIdCabine() {
        return idCabine;
    }

    /**
    * Método que informa quantos objetos da classe Cabine
    * foram instanciados.
    * @return int - contendo o número que representa
    * quantas cabines existem no sistema.
    */
    public int getNumeroCabines() {
        return numeroCabines;
    }

    public int getTamanhoMedioFila() {
        int numerador = 0;
        int denominador = 1;
        for (Integer chave : tamanhosFila.keySet()) {
            Integer valor = tamanhosFila.get(chave);

            System.out.println("<" + chave + ", " + valor + ">");

            numerador += (chave * valor);

            denominador += valor;
        }
        return (int)(numerador/(denominador - 1));
    }

    public int getTamanhoMaxFila() {
        Set<Integer> conjunto = tamanhosFila.keySet();
        LinkedList<Integer> sortedList = new LinkedList<Integer>(conjunto);

        int tamanho = sortedList.size();
        if (tamanho == 0) {
            return 0;
        }

        Collections.sort(sortedList);

        return sortedList.get(tamanho - 1);
    }

    public int getMediaTempoEspera(String tipoVeiculo) {
        int soma = 0;
        int tamanho = 0;
        
        if (!tipoVeiculo.equals("Pesado")) {
            LinkedList<Integer> listaUtilizada = tempoEsperaLeve;
            soma += getSomaLista(listaUtilizada);
            tamanho += listaUtilizada.size();
        }
        if (!tipoVeiculo.equals("Leve")) {
            LinkedList<Integer> listaUtilizada = tempoEsperaPesado;
            soma += getSomaLista(listaUtilizada);
            tamanho += listaUtilizada.size();
        }
        
        if (tamanho == 0) {
            return 0;
        }

        return (int)(soma / tamanho);
    }

    private int getSomaLista(LinkedList<Integer> lista) {
        int somador = 0;
        for (int tempo : lista) {
            somador += tempo;
        }
        return somador;
    }

    public void adicionaNovoTamanho(Integer novoTamanho) {
        Set<Integer> tamanho = tamanhosFila.keySet();
        if (tamanho.contains(novoTamanho)) {
            Integer contador = tamanhosFila.get(novoTamanho);
            tamanhosFila.put(novoTamanho, contador + 1);
        }
        else {
            tamanhosFila.put(novoTamanho, 1);
        }

    }

    public void armazenaTempo(String tipoVeiculo, int tempo) {
        if (tipoVeiculo.equals("Leve")) {
            tempoEsperaLeve.add(tempo);
        }
        else {
            tempoEsperaPesado.add(tempo);
        }
    }

    /**
    * Método que coloca um veículo na fila.
    */
    public void enfileirarVeiculo(int idVeiculo) {
        filaVeiculos.offer(idVeiculo);
    }

    /**
    * Método que retira um veículo da fila.
    * Caso ocorra a tentativa de retirar um veículo na fila vazia,
    * uma mensagem de erro é retornada.
    * @return Veiculo - remove o Veículo da fila com sucesso.
    * @return NoSuchElementException - mensagem de erro ao remover
    * um objeto em uma fila vazia.
    */
    public int desenfileirarVeiculo() throws Exception{
        try {
            int idVeiculoRemovido = filaVeiculos.remove();
            return idVeiculoRemovido;
        }
        catch (NoSuchElementException e) {
            throw new Exception("Nenhum veiculo encontrado\n");
        }
    }

    /**
    * Método que calcula o tamanho da fila de veículos no pedágio.
    * @return int - contendo a quantidade de veículos na fila.
    */
    public int calcularTamanho() {
        return filaVeiculos.size();
    }

    public boolean vazia() {
        return filaVeiculos.isEmpty();
    }
}
