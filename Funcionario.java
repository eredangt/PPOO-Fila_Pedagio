public class Funcionario extends Atendimento{
	
	private static int numeroFuncionarios;
	private int idFuncionario;
	
	
	Funcionario(double tempoAtendimento){
		super(tempoAtendimento);
		
		numeroFuncionarios += 1;
		idFuncionario = numeroFuncionarios;
	}
	
	protected int getIdFuncionario(){
		return idFuncionario;
	}
	
	@Override
	public String toString(){
		return super.toString() + "Identificação Funcionário: " + getIdFuncionario() + "\n";
	}
	
}
