import java.util.ArrayList;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

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
 * Classe responsável pelo gerenciamento de arquivos do pedágio, fazendo
 * tanto a leitura quanto a escrita dos arquivos texto.
 */
public class GerenciadorDeArquivos {
    private ArrayList<String[]> veiculos;
    private ArrayList<String[]> atendimentos;
    private ArrayList<String> logs;
    
    /**
     * Construtor da classe GerenciadorDeArquivos.
     */
    public GerenciadorDeArquivos() {
        veiculos = new ArrayList<String[]>();
        atendimentos = new ArrayList<String[]>();
        logs = new ArrayList<String>();
    }
    
    /**
     * Método utilizado para obter um veiculo que foi lido do arquivo de
     * dados.
     * @return String[] - um vetor de campos de texto que compõem um
     * veiculo.
     * @throws RuntimeException - exceção indicando falha ao remover um
     * veiculo.
     */
    public String[] removerVeiculo() throws RuntimeException {
        try {
            return veiculos.remove(0);
        }
        catch (Exception e) {
            throw new RuntimeException(String.format("Nao foi possivel remover um veiculo\n > %s", e.getMessage()));
        }
    }
    
    /**
     * Método utilizado para obter um atendimento que foi lido do
     * arquivo de dados.
     * @return String[] - um vetor de campos de texto que compõem um
     * atendimento.
     * @throws RuntimeException - exceção indicando falha ao remover um
     * atendimento.
     */
    public String[] removerAtendimento() throws RuntimeException {
        try {
            return atendimentos.remove(0);
        }
        catch (Exception e) {
            throw new RuntimeException(String.format("Nao foi possivel remover um atendimento\n > %s", e.getMessage()));
        }
    }
    
    /**
     * Método responsável pela leitura do arquivo de dados.
     * @param nomeArquivo - nome do arquivo texto que contem os dados
     * dos veiculos e atendimentos.
     * @throws RuntimeException - exceção indicando alguma falha de
     * leitura.
     */
    public void lerDados(String nomeArquivo) throws RuntimeException {
        try (BufferedReader arqBF = new BufferedReader(new FileReader(nomeArquivo))) {
            veiculos.clear();
            atendimentos.clear();
            
            int num_linha = 0;
            String texto_linha = arqBF.readLine();
            while (texto_linha != null) {
                String[] campos = texto_linha.split(",");
                num_linha ++;
                
                try {
                    switch (validarCampos(campos)) {
                        case 'V':
                            veiculos.add(campos);
                            
                            break;
                        case 'A':
                            atendimentos.add(campos);
                            
                            break;
                        
                        default:
                            break;
                    }
                }
                catch (Exception e) {
                    logs.add(String.format("Erro na linha: %d\n%s", num_linha, e.getMessage()));
                }
                
                texto_linha = arqBF.readLine();
            }
        }
        catch (IOException e) {
            String log = String.format("Falhar ao ler os dados do arquivo: %s\n > %s", nomeArquivo, e.getMessage());
            logs.add(log);
            
            throw new RuntimeException(e);
        }
        finally {
            String logCompleto = "";
            for (String log: logs) {
                logCompleto += log + "\n";
            }
            
            salvarArquivo("log.txt", logCompleto);
        }
    }
    
    /**
     * Método responsável por salvar um texto em um arquivo de tipo
     * texto.
     * @param nomeArquivo - nome do arquivo texto que contem os dados
     * dos veiculos e atendimentos.
     * @param texto - o texto com as informações que serão salvas.
     * @throws RuntimeException - exceção indicando alguma falha de
     * escrita.
     */
    public static void salvarArquivo(String nomeArquivo, String texto) throws RuntimeException {
        try (FileWriter arqFW = new FileWriter(nomeArquivo)) {
            arqFW.write(texto);
        }
        catch (IOException e) {
            throw new RuntimeException(String.format("Falhar ao salvar os dados no arquivo: %s\n > %s", nomeArquivo, e.getMessage()));
        }
    }
    
    /**
     * Método que gera um arquivo de texto contendo uma descrição de
     * como deve ser a formatação dos campos no arquivo de dados.
     */
    public static void gerarArquivoDeFormatacao() {
        String texto;
        
        texto = "Todos os campos devem ser separados por uma \",\".\n\n" +
                "O primeiro campo pode ser \"veiculo\" ou \"atendimento\":\n" +
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
        
        salvarArquivo("FormatacaoDosDados.txt", texto);
    }
    
    /**
     * Método utilizado apenas para fins de debug.
     * @return String - uma cadeia de caracteres formatada com os
     * atributos da classe.
     */
    @Override
    public String toString() {
        String texto = "Gerenciador de arquivos (GDA)\n";
        
        texto += "\nVeiculos:\n";
        if (veiculos.size() == 0) {
            texto += " > Sem veiculos";
        }
        else {
            for (String[] campos: veiculos) {
                texto += " > ";
                for (String campo: campos) {
                    texto += campo + " ";
                }
                texto += "\n";
            }
        }
        
        texto += "\nAtendimentos:\n";
        if (atendimentos.size() == 0) {
            texto += " > Sem atendimentos";
        }
        else {
            for (String[] campos: atendimentos) {
                texto += " > ";
                for (String campo: campos) {
                    texto += campo + " ";
                }
                texto += "\n";
            }
        }
        
        texto += "\nLog completo:\n";
        if (logs.size() == 0) {
            texto += " > Sem logs";
        }
        else {
            for (String log: logs) {
                texto += " " + log + "\n";
            }
        }
        
        return texto;
    }
    
    /**
     * Método responsável pela validação de um veiculo.
     * @param campos - um vetor de campos de texto que compõem um
     * veiculo.
     * @throws RuntimeException - alguma exceção indicando que algum dos
     * campos e invalido.
     */
    private static void validarVeiculo(String[] campos) throws RuntimeException {
        String log = "";
        
        if (campos.length != 4) {
            throw new RuntimeException(String.format(" > O veiculo deveria ter 4 campos, nao: %s\n", campos.length));
        }
        if (!(campos[1].equals("leve") || campos[1].equals("pesado"))) {
            throw new RuntimeException(String.format(" > O segundo campo de indentificacao deveria ser \"leve\" ou \"pesado\", nao: \"%s\"\n", campos[1]));
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
            throw new RuntimeException(log);
        }
    }
    
    /**
     * Método responsável pela validação de um atendimento.
     * @param campos - um vetor de campos de texto que compõem um
     * atendimento.
     * @throws RuntimeException - alguma exceção indicando que algum dos
     * campos e invalido.
     */
    private static void validarAtendimento(String[] campos) throws RuntimeException {
        String log = "";
        
        if (campos.length != 3) {
            throw new RuntimeException(String.format(" > O atendimento deveria ter 3 campos, nao: %s\n", campos.length));
        }
        if (!(campos[1].equals("automatico") || campos[1].equals("funcionario"))) {
            throw new RuntimeException(String.format(" > O segundo campo de indentificacao deveria ser \"automatico\" ou \"funcionario\", nao: \"%s\"\n", campos[1]));
        }
        
        try {
            Double.parseDouble(campos[2]);
        }
        catch (NumberFormatException e) {
            log += String.format(" > O terceiro campo deveria ser um valor double, nao: \"%s\"\n", campos[3]);
        }
        
        if (!log.equals("")) {
            throw new RuntimeException(log);
        }
    }
    
    /**
     * Método responsável pela validação dos campos de uma linha do
     * arquivo de dados.
     * @param campos - um vetor de campos de texto que compõem um
     * atendimento.
     * @return char - um caracter que representa um veiculo ('V'),
     * atendimento ('A') ou espaco em branco (' ').
     * @throws RuntimeException - alguma exceção indicando que algum dos
     * campos e invalido.
     */
    private static char validarCampos(String[] campos) throws RuntimeException {
        try {
            if (campos[0].equals("")) {
                return ' ';
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
                throw new RuntimeException(String.format(" > O primeiro campo de indentificacao deveria ser \"veiculo\" ou \"atendimento\", nao: \"%s\"\n", campos[0]));
            }
        }
        catch (RuntimeException e) {
            throw e;
        }
    }
}
