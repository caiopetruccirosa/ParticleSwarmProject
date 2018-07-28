package particleswarm;

/**
 * @author Caio Petrucci dos Santos Rosa
 *
 */

public class EnxameParticula {

	public static int maximoIteracoes = 1000; // indica o máximo de iterações que serão feitas 
	public static int numeroParticulas = 100; // indica o máximo de partículas que serão criadas 
	
	private static Particula[] enxame = new Particula[numeroParticulas]; // cria um vetor de partículas que será o enxame
	private static Problema problema = new Problema();                   // cria um objeto para o problema proposto
	
	public static void main(String[] args) {
		double melhorGlobal = -Double.MAX_VALUE;  // o valor inicial de 'melhorGlobal' será o melhor possível
		
		for (int i = 0; i < numeroParticulas; i++) // perorre todo o vetor, instanciando cada partícula
			enxame[i] = new Particula();
		
		for (int i = 0; i < maximoIteracoes; i++) { // loop que executa cada iteração da busca
			for (int j = 0; j < numeroParticulas; j++) {
				enxame[j].atualizar();   // atualiza a posição de cada partícula
				
				if (problema.f(melhorGlobal) < problema.f(enxame[j].obterMelhorP())) // verifica se encontrou um valor melhor
					melhorGlobal = enxame[j].obterMelhorP();
			}
			for (int j = 0; j < numeroParticulas; j++) {
				enxame[j].calcVel(melhorGlobal);  // calcula a velocidade de cada partícula
			}
		}
		
		System.out.println(melhorGlobal);              // escreve qual foi o melhor valor de x encontrado
		System.out.println(problema.f(melhorGlobal));  // escreve qual foi o melhor valor da função x encontrado
	}
}
