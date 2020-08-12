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
    private double valorTotalGanho;

    /**
    * Construtor incrementa o contador de Cabines e
    * define os IDs de cada Cabine a partir do valor do contador.
    * O construtor é responsável por criar uma fila de Veículos.
    * @param idAtendimento inteiro que representa um identificador
    * único de um atendimento.
    */
    public Cabine(int idAtendimento) {
        numeroCabines += 1;
        idCabine = numeroCabines;
        filaVeiculos = new LinkedList<Integer>();
        tamanhosFila = new HashMap<Integer,Integer>();
        tempoEsperaLeve = new LinkedList<Integer>();
        tempoEsperaPesado = new LinkedList<Integer>();
        this.idAtendimento = idAtendimento;
        valorTotalGanho = 0d;
        estatisticas = "Cabine," + idCabine + "\n" +
                       "Tempo,Tempo médio de espera dos veículos leves," +
                       "Tempo médio de espera dos veículos pesados," +
                       "Tempo médio de espera dos veículos," +
                       "Tamanho médio da fila da cabine," +
                       "Tamanho máximo da fila da cabine\n";
    }

    /**
     * Método que concatena as estatisticas da cabine em um instante da
     * execução:
     * - Tempo médio de espera dos veículos leves.
     * - Tempo médio de espera dos veículos pesados.
     * - Tempo médio de espera dos veículos.
     * - Tamanho médio da fila da cabine.
     * - Tamanho máximo da fila da cabine.
     * @param tempo inteiro que representa o tempo atual da execução.
     */
    public void concatenarEstatisticas(int tempo) {
        int TMVL, TMVP, TMV, TMedFC, TMaxFC;

        TMVL = getMediaTempoEspera("Leve"); // Tempo médio de espera dos veículos leves
        TMVP = getMediaTempoEspera("Pesado"); // Tempo médio de espera dos veículos pesados
        TMV = getMediaTempoEspera("Total"); // Tempo médio de espera dos veículos
        TMedFC = getTamanhoMedioFila(); // Tamanho médio da fila da cabine
        TMaxFC = getTamanhoMaxFila(); // Tamanho máximo da fila da cabine

        estatisticas += String.format("%d,%d,%d,%d,%d,%d\n", tempo, TMVL, TMVP, TMV, TMedFC, TMaxFC);
    }

    /**
    * Método que retorna as estatísticas geradas com os dados da simulação.
    * @return String - retorna as estatísticas geradas.
    */
    public String getEstatisticas() {
        return estatisticas;
    }

    /**
    * Método que retorna o identificador do Atendimento.
    * @return Atendimento - retorna o identificador do Atendimento.
    */
    public int getIdAtendimento(){
        return idAtendimento;
    }

    /**
    * Método que retorna o identificador único de uma cabine.
    * @return int - contendo o número que representa a ID da cabine.
    */
    public int getIdCabine() {
        return idCabine;
    }

    /**
    * Método que retorna quantos objetos da classe Cabine
    * foram instanciados.
    * @return int - contendo o número que representa
    * quantas cabines existem no sistema.
    */
    public int getNumeroCabines() {
        return numeroCabines;
    }

    /**
    * Método que retorna o numerador da fração do cálculo da média
    * ponderada dos tamanhos da fila da cabine.
    * @return int - valor que representa o numerador da fração da média ponderada
    * dos tamanhos da fila da cabine em questão.
    */
    public int getNumeradorMediaPonderadaTamanhos() {
        int numerador = 0;

        for (Integer chave : tamanhosFila.keySet()) {
            Integer valor = tamanhosFila.get(chave);
            numerador += (chave * valor);
        }
        return numerador;
    }

    /**
    * Método que retorna o denominador da fração do cálculo da média
    * ponderada dos tamanhos da fila da cabine.
    * @return int - valor que representa o denominador da fração da média
    * ponderada dos tamanhos da fila da cabine em questão.
    */
    public int getDenominadorMediaPonderadaTamanhos() {
        int denominador = 0;

        for (Integer chave : tamanhosFila.keySet()) {
            Integer valor = tamanhosFila.get(chave);
            denominador += valor;
        }

        return denominador;
    }

    /**
    * Método que retorna o tamanho médio da fila. Cria uma variável que chama
    * a função getNumeradorMediaPonderadaTamanhos() e getDenominadorMediaPonderadaTamanhos();
    * para calcular a média.
    * @return int - valor do tamanho médio da fila.
    */
    public int getTamanhoMedioFila() {
        int numerador = getNumeradorMediaPonderadaTamanhos();
        int denominador = getDenominadorMediaPonderadaTamanhos();
        if (denominador == 0) {
            return 0;
        }
        else {
            return (int)(numerador/denominador);
        }
    }

    /**
     * Método que encontra e retorna o tamanho máximo da fila dessa cabine.
     * @return int - valor que representa o tamanho máximo da fila dessa Cabine.
     */
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

    /**
    * Método que retorna o tamanho da lista que possui os tempos de espera.
    * Podendo considerar as listas para veículos leves, pesados ou ambos.
    * @param tipoVeiculo String simbolizando o tipo do veículo. "Pesado", obtem
    * o tamanho da lista de espera dos veículos pesados; "Leve", obtem o
    * tamanho da lista de espera dos veículos leves; qualquer outra String,
    * o método retornará a soma de ambos os tamanhos.
    * @return int - tamanho da lista de tempos de espera.
    */
    public int getTamanhosListasEspecificas(String tipoVeiculo) {
        int tamanho = 0;

        if (!tipoVeiculo.equals("Pesado")) {
            LinkedList<Integer> listaUtilizada = tempoEsperaLeve;
            tamanho += listaUtilizada.size();
        }
        if (!tipoVeiculo.equals("Leve")) {
            LinkedList<Integer> listaUtilizada = tempoEsperaPesado;
            tamanho += listaUtilizada.size();
        }
        return tamanho;
    }

    /**
    * Método que retorna a soma dos tempos de espera, baseado em um tipo de veículo.
    * Podendo considerar as listas para veículos leves, pesados ou ambos.
    * @param tipoVeiculo String simbolizando o tipo do veículo. "Pesado", obtem
    * soma dos tempos na lista de espera dos veículos pesados; "Leve", obtem
    * soma dos tempos na lista de espera dos veículos leves; qualquer outra String,
    * o método retornará a soma de ambas as listas.
    * @return int - soma dos tempos na lista de tempos de espera.
    */
    public int getSomasTempoEspera(String tipoVeiculo) {
        int soma = 0;

        if (!tipoVeiculo.equals("Pesado")) {
            LinkedList<Integer> listaUtilizada = tempoEsperaLeve;
            soma += getSomaLista(listaUtilizada);
        }
        if (!tipoVeiculo.equals("Leve")) {
            LinkedList<Integer> listaUtilizada = tempoEsperaPesado;
            soma += getSomaLista(listaUtilizada);
        }
        return soma;
    }

    /**
    * Método que informa o tempo médio de espera, utilizando os métodos
    * getSomasTempoEspera(tipoVeiculo) e getTamanhosListasEspecificas(tipoVeiculo).
    * Podendo considerar as listas para veículos leves, pesados ou ambos.
    * Sendo definido pela String que recebe, sendo "Leve", "Pesado" ou qualquer
    * outra String. Caso receba essa String qualquer, irá considerar tanto
    * veículos leves quanto pesados.
    * @param tipoVeiculo String simbolizando o tipo do veículo.
    * @return int - retorna a média do tempo de espera.
    */
    public int getMediaTempoEspera(String tipoVeiculo) {
        int tempos = getSomasTempoEspera(tipoVeiculo);
        int tamanho = getTamanhosListasEspecificas(tipoVeiculo);

        if (tamanho == 0) {
            return 0;
        }
        else {
            return (int)(tempos/tamanho);
        }
    }

    /**
     * Método que retorna o valor total ganho pela cabine até o momento
     * no qual é chamado.
     * @return double - representando o valor total ganho por uma cabine
     * específica.
     */
    public double getValorTotalGanho() {
        return valorTotalGanho;
    }

    /**
    * Método que adiciona um novo tamanho no HashMap armazenando os tamanhos alcançados
    * pela fila da cabine. Verifica se o tamanho já existe no HashMap, se já existir apenas
    * insere o tamanho novamente atualizando o contador (chave) do HashMap.
    * Caso o tamanho ainda não exista, insere o tamanho no HashMap com contador igual a um
    * (considerando que até o momento essa foi a primeira ocorrência desse tamanho na fila).
    * @param novoTamanho um novo tamanho em determinado tempo.
    */
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

    /**
    * Método que adiciona o tempo na lista de tempos de espera específico
    * de um tipo de veículo e tempo de espera.
    * @param tipoVeiculo String simbolizando o tipo do veículo, "Leve" ou
    * "Pesado".
    * @param tempo tempo de espera de um tipo veículo.
    * @throws RuntimeException mensagem de erro informando que uma String
    * de parâmetro incorreta foi recebida.
    */
    public void armazenaTempo(String tipoVeiculo, int tempo) throws RuntimeException {
        if (tipoVeiculo.equals("Leve")) {
            tempoEsperaLeve.add(tempo);
        }
        else if (tipoVeiculo.equals("Pesado")) {
            tempoEsperaPesado.add(tempo);
        }
        else {
            throw new RuntimeException("Função 'armazenaTempo(String tipoVeiculo, int tempo)' recebeu um tipo de Veículo inválido.");
        }
    }

    /**
    * Método que coloca um veículo na fila.
    * @param idVeiculo identificador do veículo.
    */
    public void enfileirarVeiculo(int idVeiculo) {
        filaVeiculos.offer(idVeiculo);
    }

    /**
    * Método que retira um veículo da fila.
    * Caso ocorra a tentativa de retirar um veículo na fila vazia,
    * uma mensagem de erro é retornada.
    * @return Veiculo - remove o Veículo da fila com sucesso.
    * @throws Exception mensagem de erro ao remover
    * um objeto em uma fila vazia.
    */
    public int desenfileirarVeiculo() throws Exception {
        try {
            int idVeiculoRemovido = filaVeiculos.remove();
            return idVeiculoRemovido;
        }
        catch (NoSuchElementException e) {
            throw new Exception("Nenhum veiculo encontrado.\n");
        }
    }

    /**
    * Método que calcula o tamanho da fila de veículos no pedágio.
    * @return int - contendo a quantidade de veículos na fila.
    */
    public int calcularTamanho() {
        return filaVeiculos.size();
    }

    /**
    * Método que verifica se a fila de veículos está vazia.
    * @return boolean - true se a fila de veículos está vazia, false caso
    * contrário.
    */
    public boolean vazia() {
        return filaVeiculos.isEmpty();
    }

    /**
     * Método que computa uma cobrança gerada no momento que um veículo
     * é atendido pela cabine em questão.
     * @param novoValor um double que será somado ao total ganho pela
     * cabine.
     */
     public void computaCobranca(double novoValor) {
        valorTotalGanho += novoValor;
     }

     /**
     * Método que retorna o somatório dos tempos de espera de determinada lista.
     * @param lista lista com os tempos de espera.
     * @return int - contendo o somatório dos tempos de espera da lista
     * passada por parâmetro.
     */
     private int getSomaLista(LinkedList<Integer> lista) {
         int somador = 0;
         for (int tempo : lista) {
             somador += tempo;
         }
         return somador;
     }
}
