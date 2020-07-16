public abstract class Atendimento{
	
	private static int numeroAtendimentos;
	private int idAtendimento;
	private double tempoAtendimento;
	
	public Atendimento(double umTempoAtendimento){	
		tempoAtendimento = umTempoAtendimento;
		numeroAtendimentos += 1;
		idAtendimento = numeroAtendimentos;
	}
	
	protected int getNumeroAtendimentos(){
		return numeroAtendimentos;
	}
	
	protected int getIdAtendimento(){
		return idAtendimento;
	}
	
	protected double getTempoAtendimento(){
		return tempoAtendimento;
	}
	
	@Override
	public String toString(){
		return "CABINE " + getIdAtendimento() + ":\nTEMPO: " + getTempoAtendimento() + "\n";
	}
	
}
