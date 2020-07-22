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

import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

/**
* Classe que realiza a Simulação da Fila do Pedágio.
* A classe possui atributos que correspondem a fila aleatória ou não
* (filaaRand), inicializa os arquivos de Entrada e de Relatorio,
* [completar aqui os tempos]
* Cria as Listas de Cabine, Veículos e Atendimentos, e também, a FilaEventos
* de Eventos.
*/
public class Simulador {
	private boolean filaRand;
	private String nomeArquivoEntrada;
	private String nomeArquivoRelatorio;
    private long tempoInicial; // Tempo de execução
    private int tempoTotalSimulado;
	private int tempoEventos;
	private int intervaloChegada;
	private int numeroEventosTratados;
	private int tamanhoMaiorFila;
	private ArrayList<Integer> tamanhosFilas;
	private GerenciadorDeDados gdd;
	private PriorityQueue<Evento> filaEventos;
	private HashMap<Integer, Cabine> cabines;
	private HashMap<Integer, Veiculo> veiculos;
	private HashMap<Integer, Atendimento> atendimentos;

    /**
    * Construtor cria as listas de: Lista Cabines, Lista Veículos,
    * Lista Atendimentos e, cria a Fila de Eventos.
	* Incializo os atributos da minha classe com o valor 0.
    * Além de nomear o Arquivo de Entrada e o Arquivo de Relatório.
    * Também, cria um objeto que gerencia o arquivo.
    */
	public Simulador() {
		tempoInicial = 0;
		tempoEventos = 0;
		numeroEventosTratados = 0;
		tamanhoMaiorFila = 0;
		tempoTotalSimulado = 0;
		nomeArquivoEntrada = "Dados.txt";
		nomeArquivoRelatorio = "Relatorio.txt";
		tamanhosFilas = new ArrayList<Integer>();
		gdd = new GerenciadorDeDados();
		filaEventos = new PriorityQueue<Evento>();
		cabines = new HashMap<Integer, Cabine>();
		veiculos = new HashMap<Integer, Veiculo>();
		atendimentos = new HashMap<Integer, Atendimento>();
	}

	/**
	* Método que inicia a Simulação.
	* Inicializa o Atendimento, os Veículos e as Cabines.
	* O método enfileirarChegadas() verifica se a fila será aleatória ou não.
	* O método executarSimulacao() retira os eventos da fila.
	*/
	public void iniciarSimulacao() throws Exception {
        try {
            gdd.inicializarDados(nomeArquivoEntrada);
        }
        catch (Exception e) {
			throw e;
		}

		try {
			filaRand = gdd.getFilaRand();
			intervaloChegada = gdd.getIntervaloChegada();
		}
		catch (Exception e) {
			throw e;
		}

		try {
			while (true) {
				Cabine novaCabine = gdd.removerCabine();
				cabines.put(novaCabine.getIdCabine(), novaCabine);
			}
		}
		catch (Exception e) {}

		try {
			while (true) {
				Veiculo novoVeiculo = gdd.removerVeiculo();
				veiculos.put(novoVeiculo.getIdVeiculo(), novoVeiculo);
			}
		}
		catch (Exception e) {}

		try {
			while (true) {
				Atendimento novoAtendimento = gdd.removerAtendimento();
				atendimentos.put(novoAtendimento.getIdAtendimento(), novoAtendimento);
			}
		}
		catch (Exception e) {}

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
	* Método que executa a Simulação.
	* Enquanto minha fila de Eventos não for vazia, realizo a retirada dos
	* eventos na fila de Evento.
	*/
	private void executarSimulacao() {
		int tempoUltimoEvento = -1;
		
		while (!filaEventos.isEmpty()) {
			Evento eventoAtual = desenfilerarEvento();
			tempoUltimoEvento = eventoAtual.getTempoEvento();

			try {
				executarEvento(eventoAtual);
			}
			catch (Exception e) {
				throw e;
			}
		}

		if (tempoUltimoEvento != -1) {
			setTempoTotalSimulado(tempoUltimoEvento);
		}
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
		
		for (Map.Entry<Integer, Veiculo> veiculo : veiculos.entrySet()) {
			Veiculo veiculoAtual = veiculo.getValue();

			Random random = new Random();
			tempoChegada += random.nextInt(getIntervaloChegada());

			while (listaTemposUsados.contains(tempoChegada)) {
				tempoChegada += 1;
			}
			listaTemposUsados.add(tempoChegada);

			Cabine cabineAtual = null;
			
			if (filaRand) {
				cabineAtual = cabineAleatoria(veiculoAtual.getAutomatico());
			}
			else {
				cabineAtual = cabineMenorFila(veiculoAtual.getAutomatico());
			}
			
			Chegada eventoAtual = new Chegada(tempoChegada, cabineAtual.getIdCabine(), veiculoAtual.getIdVeiculo());
			filaEventos.add(eventoAtual);
			int tempo = calcularTempoSaida(eventoAtual);
			int idCabine = eventoAtual.getIdCabine();
			int idVeiculo = eventoAtual.getIdVeiculo();
			enfileirarNaCabine(idCabine, idVeiculo);
		}
	}

	private void setTempoTotalSimulado(int tempo) {
		tempoTotalSimulado = tempo;
	}

	private int getTempoTotalSimulado() {
		return tempoTotalSimulado;
	}

	private void incrementaNumeroEventos() {
		numeroEventosTratados += 1;
	}

	private int getNumeroEventosTratados() {
		return numeroEventosTratados;
	}

	private void calcularTamanhoMaiorFila() {
		for (Map.Entry<Integer, Cabine> cabine : cabines.entrySet()) {
			Cabine cabineAtual = cabine.getValue();

			adicionaTamanhosFila(tamanhosFilas.size());
			if (cabineAtual.calcularTamanho() > tamanhoMaiorFila) {
				tamanhoMaiorFila = cabineAtual.calcularTamanho();
			}
		}
	}

	private int getTamanhoMaiorFila() {
		return tamanhoMaiorFila;
	}

	private void adicionaTamanhosFila(int tamanhoAntigo) {
		tamanhosFilas.add(tamanhoAntigo);
	}

	private int calculaTamanhoMedioFila() {
		int divisor = tamanhosFilas.size();

		int somador = 0;
		for (int tamanho : tamanhosFilas) {
			somador += tamanho;
		}

		return (int)((float)somador/divisor);
	}

	/**
	* Método que informa o intervalo de chegada.
	* @return int - reotrna o valor do intervaloChegada.
	*/
	public int getIntervaloChegada() {
		return intervaloChegada;
	}

	/**
	* Método que retira um Evento da fila.
	* @return Evento - remove o Evento da fila com sucesso.
	*/
	private Evento desenfilerarEvento() {
		return filaEventos.remove();
	}

	/**
	* Método que executa os Eventos de Chegada e Saída no pedágio.
	* Se o objeto é do tipo dinâmico Chegada, realizo o método executaChegada().
	* Caso contrário, chamo o método executaSaida(e).
	*/
	private void executarEvento(Evento eventoAtual) {
		if (eventoAtual instanceof Chegada) {
			Chegada c = (Chegada)eventoAtual;
			System.out.println("Chegada, " + eventoAtual.getTempoEvento() + ", Veiculo: " + c.getIdVeiculo() + ", Cabine: " + eventoAtual.getIdCabine());
			executarChegada((Chegada)eventoAtual);
			
		}
		else {
			System.out.println("Saida, " + eventoAtual.getTempoEvento() + ", Cabine: " + eventoAtual.getIdCabine());
			executarSaida((Saida)eventoAtual);
		}
		incrementaNumeroEventos();
		calcularTamanhoMaiorFila();
	}

	private void enfileirarNaCabine(int idCabine, int idVeiculo) {
		Cabine cabineAtual = getCabine(idCabine);
		cabineAtual.enfileirarVeiculo(idVeiculo);
	}

	private void enfileirarSaida(int tempoChegada, int idCabine) {
		filaEventos.add(new Saida(tempoChegada, idCabine));
	}

	/**
	* Método que executa a Chegada.
	* Caleta o tempo gasto no Evento e o armazeno em uma variável.
	* Ao enfileirar o evento, capturo a o retorno do método enfileirar(),
	* através da variável Cabine.
	* Assim que executo o método, crio um objeto de Saída e coloco na fila
	* o evento passado por parâmetro.
	*/
	private void executarChegada(Chegada eventoAtual) {
		int tempo = calcularTempoSaida(eventoAtual);
		int idCabine = eventoAtual.getIdCabine();
		int idVeiculo = eventoAtual.getIdVeiculo();
		enfileirarSaida(tempo, idCabine);
	}

	private Cabine getCabine(int idCabine) {
		return cabines.get(idCabine);
	}

	private Atendimento getAtendimento(int idAtendimento) {
		return atendimentos.get(idAtendimento);
	}

	private Veiculo getVeiculo(int idVeiculo) {
		return veiculos.get(idVeiculo);
	}

	/**
	* Método que informa se uma cabine é automatica.
	* @param c - cabine.
	* @return boolean - true se o atendimento da cabine for
	* automatico.
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
	* @return int - retorna o tempo final do Veículo baseado na chegada,
	* no tempo de atendimento e na locomoção.
	*/
	private int calcularTempoSaida(Chegada eventoAtual) {
		int tempoChegada = eventoAtual.getTempoEvento(); // Tempo da Chegada
		Cabine cabineAtual = getCabine(eventoAtual.getIdCabine());
		Atendimento atendimentoAtual = getAtendimento(cabineAtual.getIdAtendimento());
		int tempoAtendimento = (int)(atendimentoAtual.getTempoAtendimento()); // Tempo do atendimento

		Random random = new Random();
		Veiculo veiculoAtual = getVeiculo(eventoAtual.getIdVeiculo());
		int tempoLocomocao = (veiculoAtual instanceof VeiculoLeve) ? 2 + random.nextInt(6) : 7 + random.nextInt(9);

		return tempoChegada + tempoAtendimento + tempoLocomocao;
	}

	/**
	* Método que executa a Saída.
	* Assim que executo o método, retiro o elemento da fila.
	* @param e - evento de saida.
	*/
	private void executarSaida(Saida eventoAtual) {
		try {
			getCabine(eventoAtual.getIdCabine()).desenfileirarVeiculo();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

    /**
    * Método que gera as Estatisticas da fila de Pegágio.
    * @return String - retorna a saída final da fila.
    */
    public String gerarEstatisticas() {
		return "";
	}

    /**
    * Método que calcula o tempo gasto na execução.
    * @return long - retorna o tempo gasto na execução.
    */
	public long calcularTempoExecucao() {
		return 0L;
	}

    /**
    * Método que pega uma cabine aleatoriamente.
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
    * @return Cabine - retorna a menor fila encontrada.
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
