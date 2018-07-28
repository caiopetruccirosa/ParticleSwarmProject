package particleswarm;

/**
 * @author Caio Petrucci dos Santos Rosa
 *
 */

public class Particula {

	private double velocidade;      // velocidade da partícula
	private double posicao;			// posição atual da partícula
	private double melhorPessoal;	// melhor posição já acessada pela partícula
	
	private double amplitudePosicao = 1000; // variável que determina a amplitude da região que serão geradas as partículas
	private double C1 = 2.0;  // coeficiente que multiplica o fator pessoal da velocidade
	private double C2 = 2.0;  // coeficiente que multiplica o fator global da velocidade
	
	private Problema problema = new Problema();
	
	public Particula() { // construtor da classe
		velocidade = 0.0;                                                 // determina a velocidade inicial da partícula como 0
		posicao = Math.random() * amplitudePosicao -  amplitudePosicao/2; // posiciona a partícula aleatoriamente na região de pesquisa
		melhorPessoal = posicao;                                          // o melhorPessoal iniciará como o valor da posição inicial
	}
	
	public void calcVel(double melhorGlobal) { // método que calcula a velocidade de cada partícula
		double porcentagem = Math.random();
		
		velocidade += (C1 * (melhorPessoal - posicao) * porcentagem + C2 * (melhorGlobal - posicao) * (1-porcentagem));
	}
	
	public void atualizar() { // método que atualiza a posição da partícula e atualiza o valor de 'melhorPessoal'
		posicao += velocidade;
		
		if (problema.f(melhorPessoal) < problema.f(posicao))
			melhorPessoal = posicao;
	}
	
	public double obterMelhorP() { // função que devolve o valor de 'melhorPessoal', já que é um atributo privado
		return melhorPessoal;
	}
}
