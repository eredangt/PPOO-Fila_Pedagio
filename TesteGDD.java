import java.util.ArrayList;

public class TesteGDD {
    public static void main(String[] args) {
        String nomeArquivoDeDados = "Dados.txt";
        // String nomeArquivoDeSaida = "relatorio.txt";
        
        ArrayList<Object> objetos = null;
        try {
            objetos = GerenciadorDeDados.inicializarDados(nomeArquivoDeDados);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        for (Object obj: objetos) {
            if (obj instanceof Boolean)
                System.out.print((Boolean) obj);
            
            else if (obj instanceof Integer)
                System.out.print((Integer) obj);
            
            else if (obj instanceof Cabine) {
                System.out.print((Cabine) obj);
            }
            
            else if (obj instanceof Veiculo) {
                System.out.print((Veiculo) obj);
            }
                
            else if (obj instanceof Atendimento) {
                System.out.print((Atendimento) obj);
            }
        }
    }
}
