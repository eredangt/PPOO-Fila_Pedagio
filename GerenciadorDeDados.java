import java.util.ArrayList;

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
 * Classe responsável pelo gerenciamento de dados do pedágio.
 */
public class GerenciadorDeDados {
    private GerenciadorDeArquivos gda;
	private Boolean filaRand;
	private int intervaloChegada;
	private ArrayList<Cabine> cabines;
	private ArrayList<Veiculo> veiculos;
	private ArrayList<Atendimento> atendimentos;
    
    /**
     * Construtor da classe GerenciadorDeDados.
     */
    public GerenciadorDeDados() {
		gda = new GerenciadorDeArquivos();
		filaRand = null;
		intervaloChegada = -1;
		cabines = new ArrayList<Cabine>();
		veiculos = new ArrayList<Veiculo>();
		atendimentos = new ArrayList<Atendimento>();
	}
    
    /**
     * Método responsável pela leitura do arquivo de dados.
     * @param nomeArquivo - nome do arquivo texto que contem os dados
     * dos veiculos e atendimentos.
     * @throws Exception - exceção indicando alguma falha de
     * leitura.
     */
	public void inicializarDados(String nomeArquivo) throws Exception {
		ArrayList<String[]> dados;
		try {
			dados = gda.lerDados(nomeArquivo);
		}
		catch (Exception e) {
            throw new Exception(e);
		}

		for (String[] campos: dados) {
			try {
				switch (GerenciadorDeDados.validarCampos(campos)) {
					case 'F':
						criarFilaRand(campos);
						
						break;
					
					case 'I':
						criarIntervaloChegada(campos);
						
						break;
					case 'T':
						criarTarifa(campos);
						
						break;
					
					case 'V':
						criarVeiculo(campos);
						
						break;
					
					case 'A':
						criarAtendimento(campos);
						
						break;
					
					default:
						break;
				}
			}
			catch (Exception e) {}
		}
	}

    /**
     * Método utilizado para obter o tipo da fila das cabines.
     * @return boolean - menor fila (false), fila aleatoria (true).
     * @throws Exception - o atributo filaRand não foi inicializado.
     */
	public boolean getFilaRand() throws Exception {
		if (filaRand == null) {
			throw new Exception("O tipo de fila não foi definido\n");
		}

		return filaRand;
	}

    /**
     * Método utilizado para obter o intervalo de tempo entre
	 * a chegada dos carros.
     * @return int - intervalo de tempo entre a chegada dos carros.
     * @throws Exception - o atributo intervaloChegada não foi inicializado.
     */
	public int getIntervaloChegada() throws Exception {
		if (intervaloChegada == -1) {
			throw new Exception("O intervalo de chegada não foi definido\n");
		}

		return intervaloChegada;
	}

    /**
     * Método utilizado para obter as cabines.
     * @return Cabine.
     * @throws Exception - o atributo cabines está vazio.
     */
	public Cabine removerCabine() throws Exception {
		if (cabines.size() == 0) {
			throw new Exception("Nenhuma cabine encontrada\n");
		}

		return cabines.remove(0);
	}

    /**
     * Método utilizado para obter os veiculos.
     * @return Veiculo.
     * @throws Exception - o atributo veiculos está vazio.
     */
	public Veiculo removerVeiculo() throws Exception {
		if (veiculos.size() == 0) {
			throw new Exception("Nenhum veiculo encontrado\n");
		}

		return veiculos.remove(0);
	}

    /**
     * Método utilizado para obter os atendimetos.
     * @return Atendimento.
     * @throws Exception - o atributo atendimentos está vazio.
     */
	public Atendimento removerAtendimento() throws Exception {
		if (atendimentos.size() == 0) {
			throw new Exception("Nenhum atendimento encontrado\n");
		}

		return atendimentos.remove(0);
	}
    
    /**
     * Método responsável pela validação dos campos de uma linha do
     * arquivo de dados.
     * @param campos - um vetor de campos de texto que compõem um
     * atendimento.
     * @return char - um caracter que representa um veiculo ('V'),
     * atendimento ('A') ou espaco em branco (' ').
     * @throws Exception - alguma exceção indicando que algum dos
     * campos e invalido.
     */
    public static char validarCampos(String[] campos) throws Exception {
        try {
            if (campos[0].equals("")) {
                return ' ';
            }
            else if (campos[0].equals("fila")) {
                validarTipoFila(campos);
                
                return 'F';
            }
            else if (campos[0].equals("intervalo")) {
                validarIntervaloChegada(campos);
                
                return 'I';
            }
            else if (campos[0].equals("tarifa")) {
                validarTarifa(campos);
                
                return 'T';
            }
            else if (campos[0].equals("veiculo")) {
                validarVeiculo(campos);
                
                return 'V';
            }
            else if (campos[0].equals("atendimento")) {
                validarAtendimento(campos);
                
                return 'A';
            }
            else {
                throw new Exception(String.format(" > O primeiro campo de indentificacao deveria ser \"fila\", \"intervalo\", \"tarifa\", \"veiculo\" ou \"atendimento\", nao: \"%s\"\n", campos[0]));
            }
        }
        catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Método que gera um arquivo de texto contendo uma descrição de
     * como deve ser a formatação dos campos no arquivo de dados.
     * @throws Exception - exceção indicando alguma falha de
     * escrita.
     */
    public static void gerarArquivoDeFormatacao() throws Exception{
        String texto;
        
        texto = "Todos os campos devem ser separados por uma \",\".\n\n" +
                "O primeiro campo pode ser \"fila\", \"intervalo\", \"tarifa\", \"veiculo\" ou \"atendimento\":\n" +
                " > Campos da \"fila\":\n" +
                "    > O segundo campo pode ser \"menor\" ou \"aleatoria\"\n" +
                " > Campos da \"intervalo\":\n" +
                "    > O segundo campo pode ser um valor inteiro maior que 0\n" +
                " > Campos da \"tarifa\":\n" +
                "    > O segundo campo pode ser \"fixa\" ou \"reboque\"\n" +
                "    > O terceiro campo pode ser valor double\n" +
                " > Campos do \"veiculo\":\n" +
                "    > O segundo campo pode ser \"leve\" ou \"pesado\"\n" +
                "    > O terceiro campo pode ser \"true\" ou \"false\"\n" +
                "       > Campos do \"veiculo\" \"leve\":\n" +
                "          > O quarto campo pode ser \"true\" ou \"false\"\n" +
                "       > Campos do \"veiculo\" \"pesado\":\n" +
                "          > O quarto campo pode ser um valor inteiro\n" +
                " > Campos do \"atendimento\":\n" +
                "    > O segundo campo pode ser \"automatico\" ou \"funcionario\"\n" +
                "    > O terceiro campo pode ser valor double\n";
        
        try {
			GerenciadorDeArquivos.salvarArquivo("FormatacaoDosDados.txt", texto);
        }
		catch (Exception e) {
            throw new Exception(e);
        }
	}
	
	private void criarFilaRand(String[] campos) {
		if (filaRand == null) {
			filaRand = campos[1].equals("aleatoria");
		}
	}
	
	private void criarIntervaloChegada(String[] campos) {
		if (intervaloChegada == -1) {
			intervaloChegada = Integer.parseInt(campos[1]);
		}
	}
	
	private void criarTarifa(String[] campos) {
		if (Veiculo.getTarifaFixa() == -1d && campos[1].equals("fixa")) {
			Veiculo.setTarifaFixa(Double.parseDouble(campos[2]));
		}
		else if (VeiculoLeve.getTarifaReboque() == -1d && campos[1].equals("reboque")) {
			VeiculoLeve.setTarifaReboque(Double.parseDouble(campos[2]));
		}
	}
	
	private void criarVeiculo(String[] campos) {
		Veiculo novoVeiculo;
		if (campos[1].equals("leve")) {
			novoVeiculo = new VeiculoLeve(Boolean.parseBoolean(campos[2]), Boolean.parseBoolean(campos[3]));
		}
		else { // if (veiculo[1].equals("pesado")) {
			novoVeiculo = new VeiculoPesado(Boolean.parseBoolean(campos[2]), Integer.parseInt(campos[3]));
		}
		veiculos.add(novoVeiculo);
	}
	
	private void criarCabine(int idAtendimento) {
		Cabine novaCabine = new Cabine(idAtendimento);
		cabines.add(novaCabine);
	}
	
	private void criarAtendimento(String[] campos) {
		Atendimento novoAtendimento;
		if (campos[1].equals("automatico")) {
			novoAtendimento = new Automatico(Double.parseDouble(campos[2]));
		}
		else { // if (atendimento[1].equals("funcionario")) {
			novoAtendimento = new Funcionario(Double.parseDouble(campos[2]));
		}
		atendimentos.add(novoAtendimento);
		criarCabine(novoAtendimento.getIdAtendimento());
	}
    
    /**
     * Método responsável pela validação de um atendimento.
     * @param campos - um vetor de campos de texto que compõem um
     * atendimento.
     * @throws Exception - alguma exceção indicando que algum dos
     * campos e invalido.
     */
    private static void validarTipoFila(String[] campos) throws Exception {
        String log = "";
        
        if (campos.length != 2) {
            throw new Exception(String.format(" > O tipo de fila deveria ter 2 campos, nao: %s\n", campos.length));
        }
        if (!(campos[1].equals("menor") || campos[1].equals("aleatoria"))) {
            throw new Exception(String.format(" > O segundo campo deveria ser \"menor\" ou \"aleatoria\", nao: \"%s\"\n", campos[1]));
        }
        
        if (!log.equals("")) {
            throw new Exception(log);
        }
    }
    
    /**
     * Método responsável pela validação de uma tarifa.
     * @param campos - um vetor de campos de texto que compõem uma
     * tarifa.
     * @throws Exception - alguma exceção indicando que algum dos
     * campos e invalido.
     */
    private static void validarIntervaloChegada(String[] campos) throws Exception {
        String log = "";
        
        if (campos.length != 2) {
            throw new Exception(String.format(" > O intervalo de chegada deveria ter 2 campos, nao: %s\n", campos.length));
        }
        
        try {
            if (Integer.parseInt(campos[1]) < 1) {
                log += String.format(" > O segundo campo deveria ser um valor inteiro maior que 0, nao: \"%s\"\n", campos[1]);
            }
        }
        catch (NumberFormatException e) {
            log += String.format(" > O segundo campo deveria ser um valor inteiro, nao: \"%s\"\n", campos[1]);
        }
        
        if (!log.equals("")) {
            throw new Exception(log);
        }
    }
    
    /**
     * Método responsável pela validação de uma tarifa.
     * @param campos - um vetor de campos de texto que compõem uma
     * tarifa.
     * @throws Exception - alguma exceção indicando que algum dos
     * campos e invalido.
     */
    private static void validarTarifa(String[] campos) throws Exception {
        String log = "";
        
        if (campos.length != 3) {
            throw new Exception(String.format(" > A tarifa deveria ter 3 campos, nao: %s\n", campos.length));
        }
        if (!(campos[1].equals("fixa") || campos[1].equals("reboque"))) {
            throw new Exception(String.format(" > O segundo campo de indentificacao deveria ser \"fixa\" ou \"reboque\", nao: \"%s\"\n", campos[1]));
        }
        
        try {
            Double.parseDouble(campos[2]);
        }
        catch (NumberFormatException e) {
            log += String.format(" > O terceiro campo deveria ser um valor double, nao: \"%s\"\n", campos[2]);
        }
        
        if (!log.equals("")) {
            throw new Exception(log);
        }
    }
    
    /**
     * Método responsável pela validação de um veiculo.
     * @param campos - um vetor de campos de texto que compõem um
     * veiculo.
     * @throws Exception - alguma exceção indicando que algum dos
     * campos e invalido.
     */
    private static void validarVeiculo(String[] campos) throws Exception {
        String log = "";
        
        if (campos.length != 4) {
            throw new Exception(String.format(" > O veiculo deveria ter 4 campos, nao: %s\n", campos.length));
        }
        if (!(campos[1].equals("leve") || campos[1].equals("pesado"))) {
            throw new Exception(String.format(" > O segundo campo de indentificacao deveria ser \"leve\" ou \"pesado\", nao: \"%s\"\n", campos[1]));
        }
        if (!(campos[2].equals("true") || campos[2].equals("false"))) {
            log += String.format(" > O terceiro campo deveria ser \"true\" ou \"false\", nao: \"%s\"\n", campos[2]);
        }
        
        if (campos[1].equals("leve")) {
            if (!(campos[3].equals("true") || campos[3].equals("false"))) {
                log += String.format(" > O quarto campo deveria ser \"true\" ou \"false\", nao: \"%s\"\n", campos[3]);
            }
        }
        else if (campos[1].equals("pesado")) {
            try {
                Integer.parseInt(campos[3]);
            }
            catch (NumberFormatException e) {
                log += String.format(" > O quarto campo deveria ser um valor inteiro, nao: \"%s\"\n", campos[3]);
            }
        }
        
        if (!log.equals("")) {
            throw new Exception(log);
        }
    }
    
    /**
     * Método responsável pela validação de um atendimento.
     * @param campos - um vetor de campos de texto que compõem um
     * atendimento.
     * @throws Exception - alguma exceção indicando que algum dos
     * campos e invalido.
     */
    private static void validarAtendimento(String[] campos) throws Exception {
        String log = "";
        
        if (campos.length != 3) {
            throw new Exception(String.format(" > O atendimento deveria ter 3 campos, nao: %s\n", campos.length));
        }
        if (!(campos[1].equals("automatico") || campos[1].equals("funcionario"))) {
            throw new Exception(String.format(" > O segundo campo de indentificacao deveria ser \"automatico\" ou \"funcionario\", nao: \"%s\"\n", campos[1]));
        }
        
        try {
            Double.parseDouble(campos[2]);
        }
        catch (NumberFormatException e) {
            log += String.format(" > O terceiro campo deveria ser um valor double, nao: \"%s\"\n", campos[2]);
        }
        
        if (!log.equals("")) {
            throw new Exception(log);
        }
    }
}
