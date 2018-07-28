package analisealgoritmo;

/**
 * @author Caio Petrucci dos Santos Rosa
 *
 */

import org.math.plot.*;
import static org.math.array.DoubleArray.increment;

import javax.swing.JFrame;
 
public class HillClimbing2D {
		public static int numeroMaximo = 100000;
		public static double precisao = 0.1;
		public static double amplitude = 10.0;
		public static double quantiaMaxima = 100;
		
		public static int tamanhoDaBusca = 0;
		
		public static Plot2DPanel plotGrafico = new Plot2DPanel();
		public static Plot2DPanel plotConvergenciaFuncao = new Plot2DPanel();
		public static Plot2DPanel plotConvergenciaVariaveis = new Plot2DPanel();
		
		public static double[] pontoMaximoX = new double[1];
		public static double[] pontoMaximoY = new double[1];
		
		public static double[] historicoValoresDeY = new double[numeroMaximo];
		private static double[] historicoValoresDeX = new double[numeroMaximo];
	
        public void mostrarHillClimbing() {
        	buscarMaximo();  // o programa executa o algoritmo de Hill Climbing e obtêm os valores para construir os gráficos
        	gerarGraficos();
        }
        
		public void buscarMaximo() { // função que realiza o processo de Hill Climbing buscando o valor máximo da função										  
        	double x = Math.random();
        	double y = f(x);
        	double xVariado; 
        	
        	int quantiaIgual = 0;
        	
        	tamanhoDaBusca = 0;
        	
        	if (amplitude == 0.0)	
        		amplitude = 1.0;
        	if (precisao == 0.0) 
        		precisao = 0.1;

        	for (int i = 0; i < numeroMaximo; i++) {
        		xVariado = (amplitude * Math.random() - amplitude/2)+x; // indica uma variação aleatório. OBS: definir limite de aleatoriedade
        		
        		y = f(x);
        		double yAux = f(xVariado);
        		
        		quantiaIgual++;
        		
        		if (y < yAux) {
        			if ((yAux - y) > precisao) 
        				quantiaIgual = 0;
     
        			y = yAux;
        			x = xVariado;
        			
        			if (amplitude > 1)
        				amplitude *= 0.95;	
        		}

        		historicoValoresDeY[i] = y;
        		historicoValoresDeX[i] = x;
        		tamanhoDaBusca++;
        		
				if (quantiaIgual > quantiaMaxima)
        			break;
        	}
        	
        	pontoMaximoX[0] = x;		// preenche o vetor com as coordenadas do ponto máximo
        	pontoMaximoY[0] = y;
        }
		
		////////////////////////////////// Métodos privados ///////////////////////////////
		
		private static void gerarGraficos() {
			
			//////////////////////// Gráfico da Função ///////////////////////
					
			double[] x = increment(-10.0, 0.01, 10.0); // define os dois eixos
		    double[] y = f(x);    
		   
		    plotGrafico.addLinePlot("f(x) = 10 - x - x^2", x, y); // adiciona a linha da função ao painel
		    plotGrafico.addScatterPlot("Ponto máximo da função", pontoMaximoX, pontoMaximoY);  
		   
		    JFrame frameGrafico = new JFrame("Gráfico da função");
		    frameGrafico.setSize(16*70, 9*70);
		    frameGrafico.setContentPane(plotGrafico);
		    frameGrafico.setVisible(true); 
		    
		    ////////////////////////// Gráfico da Convergência //////////////////////////
		    
		    double[] escalaEixoX = increment(0.0, 1.0, tamanhoDaBusca);
		    
		    double[] historicoBuscaY = new double[tamanhoDaBusca]; 
		    double[] historicoBuscaX = new double[tamanhoDaBusca];    
		    
		    for (int i = 0; i < tamanhoDaBusca; i++) {
		    	historicoBuscaY[i] = historicoValoresDeY[i];
		    	historicoBuscaX[i] = historicoValoresDeX[i];
		    }
		   
		    JFrame frameConvergenciaFuncao = new JFrame("Gráfico da convergência da função");
		    frameConvergenciaFuncao.setSize(16*50, 9*50);
		    frameConvergenciaFuncao.setContentPane(plotConvergenciaFuncao);
		    frameConvergenciaFuncao.setVisible(true); 
		                    
		    plotConvergenciaFuncao.addLinePlot("Convergência da função", escalaEixoX, historicoBuscaY); // adiciona a linha da função ao painel
		    plotConvergenciaFuncao.setAxisLabel(0, "Iterações");
		    plotConvergenciaFuncao.setAxisLabel(1, "Valor da função de X");
		    plotConvergenciaFuncao.setFixedBounds(0, 0.0, escalaEixoX[escalaEixoX.length-1]);
		    
		    JFrame frameConvergenciaVariaveis = new JFrame("Gráfico da convergência das variáveis");
		    frameConvergenciaVariaveis.setSize(16*50, 9*50);
		    frameConvergenciaVariaveis.setContentPane(plotConvergenciaVariaveis);
		    frameConvergenciaVariaveis.setVisible(true); 
		                    
		    plotConvergenciaVariaveis.addLinePlot("Convergência do valor das variáveis", escalaEixoX, historicoBuscaX); // adiciona a linha da função ao painel
		    plotConvergenciaVariaveis.setAxisLabel(0, "Iterações");
		    plotConvergenciaVariaveis.setAxisLabel(1, "Valor das variáveis");
		    plotConvergenciaVariaveis.setFixedBounds(0, 0.0, escalaEixoX[escalaEixoX.length-1]);
		}

		private static double[] f(double[] x) {
			double[] y = new double[x.length]; // cria um vetor de mesmo tamanho do x
			for (int i = 0; i < x.length; i++) { // percorre os vetores calculando a função de x para cada valor de x
				y[i] = f(x[i]);
			}
			return y; // devolve o vetor y
		}
		
		private static double f(double x) { // procedimento que calcula a função de x
		double y = -x*x + 5*x - 6; 	
		return y;
		}
}
