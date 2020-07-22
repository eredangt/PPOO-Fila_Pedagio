public class TesteGDD {
    public static void main(String[] args) {
        GerenciadorDeDados gdd = new GerenciadorDeDados();
        String nomeArquivoDeDados = "Dados.txt";
        // String nomeArquivoDeSaida = "relatorio.txt";
        
        try {
            gdd.inicializarDados(nomeArquivoDeDados);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.print(gdd.getFilaRand());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println();
        
        try {
            System.out.print(gdd.getIntervaloChegada());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println();
        
        try {
            while (true) {
                System.out.print(gdd.removerCabine());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println();
        
        try {
            while (true) {
                System.out.print(gdd.removerVeiculo());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println();
        
        try {
            while (true) {
                System.out.print(gdd.removerAtendimento());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
