package particleswarm;

/**
 * @author Caio Petrucci dos Santos Rosa
 *
 */

public class Particula {

	private double[] velocidade;    // velocidade da partícula
	private double[] variaveis;		// variáveis atuais da partícula
	private double[] melhorPessoal;	// melhores variáveis já acessadas pela partícula
	
	private double amplitudeVariaveis = 1000; // variável que determina a amplitude da região que serão geradas as partículas
	private double C1 = 2.0;  // coeficiente que multiplica o fator pessoal da velocidade
	private double C2 = 2.0;  // coeficiente que multiplica o fator global da velocidade
	
	private Problema problema = new Problema();
	
	public Particula(int numeroVariaveis) {     // construtor da classe
		variaveis = new double[numeroVariaveis];
		velocidade = new double[numeroVariaveis];
		
		for (int i = 0; i < variaveis.length; i++)
			variaveis[i] = Math.random() * amplitudeVariaveis -  amplitudeVariaveis/2; // posiciona a partícula aleatoriamente na região de pesquisa
		
		melhorPessoal = variaveis;  // o melhorPessoal iniciará como o valor da posição inicial
	}
	
	public void calcVel(double[] melhorGlobal) { // método que calcula a velocidade de cada partícula
		double porcentagem = Math.random();
		for (int i = 0; i < variaveis.length; i++)
			velocidade[i] = 0.5*velocidade[i] + (C1 * (melhorPessoal[i] - variaveis[i]) * porcentagem + C2 * (melhorGlobal[i] - variaveis[i]) * (1-porcentagem));
	}
	
	public void atualizar() { // método que atualiza a posição da partícula e atualiza o valor de 'melhorPessoal'
		for (int i = 0; i < variaveis.length; i++)
			variaveis[i] += velocidade[i];
			
		if (problema.f(variaveis) < problema.f(melhorPessoal))
			melhorPessoal = variaveis;
	}
	
	public double[] obterMelhorP() { // função que devolve o valor de 'melhorPessoal', já que é um atributo privado
		return melhorPessoal;
	}
}
