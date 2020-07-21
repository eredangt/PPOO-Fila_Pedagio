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
	private double tempoSimulacao; // Tempo real
	private int tempoEventos;
	private int intervaloChegada;
	private GerenciadorDeArquivos gda;
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
		tempoSimulacao = 0d;
		tempoEventos = 0;
		nomeArquivoEntrada = "Dados.txt";
		nomeArquivoRelatorio = "Relatorio.txt";
		gda = new GerenciadorDeArquivos();
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
	public void Simulacao() {
		filaRand = gda.getFilaRand();
		intervaloChegada = gda.getIntervaloChegada();
		inicializarAtendimentos();
		inicializarTarifas();
		inicializarVeiculos();
		inicializarCabines();
		enfileirarChegadas();
		executarSimulacao();
	}

	/**
	* Método que executa a Simulação.
	* Enquanto minha fila de Eventos não for vazia, realizo a retirada dos
	* eventos na fila de Evento.
	*/
	private void executarSimulacao() {
		while (!filaEventos.isEmpty()) {
			Evento e = desenfilerarEvento();

			executarEvento(e);
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
		for (Map.Entry<Integer, Veiculo> veiculo : veiculos.entrySet()) {
			Veiculo v = veiculo.getValue();
			if (filaRand) {
				Cabine c = cabineAleatoria(v.getAutomatico());
				filaEventos.add(new Chegada(tempoChegada, c.getIdCabine(), v.getIdVeiculo()));
			}
			else {
				Cabine c = cabineMenorFila(v.getAutomatico());
				filaEventos.add(new Chegada(tempoChegada, c.getIdCabine(), v.getIdVeiculo()));
			}
			tempoChegada += getIntervaloChegada();
		}
	}

	/**
	* Método que informa o intervalo de chegada.
	* @return int - reotrna o valor do intervaloChegada.
	*/
	public int getIntervaloChegada() {
		return intervaloChegada;
	}

	/**
	* Método que inicializa as Cabines.
	* Cria uma cabine para cada atendimento.
	*/
	private void inicializarCabines() {
		try {
			for (Map.Entry<Integer, Atendimento> atendimento : atendimentos.entrySet()) {
				Atendimento a = atendimento.getValue();
				Cabine novaCabine = new Cabine(a.getIdAtendimento());
				cabines.put(novaCabine.getIdCabine(), novaCabine);
			}
        }
        catch (Exception e) {
            throw e;
        }

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
	private void executarEvento(Evento e) {
		if (e instanceof Chegada) {
			executarChegada((Chegada)e);
		}
		else {
			executarSaida((Saida)e);
		}
	}

	private void enfileirarNaCabine(int idCabine) {
		Cabine c = getCabine(idCabine);
		getCabine(c.getIdCabine());
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
	private void executarChegada(Chegada e) {
		int tempo = calcularTempoSaida(e);
		int idCabine = e.getIdCabine();
		enfileirarNaCabine(idCabine);
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
	private boolean ehAutomatico(Cabine c) {
		return getAtendimento(c.getIdAtendimento()) instanceof Automatico;
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
	private int calcularTempoSaida(Chegada e) {
		int tempoChegada = e.getTempoEvento(); // Tempo da Chegada
		int tempoAtendimento = (int)(getAtendimento(getCabine(e.getIdCabine()).getIdAtendimento()).getTempoAtendimento()); // Tempo do atendimento

		Random random = new Random();
		Veiculo v = getVeiculo(e.getIdVeiculo());
		int tempoLocomocao = (v instanceof VeiculoLeve) ? 2 + random.nextInt(6) : 7 + random.nextInt(9);

		return tempoChegada + tempoAtendimento + tempoLocomocao;
	}

	/**
	* Método que executa a Saída.
	* Assim que executo o método, retiro o elemento da fila.
	* @param e - evento de saida.
	*/
	private void executarSaida(Saida e) {
		getCabine(e.getIdCabine()).desenfileirarVeiculo();
	}

	private void inicializarTarifas() {
		try {
			String[] tarifa;
			while (true) {
                tarifa = gda.removerTarifa();
				if (Veiculo.getTarifaFixa() == -1d && tarifa[1].equals("fixa")) {
					Veiculo.setTarifaFixa(Double.parseDouble(tarifa[2]));
		        }
		        else if (VeiculoLeve.getTarifaReboque() == -1d && tarifa[1].equals("reboque")) {
					VeiculoLeve.setTarifaReboque(Double.parseDouble(tarifa[2]));
		        }
            }
		}
        catch (Exception e) {
            throw e;
        }
	}

	/**
	* Método que cria os Veiculos.
	* Incializa a tarifa e identifica os tipos de veículo no arquivo, além de
	* inserir na Lista de Veículos, de acordo com o tipo.
	*/
	private void inicializarVeiculos() {
		try {
            String[] veiculo;
            while (true) {
				veiculo = gda.removerVeiculo();
				Veiculo novoVeiculo;
				if (veiculo[1].equals("leve")) {
					novoVeiculo = new VeiculoLeve(Boolean.parseBoolean(veiculo[2]), Boolean.parseBoolean(veiculo[3]));
		        }
		        else { // if (veiculo[1].equals("pesado")) {
					novoVeiculo = new VeiculoPesado(Boolean.parseBoolean(veiculo[2]), Integer.parseInt(veiculo[3]));
		        }
				veiculos.put(novoVeiculo.getIdVeiculo(), novoVeiculo);
            }
        }
        catch (Exception e) {
            throw e;
        }
	}

	/**
	* Método que inicia os Atendimentos.
	* Identifica os tipos de atendimento no arquivo e
	* insere na Lista de Atendimentosde acordo com o tipo.
	*/
	private void inicializarAtendimentos() {
		try {
            String[] atendimento;
            while (true) {
				atendimento = gda.removerVeiculo();
				Atendimento novoAtendimento;
				if (atendimento[1].equals("automatico")) {
					novoAtendimento = new Automatico(Double.parseDouble(atendimento[2]));
		        }
		        else { // if (atendimento[1].equals("funcionario")) {
					novoAtendimento = new Funcionario(Double.parseDouble(atendimento[2]));
		        }
				atendimentos.put(novoAtendimento.getIdAtendimento(), novoAtendimento);
            }
        }
        catch (Exception e) {
            throw e;
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
		aleatoria = cabines.get(1 + random.nextInt(cabines.size() - 1));

		if (automatico) {
			while (!ehAutomatico(aleatoria)) {
				aleatoria = cabines.get(1 + random.nextInt(cabines.size() - 1));
			}
		} else {
			while (ehAutomatico(aleatoria)) {
				aleatoria = cabines.get(1 + random.nextInt(cabines.size() - 1));
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
			Cabine c = cabine.getValue();
			if (ehAutomatico(c) == automatico) {
				menorFila = c;
				break;
			}
		}

		for (Map.Entry<Integer, Cabine> cabine : cabines.entrySet()) {
			Cabine c = cabine.getValue();
			if ((c.calcularTamanho() < menorFila.calcularTamanho())
							&& (ehAutomatico(c) == automatico)) {
				menorFila = c;
			}
		}

		return menorFila;
	}
}
