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
public abstract class GerenciadorDeArquivos {
    
    /**
     * Construtor da classe GerenciadorDeArquivos.
     */
    public GerenciadorDeArquivos() {}
    
    /**
     * Método responsável pela leitura do arquivo de dados.
     * @param nomeArquivo - nome do arquivo texto que contem os dados
     * dos veiculos e atendimentos.
     * @throws IOException - exceção indicando alguma falha de
     * leitura.
     */
    public static ArrayList<String[]> lerDados(String nomeArquivo) throws IOException {
        ArrayList<String[]> dados = null;
        ArrayList<String> logs = new ArrayList<String>();

        try (BufferedReader arqBF = new BufferedReader(new FileReader(nomeArquivo))) {
            dados = new ArrayList<String[]>();

            int num_linha = 0;
            String texto_linha = arqBF.readLine();
            while (texto_linha != null) {
                String[] campos = texto_linha.split(",");
                num_linha ++;
                
                try {
                    switch (GerenciadorDeDados.validarCampos(campos)) {
                        case ' ':
                            break;
                        
                        default:
                            dados.add(campos);

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
            
            throw new IOException(log);
        }

        salvarArquivo("log.txt", mensagemLogs(logs));

        return dados;
    }
    
    /**
     * Método responsável por salvar um texto em um arquivo de tipo
     * texto.
     * @param nomeArquivo - nome do arquivo texto que contem os dados
     * dos veiculos e atendimentos.
     * @param texto - o texto com as informações que serão salvas.
     * @throws IOException - exceção indicando alguma falha de
     * escrita.
     */
    public static void salvarArquivo(String nomeArquivo, String texto) throws IOException {
        try (FileWriter arqFW = new FileWriter(nomeArquivo)) {
            arqFW.write(texto);
        }
        catch (IOException e) {
            throw new IOException(String.format("Falhar ao salvar os dados no arquivo: %s\n > %s", nomeArquivo, e.getMessage()));
        }
    }
    
    private static String mensagemLogs(ArrayList<String> logs) {
        String texto = "Log completo:\n";
        
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
}
