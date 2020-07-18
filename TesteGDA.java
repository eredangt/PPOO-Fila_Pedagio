

public class TesteGDA {
    public static void main(String[] args) {
        GerenciadorDeArquivos gda = new GerenciadorDeArquivos();
        String nomeArquivoDeDados = "Dados.txt";
        String nomeArquivoDeSaida = "relatorio.txt";
        
        System.out.println();
        System.out.println(gda);
        
        try {
            gda.lerDados(nomeArquivoDeDados);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println();
        System.out.println(gda);
        
        try {
            String[] veiculo;
            while (true) {
                veiculo = gda.removerVeiculo();
                for (String s: veiculo) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println();
        
        try {
            String[] atendimento;
            while (true) {
                atendimento = gda.removerAtendimento();
                for (String s: atendimento) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println();
        System.out.println(gda);
        
        gda.gerarArquivoDeFormatacao();
    }
}
