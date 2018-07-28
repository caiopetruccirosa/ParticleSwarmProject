package particleswarm;

/**
 * @author Caio Petrucci dos Santos Rosa
 *
 */

public class EnxameParticula {

	public static int maximoIteracoes = 10;  // indica o m�ximo de itera��es que ser�o feitas 
	public static int numeroParticulas = 2;  // indica a quantidade de part�culas que ser�o criadas
	public static int numeroVariaveis = 2;   // indica a quantidade de vari�veis que existiram
	
	private static Particula[] enxame = new Particula[numeroParticulas];  // cria um vetor de part�culas que ser� o enxame
	private static Problema problema = new Problema();                    // cria um objeto para o problema proposto
	
	public static void main(String[] args) {
		double[] melhorGlobal = new double[numeroVariaveis]; // cria um vetor das melhores vari�veis
		for (int i = 0; i < numeroVariaveis; i++)
			melhorGlobal[i] = Double.MAX_VALUE;              // os valores iniciar�o sendo o maior poss�vel
		
		for (int i = 0; i < numeroParticulas; i++)       // percorre o vetor do enxame, instanciando cada part�cula
			enxame[i] = new Particula(numeroVariaveis);
		
		for (int i = 0; i < maximoIteracoes; i++) {       // loop que realiza as itera��es
			for (int j = 0; j < numeroParticulas; j++) {  // atualiza a posi��o de cada part�cula
				enxame[j].atualizar();
				
			    if (problema.f(enxame[j].obterMelhorP()) < problema.f(melhorGlobal)) // verifica se encontrou um valor melhor para o 'melhorGlobal'
					melhorGlobal = enxame[j].obterMelhorP();
			}
			for (int j = 0; j < numeroParticulas; j++) // calcula a velocidade de cada part�cula
				enxame[j].calcVel(melhorGlobal);
		}
	}
}
