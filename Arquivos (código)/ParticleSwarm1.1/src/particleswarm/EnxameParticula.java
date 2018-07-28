package particleswarm;

/**
 * @author Caio Petrucci dos Santos Rosa
 *
 */

public class EnxameParticula {

	public static int maximoIteracoes = 10;  // indica o máximo de iterações que serão feitas 
	public static int numeroParticulas = 2;  // indica a quantidade de partículas que serão criadas
	public static int numeroVariaveis = 2;   // indica a quantidade de variáveis que existiram
	
	private static Particula[] enxame = new Particula[numeroParticulas];  // cria um vetor de partículas que será o enxame
	private static Problema problema = new Problema();                    // cria um objeto para o problema proposto
	
	public static void main(String[] args) {
		double[] melhorGlobal = new double[numeroVariaveis]; // cria um vetor das melhores variáveis
		for (int i = 0; i < numeroVariaveis; i++)
			melhorGlobal[i] = Double.MAX_VALUE;              // os valores iniciarão sendo o maior possível
		
		for (int i = 0; i < numeroParticulas; i++)       // percorre o vetor do enxame, instanciando cada partícula
			enxame[i] = new Particula(numeroVariaveis);
		
		for (int i = 0; i < maximoIteracoes; i++) {       // loop que realiza as iterações
			for (int j = 0; j < numeroParticulas; j++) {  // atualiza a posição de cada partícula
				enxame[j].atualizar();
				
			    if (problema.f(enxame[j].obterMelhorP()) < problema.f(melhorGlobal)) // verifica se encontrou um valor melhor para o 'melhorGlobal'
					melhorGlobal = enxame[j].obterMelhorP();
			}
			for (int j = 0; j < numeroParticulas; j++) // calcula a velocidade de cada partícula
				enxame[j].calcVel(melhorGlobal);
		}
	}
}
