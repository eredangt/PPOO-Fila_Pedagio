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
import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

/**
* Classe que realiza a Simulação da Fila do Pedágio.
* A classe possui atributos que correspondem a fila aleatória ou não
* (filaRand), inicializa os arquivos de Entrada.
* Cria as Listas de Cabine, Veículos e Atendimentos, e também, a FilaEventos
* de Eventos.
*/
public class Simulador {
    private boolean filaRand;
    private String nomeArquivoEntrada;
    private String nomeArquivosSaida;
    private int tempoTotalSimulado;
    private int intervaloChegada;
    private int numeroEventosTratados;
    private PriorityQueue<Evento> filaEventos;
    private Set<Integer> temposEventos;
    private HashMap<Integer, Cabine> cabines;
    private HashMap<Integer, Veiculo> veiculos;
    private HashMap<Integer, Atendimento> atendimentos;
    private String estatisticas;

    /**
    * Construtor cria as listas de: Lista Cabines, Lista Veículos,
    * Lista Atendimentos e, cria a Fila de Eventos.
    * Incializo os atributos da classe com o valor 0.
    * Além de nomear o Arquivo de Entrada.
    */
    public Simulador() {
        numeroEventosTratados = 0;
        tempoTotalSimulado = 0;
        nomeArquivoEntrada = "dadosEntrada.txt";
        nomeArquivosSaida = "estatisticas";
        filaEventos = new PriorityQueue<Evento>();
        temposEventos = new LinkedHashSet<Integer>();
        cabines = new HashMap<Integer, Cabine>();
        veiculos = new HashMap<Integer, Veiculo>();
        atendimentos = new HashMap<Integer, Atendimento>();
        estatisticas = "Simulador\n" +
                       "Tempo,Tempo médio de espera geral dos veiculos leves," +
                       "Tempo médio de espera geral dos veiculos pesados," +
                       "Tempo médio de espera geral dos veiculos," +
                       "Tamanho médio geral das filas," +
                       "Tamanho maxímo da maior fila\n";
    }

    /**
    * Método que concatena as estatisticas do simulador em certo tempo de
    * execução. Serve também, para debugar os dados, também será utilizada
    *  no arquivo texto.
    * @param tempo instante de tempo da execução.
    */
    private void concatenarEstatisticas(int tempo) {
        int TMGVL, TMGVP, TMGV, TMedF, TMaxF;
        TMGVL = getTempoMedioEsperaGeral(0, "Leve"); // Tempo médio de espera geral dos veiculos leves
        TMGVP = getTempoMedioEsperaGeral(0, "Pesado"); // Tempo médio de espera geral dos veiculos pesados
        TMGV = getTempoMedioEsperaGeral(0, "Total"); // Tempo médio de espera geral dos veiculos
        TMedF = getTamanhoMedioFilaGeral(0); // Tamanho médio geral das filas
        TMaxF = getTamanhoMaiorFila(0); // Tamanho maxímo da maior fila

        estatisticas += String.format("%d,%d,%d,%d,%d,%d\n", tempo, TMGVL, TMGVP, TMGV, TMedF, TMaxF);
    }

    /**
    * Método que inicia a Simulação.
    * Inicializa a filaRand, intervaloChegada, os atendimentos, os Veículos e as Cabines.
    * O método enfileirarChegadas() verifica se a fila será aleatória ou não.
    * O método executarSimulacao() retira os eventos da fila.
    * @throws Exception erros durante a inicialização dos dados do
    * simulador.
    */
    public void iniciarSimulacao() throws Exception {
        ArrayList<Object> objetos = null;
        try {
            objetos = GerenciadorDeDados.inicializarDados(nomeArquivoEntrada);
        }
        catch (Exception e) {
            throw e;
        }

        for (Object obj: objetos) {
            if (obj instanceof Boolean)
                filaRand = (Boolean) obj;

            else if (obj instanceof Integer)
                intervaloChegada = (Integer) obj;

            else if (obj instanceof Cabine) {
                Cabine novaCabine = (Cabine) obj;
                cabines.put(novaCabine.getIdCabine(), novaCabine);
            }

            else if (obj instanceof Veiculo) {
                Veiculo novoVeiculo = (Veiculo) obj;
                veiculos.put(novoVeiculo.getIdVeiculo(), novoVeiculo);
            }

            else if (obj instanceof Atendimento) {
                Atendimento novoAtendimento = (Atendimento) obj;
                atendimentos.put(novoAtendimento.getIdAtendimento(), novoAtendimento);
            }
        }

        try {
            enfileirarChegadas();
        }
        catch (Exception e) {
            throw e;
        }

        try {
            executarSimulacao();
        }
        catch (Exception e) {
            throw e;
        }

    }

    /**
    * Método que calcula o tempo gasto na execução.
    * @return long retorna o tempo gasto na execução.
    */
    public long calcularTempoExecucao() {
        return 0L;
    }

    /**
    * Método que gera as Estatisticas da fila de Pegágio, as quais serão salvas
    * no arquivo texto.
    * @return String - retorna as estatísticas ao final da simulação.
    */
    public String gerarEstatisticas() {
        String texto;
        String textoMaioresTamanhos = getTextoTamanhos("Maior");
        String textoMedioTamanhos = getTextoTamanhos("Medio");
        String textoMedioAtendimentosTotal = getTextoMedioAtendimentos("Total");
        String textoMedioAtendimentosEspecífico = "";
        textoMedioAtendimentosEspecífico += getTextoMedioAtendimentos("Leve");
        textoMedioAtendimentosEspecífico += getTextoMedioAtendimentos("Pesado");

        texto = String.format("Tempo total de simulacao: %d\n\n", getTempoTotalSimulado()) +
                String.format("Numero de eventos tradados: %d\n\n", getNumeroEventosTratados()) +
                String.format("Tempos médios de espera em cada fila de atendimento:\n\n%s", textoMedioAtendimentosTotal) +
                String.format("Tamanho médio das filas de atendimento:\n\n%s", textoMedioTamanhos) +
                String.format("Tamanho máximo das filas de atendimento:\n\n%s", textoMaioresTamanhos) +
                String.format("Tempo médio de atendimento de cada tipo de cliente:\n\n%s", textoMedioAtendimentosEspecífico);

        return texto;
    }

    /**
    * Método que retona um texto com informações sobre o tempo médio de
    * Atendimentos. Caso o parâmetro seja "Leve" irá considerar apenas os
    * veículos leves, caso o parâmetro seja "Pesado" irá considerar apenas os
    * veículos pesados, caso seja qualquer outra String irá levar ambos os
    * tipos em consideração.
    * Método que serve para debugar os dados, também será utilizado no arquivo texto.
    * @param abordagem String simbolizando o tipo do veículo, podendo ser
    * "Leve", "Pesado", ou qualquer outra String.
    * @return String - mensagem informando o tempo médio de espera dos tipos de veículo.
    */
    public String getTextoMedioAtendimentos(String abordagem) {
        String texto = "";
        int idCabineAtual = 0;
        int tempo = 0;

        if (abordagem.equals("Leve")) {
            texto += "Média de tempo de espera de Veículos Leves:\n";
        }
        else if (abordagem.equals("Pesado")) {
            texto += "Média de tempo de espera de Veículos Pesados:\n";
        }

        for (Map.Entry<Integer, Cabine> cabine : cabines.entrySet()) {
            idCabineAtual = cabine.getKey();
            tempo = getTempoMedioEsperaGeral(idCabineAtual, abordagem);

            texto += String.format("Fila da Cabine %d: %d\n", idCabineAtual, tempo);
        }
        return texto + "\n";
    }

    /**
    * Método que retona um texto com informações sobre o tempo médio de
    * Atendimentos. Caso o parâmetro seja "Maior" irá retornar o maior tamanho
    * da fila, caso o parâmetro seja "Medio" irá retornar o tamanho médio das filas.
    * Método que serve para debugar os dados, também será utilizada no arquivo texto.
    * @param abordagem String simbolizando o "Maior" ou valor "Medio"
    * @return String - mensagem informando o tempo médio de espera dos tipos de veículo.
    */
    public String getTextoTamanhos(String abordagem) {
        String texto = "";
        int idCabineAtual = 0;
        int tamanho = 0;

        if (abordagem.equals("Maior")) {
            for (Map.Entry<Integer, Cabine> cabine : cabines.entrySet()) {
                idCabineAtual = cabine.getKey();
                tamanho = getTamanhoMaiorFila(idCabineAtual);

                texto += String.format("Fila da Cabine %d: %d\n", idCabineAtual, tamanho);
            }
        }
        else { // if (abordagem.equals("Medio") {
            for (Map.Entry<Integer, Cabine> cabine : cabines.entrySet()) {
                idCabineAtual = cabine.getKey();
                tamanho = getTamanhoMedioFilaGeral(idCabineAtual);

                texto += String.format("Fila da Cabine %d: %d\n", idCabineAtual, tamanho);
            }
        }
        return texto + "\n";
    }

    /**
    * Método que retorna o tempo total da simulação.
    * @return int - contendo o tempo total da simulação.
    */
    private int getTempoTotalSimulado() {
        return tempoTotalSimulado;
    }

    /**
    * Método que retorna o intervalo de chegada.
    * @return int - contendo o valor do intervaloChegada.
    */
    public int getIntervaloChegada() {
        return intervaloChegada;
    }

    /**
     * Método que retorna o total de eventos tratados.
     * @return int - contendo o número de eventos tratados.
     */
    private int getNumeroEventosTratados() {
        return numeroEventosTratados;
    }

    /**
    * Método que retorna o tamanho da maior fila no contexto geral ou
    * de uma Cabine específica.
    * @param idCabine código da cabine a ser considerada, caso seja o
    * o valor 0 irá considerar todas as cabines juntas.
    * @return int - contendo o tamanho da fila de maior tamanho.
    */
    private int getTamanhoMaiorFila(int idCabine) {
        if (idCabine == 0) {
            int tamanho = 0;
            int tamanhoMaxCabineAtual = 0;
            int idCabineAtual = 0;
            for (Map.Entry<Integer, Cabine> cabine : cabines.entrySet()) {
                idCabineAtual = cabine.getKey();
                tamanhoMaxCabineAtual = cabines.get(idCabineAtual).getTamanhoMaxFila();
                if (tamanho < tamanhoMaxCabineAtual) {
                    tamanho = tamanhoMaxCabineAtual;
                }
            }
            return tamanho;
        }
        else {
            try {
                return cabines.get(idCabine).getTamanhoMaxFila();
            }
            catch (Exception e) {
                System.out.println("Cabine inexistente!");
                return -1;
            }
        }
    }

    /**
    * Método que retorna o tamnho médio de todas as filas ou de uma
    * fila de Cabine específica.
    * @param idCabine ID da cabine a ser considerada, caso seja o
    * o valor 0, irá considerar todas as cabines juntas.
    * @return int - contendo a média do tamanho das filas.
    */
    private int getTamanhoMedioFilaGeral(int idCabine) {
        Cabine cabineAtual = null;
        if (idCabine == 0) {
            int numerador = 0;
            int denominador = 0;
            for (Map.Entry<Integer, Cabine> cabine : cabines.entrySet()) {
                cabineAtual = cabine.getValue();
                numerador += cabineAtual.getNumeradorMediaPonderadaTamanhos();
                denominador += cabineAtual.getDenominadorMediaPonderadaTamanhos();
            }
            if (denominador == 0) {
                return 0;
            }
            else {
                return (int)(numerador/denominador);
            }
        }
        else {
            try {
                cabineAtual = cabines.get(idCabine);
                return cabineAtual.getTamanhoMedioFila();
            }
            catch (Exception e) {
                System.out.println("Cabine inexistente!");
                return -1;
            }
        }
    }

    /**
    * Método que retorna o tempo médio geral de espera na fila.
    * Podendo considerar as listas para veículos leves, pesados ou ambos.
    * @param idCabine código da cabine a ser considerada, caso seja o
    * o valor 0 irá considerar todas as cabines juntas.
    * @param abordagem String simbolizando o tipo do veículo.
    * @return int - valor do tempo médio geral de espera.
    */
    private int getTempoMedioEsperaGeral(int idCabine, String abordagem) {
        Cabine cabineAtual = null;
        if (idCabine == 0) {
            int tamanho = 0;
            int tempos = 0;
            for (Map.Entry<Integer, Cabine> cabine : cabines.entrySet()) {
                cabineAtual = cabine.getValue();
                tempos += cabineAtual.getSomasTempoEspera(abordagem);
                tamanho += cabineAtual.getTamanhosListasEspecificas(abordagem);
            }
            if (tamanho == 0) {
                return 0;
            }
            else {
                return (int)(tempos/tamanho);
            }
        }
        else {
            try {
                cabineAtual = cabines.get(idCabine);
                return cabineAtual.getMediaTempoEspera(abordagem);
            }
            catch (Exception e) {
                System.out.println("Cabine inexistente!");
                return -1;
            }
        }
    }

    /**
    * Método que retorna o valor total ganho por uma cabine específica
    * ou pelo pedágio no geral, dependendo do ID que recebe como
    * parâmetro, sendo o ID 0(zero) que referencia todas as cabines.
    * @param idCabine ID da cabine a ser considerada, caso seja o
    * o valor 0, irá considerar todas as cabines juntas.
    * @return double - valor que representa o total ganho por uma cabine
    * específica ou pelo pedágio no geral.
    */
    private double getValorTotalGanhoGeral(int idCabine) {
        Cabine cabineAtual = null;
        if (idCabine == 0) {
            double somatorioTotal = 0d;
            for (Map.Entry<Integer, Cabine> cabine : cabines.entrySet()) {
                cabineAtual = cabine.getValue();
                somatorioTotal += cabineAtual.getValorTotalGanho();
            }
            return somatorioTotal;
        }
        else {
            try {
                cabineAtual = cabines.get(idCabine);
                return cabineAtual.getValorTotalGanho();
            }
            catch (Exception e) {
                System.out.println("Cabine inexistente!");
                return -1;
            }
        }
    }

    /**
    * Método que captura um objeto Cabine através de um ID.
    * @param idCabine inteiro que representa o ID único da Cabine.
    * @return Cabine - o objeto Cabine de acordo com o ID.
    */
    private Cabine getCabine(int idCabine) {
        return cabines.get(idCabine);
    }

    /**
    * Método que captura o atendimento através de um ID.
    * @param idAtendimento inteiro que representa o ID único do Atendimento.
    * @return Atendimento - o objeto Atendimento de acordo com o ID.
    */
    private Atendimento getAtendimento(int idAtendimento) {
        return atendimentos.get(idAtendimento);
    }

    /**
     * Método que retorna um veículo a partir de um identificador informado.
     * @param idVeiculo identificador do veículo.
     * @return Veiculo - objeto correspondente ao identificador do informado.
     */
    private Veiculo getVeiculo(int idVeiculo) {
        return veiculos.get(idVeiculo);
    }

    /**
     * Método que incrementa o número de eventos tratados durante a
     * execução do simulador.
     */
    private void incrementaNumeroEventos() {
        numeroEventosTratados += 1;
    }

    /**
    * Método que salva as estatisticas geradas durante a execução do
    * simulador, em um arquivo "txt" e outro "csv".
    */
    private void salvarEstatisticas() {
        String estatisticas = this.estatisticas;
        for (Cabine cabine: cabines.values()) {
            estatisticas += cabine.getEstatisticas();
        }

        try {
            GerenciadorDeArquivos.salvarArquivo(nomeArquivosSaida + ".csv", estatisticas);
            GerenciadorDeArquivos.salvarArquivo(nomeArquivosSaida + ".txt", gerarEstatisticas());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
    * Método que executa a Simulação.
    * Enquanto a fila de Eventos não for vazia, realizo a retirada dos
    * eventos na fila de Eventos.
    */
    private void executarSimulacao() {
        int tempoUltimoEvento = -1;
        int tempoExecucao = 0;
        Evento eventoAtual;

        do {
            eventoAtual = filaEventos.peek();

            if (eventoAtual.getTempoEvento() == tempoExecucao) {
                eventoAtual = desenfilerarEvento();
                tempoUltimoEvento = eventoAtual.getTempoEvento();

                try {
                    executarEvento(eventoAtual);
                }
                catch (Exception e) {
                    throw new RuntimeException("Erro na execução de um evento!\n" + e.getMessage());
                }
            }
            for (Cabine cabine: cabines.values()) {
                cabine.concatenarEstatisticas(tempoExecucao);
            }
            concatenarEstatisticas(tempoExecucao);

            tempoExecucao ++;
            atualizaMediaFilas();
        }
        while (!filaEventos.isEmpty());

        if (tempoUltimoEvento != -1) {
            setTempoTotalSimulado(tempoUltimoEvento);
        }

        salvarEstatisticas();
    }

    /**
    * Método que executa os Eventos de Chegada e Saída no pedágio.
    * Se o objeto é do tipo dinâmico Chegada, realizo o método executaChegada().
    * Caso contrário, chamo o método executarSaida().
    * @param eventoAtual evento que será executado.
    */
    private void executarEvento(Evento eventoAtual) {
        try {
            if (eventoAtual instanceof Chegada) {
                executarChegada((Chegada)eventoAtual);
            }
            else {
                executarSaida((Saida)eventoAtual);
            }
            incrementaNumeroEventos();
        }
        catch (Exception e) {
            throw new RuntimeException("Erro na execução da chegada ou saída.\n" + e.getMessage());
        }
    }

    /**
    * Método que executa a Chegada.
    * Ao enfileirar o evento, captura o retorno do método enfileirar(),
    * através da variável Cabine.
    * Assim que executo o método, cria um objeto de Saída e é colocada na fila
    * o evento passado por parâmetro.
    * @param eventoAtual evento de chegada que será executado.
    */
    private void executarChegada(Chegada eventoAtual) {
        try {
            int idVeiculo = eventoAtual.getIdVeiculo();
            Veiculo veiculoAtual = getVeiculo(idVeiculo);
            int tempoChegada = eventoAtual.getTempoEvento();

            Cabine cabineAtual = null;
            if (filaRand) {
                cabineAtual = cabineAleatoria(veiculoAtual.getAutomatico());
            }
            else {
                cabineAtual = cabineMenorFila(veiculoAtual.getAutomatico());
            }
            int idCabine = cabineAtual.getIdCabine();
            eventoAtual.setIdCabine(idCabine);

            int tempo = calcularTempoSaida(eventoAtual);

            veiculoAtual.setTempoEspera(tempo - tempoChegada);
            enfileirarNaCabine(idCabine, idVeiculo);
            enfileirarSaida(tempo, idCabine);
        }
        catch (Exception e) {
            throw new RuntimeException("Erro na função 'executarChegada()'.\n");
        }
    }

    /**
    * Método que executa a Saída.
    * Assim que executa o método, retira o elemento da fila.
    * @param eventoAtual evento de saida que será executado.
    * @return int - identificador do veículo que estava no evento de saída.
    */
    private int executarSaida(Saida eventoAtual) {
        int idVeiculoRemovido = -1;
        try {
            Cabine cabineParaRemover = getCabine(eventoAtual.getIdCabine());
            idVeiculoRemovido = cabineParaRemover.desenfileirarVeiculo();
            Veiculo veiculoRemovido = getVeiculo(idVeiculoRemovido);
            cabineParaRemover.computaCobranca(veiculoRemovido.calcularTarifa());

            String tipoVeiculo = (veiculoRemovido instanceof VeiculoLeve) ? "Leve" : "Pesado";
            cabineParaRemover.armazenaTempo(tipoVeiculo, veiculoRemovido.getTempoEspera());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return idVeiculoRemovido;
    }

    /**
    * Método que enfileira Chegadas.
    * Caso seja a Simulação por Fila Aleatória, os eventos são adicionados
    * a partir dessa ideia.
    * Caso a Simulação seja sem ter Fila Aleatória, então adiciono os eventos
    * de acordo com o valor falso do atirbuto filaRand.
    */
    private void enfileirarChegadas() {
        int tempoChegada = 0;
        ArrayList<Integer> listaTemposUsados = new ArrayList<Integer>();
        Random random = new Random();

        for (Map.Entry<Integer, Veiculo> veiculo : veiculos.entrySet()) {
            Veiculo veiculoAtual = veiculo.getValue();

            tempoChegada += random.nextInt(getIntervaloChegada());

            while (listaTemposUsados.contains(tempoChegada)) {
                tempoChegada += 1;
            }
            listaTemposUsados.add(tempoChegada);

            temposEventos.add(tempoChegada);
            Chegada eventoAtual = new Chegada(tempoChegada, veiculoAtual.getIdVeiculo());
            filaEventos.add(eventoAtual);
        }
    }

    /**
     * Método que enfileira o identificador de um veículo em determinada
     * cabine.
     * @param idCabine identificador da cabine.
     * @param idVeiculo identificador do veículo.
     */
    private void enfileirarNaCabine(int idCabine, int idVeiculo) {
        Cabine cabineAtual = getCabine(idCabine);
        cabineAtual.enfileirarVeiculo(idVeiculo);
    }

    /**
     * Método que enfileira um evento de saída na fila de eventos.
     * @param tempoChegada tempo de chegada do veículo na fila.
     * @param idCabine cabine a ser enfileirada na fila de eventos.
     */
    private void enfileirarSaida(int tempoChegada, int idCabine) {
        temposEventos.add(tempoChegada);
        filaEventos.add(new Saida(tempoChegada, idCabine));
    }

    /**
     * Método que retira um Evento da fila.
     * @return Evento - remove o Evento da fila com sucesso.
     */
    private Evento desenfilerarEvento() {
        return filaEventos.remove();
    }

    /**
    * Método que atualiza a Média das filas, de acordo com o novo tamanho.
    */
    private void atualizaMediaFilas() {
        for (Map.Entry<Integer, Cabine> cabine : cabines.entrySet()) {
            Cabine cabineAtual = cabine.getValue();
            cabineAtual.adicionaNovoTamanho(cabineAtual.calcularTamanho());
        }
    }

    /**
    * Método que define o tempo total da simulação.
    * @param tempo tempo final.
    */
    private void setTempoTotalSimulado(int tempo) {
        tempoTotalSimulado = tempo;
    }

    /**
    * Método que informa se uma cabine é automatica.
    * @param cabineAtual objeto cabine a ser verificado.
    * @return boolean - true se o atendimento da cabine for
    * automático, false caso contrário.
    */
    private boolean ehAutomatico(Cabine cabineAtual) {
        return getAtendimento(cabineAtual.getIdAtendimento()) instanceof Automatico;
    }

    /**
    * Método que calcula o tempo final do veículo na fila, a partir da
    * chegada do mesmo, considerando o tipo de Atendimento e o tipo
    * de Veículo.
    * A variável tempoLocomocao atribui um valor adicional aleatório ao tempo,
    * caracterizando um veículo leve e pesado(momento da partida).
    * Veículos leves podem possuir um acréscimo no tempo de 2 a 7.
    * Veículos pesados podem possuir um acréscimo no tempo de 7 a 15.
    * @param eventoAtual evento de chegada.
    * @return int - retorna o tempo final do Veículo baseado na chegada,
    * no tempo de atendimento e na locomoção.
    */
    private int calcularTempoSaida(Chegada eventoAtual) {
        int tempoChegada = eventoAtual.getTempoEvento(); // Tempo da Chegada
        Cabine cabineAtual = getCabine(eventoAtual.getIdCabine());
        Atendimento atendimentoAtual = getAtendimento(cabineAtual.getIdAtendimento());
        int tempoAtendimento = atendimentoAtual.getTempoAtendimento(); // Tempo do atendimento

        Veiculo veiculoAtual = getVeiculo(eventoAtual.getIdVeiculo());
        Random random = new Random();

        int tempoLocomocao = (veiculoAtual instanceof VeiculoLeve) ? 2 + random.nextInt(6) : 7 + random.nextInt(9);

        int tempoTotal = tempoChegada + tempoAtendimento + tempoLocomocao;
        while (temposEventos.contains(tempoTotal)) {
            tempoTotal ++;
        }

        return tempoTotal;
    }

    /**
     * Método que pega uma cabine aleatoriamente.
     * @param automatico booleana que define se um veículo pode utilizar
     * as cabines automáticas.
     * @return Cabine - retorna a cabine aleatória.
     */
    private Cabine cabineAleatoria(boolean automatico) {
        Cabine aleatoria;

        Random random = new Random();
        aleatoria = cabines.get(1 + random.nextInt(cabines.size()));

        if (automatico) {
            while (!ehAutomatico(aleatoria)) {
                aleatoria = cabines.get(1 + random.nextInt(cabines.size()));
            }
        } else {
            while (ehAutomatico(aleatoria)) {
                aleatoria = cabines.get(1 + random.nextInt(cabines.size()));
            }
        }

        return aleatoria;
    }

    /**
     * Método que busca a menor fila entre as cabines disponíveis.
     * @param automatico booleana que define se um veículo pode utilizar
     * as cabines automáticas.
     * @return Cabine - retorna a cabine com a menor fila encontrada.
     */
    private Cabine cabineMenorFila(boolean automatico) {
        Cabine menorFila = null;

        for (Map.Entry<Integer, Cabine> cabine : cabines.entrySet()) {
            Cabine cabineAtual = cabine.getValue();
            if (ehAutomatico(cabineAtual) == automatico) {
                menorFila = cabineAtual;
                break;
            }
        }

        for (Map.Entry<Integer, Cabine> cabine : cabines.entrySet()) {
            Cabine cabineAtual = cabine.getValue();
            if ((cabineAtual.calcularTamanho() < menorFila.calcularTamanho())
                            && (ehAutomatico(cabineAtual) == automatico)) {
                menorFila = cabineAtual;
            }
        }

        return menorFila;
    }
}
