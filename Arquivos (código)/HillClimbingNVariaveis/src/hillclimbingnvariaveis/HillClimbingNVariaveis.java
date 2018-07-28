package hillclimbingnvariaveis;

/**
 * @author Caio Petrucci dos Santos Rosa
 *
 */

import org.math.plot.*;
import static org.math.array.DoubleArray.increment;

import javax.swing.JFrame;
 
public class HillClimbingNVariaveis {
		static int qtasVariaveis = 6;
		
		public static int n = 10000;
		public static double precisao = 0.5;
		public static double amplitude = 10.0;
		public static double quantiaConvergencia = 100;
		
		private static int tamanhoBusca = 0;
		private static double[][] matrizValores = new double[qtasVariaveis+1][n];
		
		public void hillClimbing() {
        		buscaMinimo();  // o programa executa o algoritmo de Hill Climbing e obtêm os valores para construir os gráficos
        		
        		//////////////////////// Gráfico da Convergência da Função ///////////////////////
                         
        		double[] xEscala = increment(0.0, 1.0, tamanhoBusca);
        		
        		Plot2DPanel plotConvergenciaFuncao = new Plot2DPanel();
        		
                plotConvergenciaFuncao.addLinePlot("Convergência do valor da função", xEscala, matrizValores[0]);
                
                plotConvergenciaFuncao.setAxisLabel(0, "Iterações");
                plotConvergenciaFuncao.setAxisLabel(1, "Valor da função");
                plotConvergenciaFuncao.setFixedBounds(0, 0.0, xEscala[xEscala.length-1]);
                
                JFrame frameConvergenciaFuncao = new JFrame("Gráfico da convergência da função");
                frameConvergenciaFuncao.setSize(16*70, 9*70);
                frameConvergenciaFuncao.setContentPane(plotConvergenciaFuncao);
                frameConvergenciaFuncao.setVisible(true);
                
                ///////////////////// Gráfico da Convergência das Variáveis //////////////////////
                
                Plot2DPanel plotConvergenciaVariaveis = new Plot2DPanel();
                
                for (int i = 1; i < qtasVariaveis+1; i++)   // gera uma linha no gráfico para cada variável
                	plotConvergenciaVariaveis.addLinePlot("Convergência do valor de x" + i, xEscala, matrizValores[i]);
                
                plotConvergenciaVariaveis.setAxisLabel(0, "Iterações");
                plotConvergenciaVariaveis.setAxisLabel(1, "Valor das variáveis");
                plotConvergenciaVariaveis.setFixedBounds(0, 0.0, xEscala[xEscala.length-1]);
                
                JFrame frameConvergenciaVariaveis = new JFrame("Gráfico da convergência das variáveis");
                frameConvergenciaVariaveis.setSize(16*70, 9*70);
                frameConvergenciaVariaveis.setContentPane(plotConvergenciaVariaveis);
                frameConvergenciaVariaveis.setVisible(true);
                
                System.out.println("O algoritmo ver " + tamanhoBusca + " iterações");
        }
        
		////////////////////////////////// Métodos privados ///////////////////////////////
		
        private static double f(double[] x) { // procedimento que calcula a função de x
        	double f = 0;  
        	for (int i = 0; i < x.length; i++) {
        		f += x[i]*x[i];
        	}	
        	return f;
        }
        
		private void buscaMinimo() { // função que realiza o processo de Hill Climbing buscando o valor máximo da função		
			
        	double[] variaveis = new double[qtasVariaveis];  ///
        													  // gera o vetor de N variáveis
        	for (int i = 0; i < variaveis.length; i++)		  //
        		variaveis[i] = Math.random() * 50.0;		 ///
        	
        	double funcao = f(variaveis);
        	double variacao;
        	
        	int qtasVezesIgual = 0;
        	
        	for (int i = 0; i < n; i++) {       		
        		double[] variaveisAux = new double[qtasVariaveis];
        		for (int j = 0; j < variaveis.length; j++) {
        			variacao = amplitude * Math.random() - amplitude/2;
        			variaveisAux[j] = variaveis[j] + variacao;
        		}
        		
        		funcao = f(variaveis);
        		double funcaoAux = f(variaveisAux);
        		
        		qtasVezesIgual++;
        		
        		if (funcaoAux < funcao) {
        			if ((funcao - funcaoAux) > precisao) 
        				qtasVezesIgual = 0;
        			else if (qtasVezesIgual > quantiaConvergencia/2)
            			amplitude *= 5;
        			
        			funcao = funcaoAux;
        			variaveis = variaveisAux;
        			
        			if (amplitude > 1)
        				amplitude *= 0.95;
        		}
        		
        		for (int j = 1; j < qtasVariaveis+1; j++)
        			matrizValores[j][i] = variaveis[j-1];
        		matrizValores[0][i] = funcao;
        		tamanhoBusca++;
        		
        		if (qtasVezesIgual > quantiaConvergencia)
        			break;
        	}
        }
}