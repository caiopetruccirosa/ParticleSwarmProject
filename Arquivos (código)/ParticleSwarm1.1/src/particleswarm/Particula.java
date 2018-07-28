package particleswarm;

/**
 * @author Caio Petrucci dos Santos Rosa
 *
 */

public class Particula {

	private double[] velocidade;    // velocidade da part�cula
	private double[] variaveis;		// vari�veis atuais da part�cula
	private double[] melhorPessoal;	// melhores vari�veis j� acessadas pela part�cula
	
	private double amplitudeVariaveis = 1000; // vari�vel que determina a amplitude da regi�o que ser�o geradas as part�culas
	private double C1 = 2.0;  // coeficiente que multiplica o fator pessoal da velocidade
	private double C2 = 2.0;  // coeficiente que multiplica o fator global da velocidade
	
	private Problema problema = new Problema();
	
	public Particula(int numeroVariaveis) {     // construtor da classe
		variaveis = new double[numeroVariaveis];
		velocidade = new double[numeroVariaveis];
		
		for (int i = 0; i < variaveis.length; i++)
			variaveis[i] = Math.random() * amplitudeVariaveis -  amplitudeVariaveis/2; // posiciona a part�cula aleatoriamente na regi�o de pesquisa
		
		melhorPessoal = variaveis;  // o melhorPessoal iniciar� como o valor da posi��o inicial
	}
	
	public void calcVel(double[] melhorGlobal) { // m�todo que calcula a velocidade de cada part�cula
		double porcentagem = Math.random();
		for (int i = 0; i < variaveis.length; i++)
			velocidade[i] = 0.5*velocidade[i] + (C1 * (melhorPessoal[i] - variaveis[i]) * porcentagem + C2 * (melhorGlobal[i] - variaveis[i]) * (1-porcentagem));
	}
	
	public void atualizar() { // m�todo que atualiza a posi��o da part�cula e atualiza o valor de 'melhorPessoal'
		for (int i = 0; i < variaveis.length; i++)
			variaveis[i] += velocidade[i];
			
		if (problema.f(variaveis) < problema.f(melhorPessoal))
			melhorPessoal = variaveis;
	}
	
	public double[] obterMelhorP() { // fun��o que devolve o valor de 'melhorPessoal', j� que � um atributo privado
		return melhorPessoal;
	}
}
