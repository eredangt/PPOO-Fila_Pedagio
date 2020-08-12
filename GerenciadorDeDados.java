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

import java.util.ArrayList;

/**
 * Classe responsável pelo gerenciamento de dados do pedágio.
 */
public abstract class GerenciadorDeDados {

    /**
     * Construtor da classe GerenciadorDeDados.
     */
    public GerenciadorDeDados() {}

    /**
     * Método responsável criação dos objetos a partir dos Campos
     * lidos do arquivo de dados.
     * @param nomeArquivo nome do arquivo texto que contem os dados
     * dos veículos e atendimentos.
     * @return ArrayList - lista com todos os objetos criados a
     * partir dos campos lidos do arquivo.
     * @throws Exception exceção indicando alguma falha de leitura.
     */
	public static ArrayList<Object> inicializarDados(String nomeArquivo) throws Exception {
		ArrayList<String[]> dados;
		try {
			dados = GerenciadorDeArquivos.lerDados(nomeArquivo);
		}
		catch (Exception e) {
            throw new Exception(e);
        }

        Boolean filaRand = null;
        Integer intervaloChegada = -1;
        ArrayList<Object> objetos = new ArrayList<Object>();

		for (String[] campos: dados) {
			try {
				switch (GerenciadorDeDados.validarCampos(campos)) {
                    case 'F':
                        if (filaRand == null) {
                            filaRand = criarFilaRand(campos);
                            objetos.add(filaRand);
                        }

						break;

					case 'I':
                        if (intervaloChegada == -1) {
                            intervaloChegada = criarIntervaloChegada(campos);
                            objetos.add(intervaloChegada);
                        }

						break;
					case 'T':
						criarTarifa(campos);

						break;

					case 'V':
                        objetos.add(criarVeiculo(campos));

						break;

					case 'A':
                        Atendimento atendimento = criarAtendimento(campos);
                        objetos.add(atendimento);
                        objetos.add(criarCabine(atendimento.getIdAtendimento()));

						break;

					default:
						break;
				}
			}
			catch (Exception e) {}
        }

        return objetos;
	}

    /**
     * Método responsável pela validação dos campos de uma linha do
     * arquivo de dados.
     * @param campos um vetor de campos de texto que compõem um
     * atendimento.
     * @return char - um caracter que representa um fila ('F'), intervalo ('I'),
     * veículo ('V'), tarifa ('T'), atendimento ('A') ou espaco em branco (' ').
     * @throws Exception alguma exceção indicando que algum dos
     * campos é invalido.
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
     * @throws Exception exceção indicando alguma falha de escrita.
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
                "    > O terceiro campo pode ser um valor int\n";

        try {
			GerenciadorDeArquivos.salvarArquivo("FormatacaoDosDados.txt", texto);
        }
		catch (Exception e) {
            throw new Exception(e);
        }
	}

    /**
    * Método que retorna o modo da fila rand validado.
    * @param campos campos de uma linha já validados.
    * @return Boolean - modo da fila rand criado a partir dos compos
    * lidos do arquivo.
    */
	private static Boolean criarFilaRand(String[] campos) {
		return (Boolean) campos[1].equals("aleatoria");
	}

    /**
    * Método que retorna o intervalo entre as chegadas do veículos validado.
    * @param campos campos de uma linha já validados.
    * @return Integer - intervalo criado a partir dos compos
    * lidos do arquivo.
    */
	private static Integer criarIntervaloChegada(String[] campos) {
		return Integer.parseInt(campos[1]);
	}

    /**
    * Método que define o tipo de tarifa validado.
    * @param campos campos de uma linha já validados.
    */
	private static void criarTarifa(String[] campos) {
		if (Veiculo.getTarifaFixa() == -1d && campos[1].equals("fixa")) {
			Veiculo.setTarifaFixa(Double.parseDouble(campos[2]));
		}
		else if (VeiculoLeve.getTarifaReboque() == -1d && campos[1].equals("reboque")) {
			VeiculoLeve.setTarifaReboque(Double.parseDouble(campos[2]));
		}
	}

    /**
    * Método que retorna o tipo de veículo validado.
    * @param campos campos de uma linha já validados.
    * @return Veiculo - novo veiculo criado a partir dos compos
    * lidos do arquivo.
    */
	private static Veiculo criarVeiculo(String[] campos) {
		Veiculo novoVeiculo;
		if (campos[1].equals("leve")) {
			novoVeiculo = new VeiculoLeve(Boolean.parseBoolean(campos[2]), Boolean.parseBoolean(campos[3]));
		}
		else { // if (veiculo[1].equals("pesado")) {
			novoVeiculo = new VeiculoPesado(Boolean.parseBoolean(campos[2]), Integer.parseInt(campos[3]));
        }

        return novoVeiculo;
	}

    /**
    * Método que retorna uma nova cabine.
    * @param idAtendimento código do idAtendimento.
    * @return Cabine - nova cabine criado a partir do id de um
    * atendimento.
    */
	private static Cabine criarCabine(int idAtendimento) {
        Cabine novaCabine = new Cabine(idAtendimento);

        return novaCabine;
	}

    /**
    * Método que retorna o tipo de atendimento validado.
    * @param campos campos de uma linha já validados.
    * @return Atendimento - novo atendimento criado a partir dos compos
    * lidos do arquivo.
    */
	private static Atendimento criarAtendimento(String[] campos) {
		Atendimento novoAtendimento;
		if (campos[1].equals("automatico")) {
			novoAtendimento = new Automatico(Integer.parseInt(campos[2]));
		}
		else { // if (atendimento[1].equals("funcionario")) {
			novoAtendimento = new Funcionario(Integer.parseInt(campos[2]));
        }

        return novoAtendimento;
	}

    /**
     * Método responsável pela validação do tipo de fila.
     * @param campos um vetor de campos de texto que compõem um
     * tipo de fila.
     * @throws Exception alguma exceção indicando que algum dos
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
     * Método responsável pela validação do intervalo de chegada.
     * @param campos um vetor de campos de texto que compõem o
     * intervalo de chegada.
     * @throws Exception alguma exceção indicando que algum dos
     * campos e inválido.
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
     * @param campos um vetor de campos de texto que compõem uma
     * tarifa.
     * @throws Exception alguma exceção indicando que algum dos
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
     * @param campos um vetor de campos de texto que compõem um
     * veiculo.
     * @throws Exception alguma exceção indicando que algum dos
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
     * @param campos um vetor de campos de texto que compõem um
     * atendimento.
     * @throws Exception alguma exceção indicando que algum dos
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
            Integer.parseInt(campos[2]);
        }
        catch (NumberFormatException e) {
            log += String.format(" > O terceiro campo deveria ser um valor int, nao: \"%s\"\n", campos[2]);
        }

        if (!log.equals("")) {
            throw new Exception(log);
        }
    }
}
