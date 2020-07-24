
public class Testes{

	public static void main(String[] args){
		Atendimento a1 = new Automatico(5.0);
		Atendimento a2 = new Funcionario(6.0);
		Atendimento a3 = new Automatico(11.0);
		Atendimento a4 = new Funcionario(26.0);
		Atendimento a5 = new Automatico(35.0);
		Atendimento a6 = new Funcionario(90.0);
		Atendimento a7 = new Automatico(100.0);
		Atendimento a8 = new Funcionario(12.0);
		
		System.out.println(a1.toString());
		System.out.println(a3.toString());
		System.out.println(a5.toString());
		System.out.println(a7.toString());
		
		System.out.println(a2.toString());
		System.out.println(a4.toString());
		System.out.println(a6.toString());
		System.out.println(a8.toString());
	}

}
