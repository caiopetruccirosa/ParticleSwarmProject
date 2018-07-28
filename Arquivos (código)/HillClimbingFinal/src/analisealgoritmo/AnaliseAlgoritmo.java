package analisealgoritmo;

/**
 * @author Caio Petrucci dos Santos Rosa
 *
 */

import org.math.plot.*;
import static org.math.array.DoubleArray.increment;

import javax.swing.JFrame;

public class AnaliseAlgoritmo {
	
	public static HillClimbing2D hillClimbing = new HillClimbing2D();
	
	// matrizes que indica algo sobre simulações sobre determinadas amplitude e precisão
	public static double[][] mediaDados = new double[100][100];    // matriz com a média de iterações
	public static double[][] minimoDados = new double[100][100];   // matriz com a quantidade mínima de iterações para cada simulação
	public static double[][] maximoDados = new double[100][100];   // matriz com a quantidade máxima de iterações para cada simulação
	public static double[][] desvioPadraoDados = new double[100][100]; // matriz com o desvio padrão de cada simulação
	public static double[][] valoresYDados = new double [100][100]; // matriz com a média dos melhores resultados de cada simulação 
	
	public static void main(String[] args) {
		double melhorMedia = hillClimbing.numeroMaximo;
		double melhorDesvio = hillClimbing.numeroMaximo;
		double melhorResultado = -Double.MAX_VALUE;
		
		int[] posicao = new int [2];
		
		preencherValores(); // procedimento que preenche as matrizes com seus respectivos valores
	
		for (int i = 1; i < 100; i++) {
			for (int j = 1; j < 100; j++) {
				if (melhorResultado < valoresYDados[i][j]) {
					melhorResultado = valoresYDados[i][j]; 
					melhorMedia = mediaDados[i][j];
					melhorDesvio = desvioPadraoDados[i][j];
					posicao[0] = i;
					posicao[1] = j;
				} else if (melhorResultado == valoresYDados[i][j]) {
					if (mediaDados[i][j] < melhorMedia) {
						melhorResultado = valoresYDados[i][j]; 
						melhorMedia = mediaDados[i][j];
						melhorDesvio = desvioPadraoDados[i][j];
						posicao[0] = i;
						posicao[1] = j;
					}					
					else if (mediaDados[i][j] == melhorMedia) {
						if (desvioPadraoDados[i][j] < melhorDesvio) {
							melhorResultado = valoresYDados[i][j]; 
							melhorMedia = mediaDados[i][j];
							melhorDesvio = desvioPadraoDados[i][j];
							posicao[0] = i;
							posicao[1] = j;
						}
					}
				}
			}
		}
		System.out.println(melhorResultado);
		
		//produzirGrafico(posicao); // procedimento que produz o gráfico com o desvio padrão e a melhor curva
	}
	
	public static void preencherValores() { // preenche todas as matrizes com seus respectivos valores
		double amplitude = 0;
		double precisao = 0;
		
		double minimoIteracoes;
		double maximoIteracoes; 
		double somaIteracoes;
		double mediaIteracoes;
		double somaResultados;
		double mediaResultados;
		
		for (int i = 1; i < 100; i++) {	// percorre todas as matrizes, adicionando os cabeçalhos que indicam os valores da amplitude e da precisão
			amplitude += 5.0;				
			precisao += 0.1;
			mediaDados[i][0] = amplitude;
			mediaDados[0][i] = precisao;
			
			minimoDados[i][0] = amplitude;
			minimoDados[0][i] = precisao;
			
			maximoDados[i][0] = amplitude;
			maximoDados[0][i] = precisao;
			
			desvioPadraoDados[i][0] = amplitude;
			desvioPadraoDados[0][i] = precisao;
			
			valoresYDados[i][0] = amplitude;
			valoresYDados[0][i] = precisao;
		}
		
		for (int i = 1; i < 100; i++) { // realiza várias simulações para obter todas as médias e desvios
			hillClimbing.precisao = mediaDados[0][i];
			for (int j = 1; j < 100; j++) {
				hillClimbing.amplitude = mediaDados[j][0];
				
				minimoIteracoes = hillClimbing.numeroMaximo;
				maximoIteracoes = 0; 
				somaIteracoes = 0;
				mediaIteracoes = 0;
				somaResultados = 0;
				mediaResultados = 0;
				
				for (int loopInterno = 0; loopInterno < 10; loopInterno++) {
					hillClimbing.buscarMaximo();
					somaIteracoes += hillClimbing.tamanhoDaBusca;
					somaResultados += hillClimbing.pontoMaximoY[0];
					if (hillClimbing.tamanhoDaBusca < minimoIteracoes)
						minimoIteracoes = hillClimbing.tamanhoDaBusca;
					if (hillClimbing.tamanhoDaBusca > maximoIteracoes)
						maximoIteracoes = hillClimbing.tamanhoDaBusca;
				}
				mediaIteracoes = somaIteracoes/10;
				mediaResultados = somaResultados/10;
				
				mediaDados[i][j] = mediaIteracoes;
				maximoDados[i][j] = maximoIteracoes;
				minimoDados[i][j] = minimoIteracoes;
				valoresYDados[i][j] = mediaResultados;
				desvioPadraoDados[i][j] = calcularDesvioPadrao(mediaDados[j][0], mediaDados[0][i], mediaIteracoes);
			}
		}
	}
	
	public static double calcularDesvioPadrao(double amplitude, double precisao, double mediaIteracoes) { // método que calcula o desvio padrão de determinada combinação de amplitude e precisão
		double variancia = 0;
		double desvioPadrao = 0;
		
		hillClimbing.amplitude = amplitude;	// a combinação de amplitude e precisão são passados como parâmetros para 
		hillClimbing.precisao = precisao;	// definir qual combinação terá seu desvio padrão calculado
		
		for (int i = 0; i < 100; i ++) {
			hillClimbing.buscarMaximo();
			variancia += (hillClimbing.tamanhoDaBusca-mediaIteracoes) * (hillClimbing.tamanhoDaBusca-mediaIteracoes);
		}
		variancia = variancia/100 ;
		desvioPadrao = Math.sqrt(variancia);	
		
		return desvioPadrao;
	}
	
	public static void produzirGrafico(int[] posicao) { // método que produz o gráfico mostrando a variação do valor da função até a otimização
		Plot2DPanel plot = new Plot2DPanel();
		
		hillClimbing.amplitude = mediaDados[posicao[0]][0];	// define o valor da amplitude como o médio que é colocado no meio do vetor passado como parâmetro
		hillClimbing.precisao = mediaDados[0][posicao[1]];    // define o valor da precisão como o médio
		
		System.out.println(mediaDados[posicao[0]][0]);
		System.out.println(mediaDados[0][posicao[1]]);
		
		hillClimbing.buscarMaximo(); // executa o algoritmo de HillClimbing buscando o valor máximo da função

		double[] x = increment(0.0, 1.0, hillClimbing.tamanhoDaBusca);
		double[] yOtimo = new double[hillClimbing.tamanhoDaBusca];
		double[] yDesvioMaior = new double[hillClimbing.tamanhoDaBusca];
		double[] yDesvioMenor = new double[hillClimbing.tamanhoDaBusca];

		double diminuicao = desvioPadraoDados[posicao[0]][posicao[1]]/hillClimbing.tamanhoDaBusca;
		
		for (int i = 0; i < hillClimbing.tamanhoDaBusca; i++) { // percorre o histórico gera no HillClimbing para montar um vetor Y que gerará o gráfico
			yOtimo[i] = hillClimbing.historicoValoresDeY[i];
			yDesvioMaior[i] = hillClimbing.historicoValoresDeY[i] + desvioPadraoDados[posicao[0]][posicao[1]] - diminuicao*i;
			yDesvioMenor[i] = hillClimbing.historicoValoresDeY[i] - desvioPadraoDados[posicao[0]][posicao[1]] + diminuicao*i;
		}
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		plot.addLinePlot("Combinação otimizada", x, yOtimo); // adiciona a linha da função ao painel
		plot.addLinePlot("Desvio padrão", x, yDesvioMaior); // adiciona a linha da função ao painel
		plot.addLinePlot("Desvio padrão", x, yDesvioMenor); // adiciona a linha da função ao painel
		
		plot.setAxisLabel(0, "Iterações"); 			// padroniza o gráfico, dando nomes aos eixos e definindo uma escala
		plot.setAxisLabel(1, "Valor da função");
        plot.setFixedBounds(0, 0.0, x[x.length-1]); 
        
        JFrame frame = new JFrame("Gráfico da função"); // gera o frame do gráfico, colocando o plot como conteúdo
        frame.setSize(16*70, 9*70);
        frame.setContentPane(plot);
        frame.setVisible(true); 
        
        System.out.print(yOtimo[yOtimo.length-1]);
	}
}
