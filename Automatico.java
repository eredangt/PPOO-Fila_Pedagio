public class Automatico extends Atendimento{
	
	private static int numeroAutomaticos;
	private int idAutomatico;
	
	Automatico(double tempoAtendimento){
		super(tempoAtendimento);
		
		numeroAutomaticos += 1;
		idAutomatico = numeroAutomaticos;
	}
	
	protected int getIdAutomatico(){
		return idAutomatico;
	}
	
	@Override
	public String toString(){
		return super.toString() + "Identificação Cobrança Automática: " + getIdAutomatico() + "\n";
	}
	
}
