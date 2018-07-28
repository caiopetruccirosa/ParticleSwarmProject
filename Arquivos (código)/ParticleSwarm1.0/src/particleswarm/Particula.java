package particleswarm;

/**
 * @author Caio Petrucci dos Santos Rosa
 *
 */

public class Particula {

	private double velocidade;      // velocidade da part�cula
	private double posicao;			// posi��o atual da part�cula
	private double melhorPessoal;	// melhor posi��o j� acessada pela part�cula
	
	private double amplitudePosicao = 1000; // vari�vel que determina a amplitude da regi�o que ser�o geradas as part�culas
	private double C1 = 2.0;  // coeficiente que multiplica o fator pessoal da velocidade
	private double C2 = 2.0;  // coeficiente que multiplica o fator global da velocidade
	
	private Problema problema = new Problema();
	
	public Particula() { // construtor da classe
		velocidade = 0.0;                                                 // determina a velocidade inicial da part�cula como 0
		posicao = Math.random() * amplitudePosicao -  amplitudePosicao/2; // posiciona a part�cula aleatoriamente na regi�o de pesquisa
		melhorPessoal = posicao;                                          // o melhorPessoal iniciar� como o valor da posi��o inicial
	}
	
	public void calcVel(double melhorGlobal) { // m�todo que calcula a velocidade de cada part�cula
		double porcentagem = Math.random();
		
		velocidade += (C1 * (melhorPessoal - posicao) * porcentagem + C2 * (melhorGlobal - posicao) * (1-porcentagem));
	}
	
	public void atualizar() { // m�todo que atualiza a posi��o da part�cula e atualiza o valor de 'melhorPessoal'
		posicao += velocidade;
		
		if (problema.f(melhorPessoal) < problema.f(posicao))
			melhorPessoal = posicao;
	}
	
	public double obterMelhorP() { // fun��o que devolve o valor de 'melhorPessoal', j� que � um atributo privado
		return melhorPessoal;
	}
}
