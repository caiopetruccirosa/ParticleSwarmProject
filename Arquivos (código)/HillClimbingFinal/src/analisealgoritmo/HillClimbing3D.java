package analisealgoritmo;

/**
 * @author Caio Petrucci dos Santos Rosa
 *
 */

import org.math.plot.*;
import static org.math.array.DoubleArray.increment;

import javax.swing.JFrame;
 
public class HillClimbing3D {
		public static int numeroMaximo = 100000;
		public static double precisao = 0.1;
		public static double amplitudeX = 10.0;
		public static double amplitudeY = 10.0;
		public static double quantiaMaxima = 50;
	
		private static int tamanhoDaBusca = 0;
		
		private static double[] historicoValoresDeZ = new double[numeroMaximo];
		private static double[] historicoValoresDeX = new double[numeroMaximo];
		private static double[] historicoValoresDeY = new double[numeroMaximo];
		private static double[] pontoMinimoX = new double[1];
		private static double[] pontoMinimoY = new double[1];
		private static double[] pontoMinimoZ = new double[1];
		
        public void hillClimbing() {
        	buscarMinimo();
        	
        	//////////////////////////// Gr�ficos ///////////////////////////
        	
        	double[] eixoX = increment(-5.00, 0.1, 5.0);
    		double[] eixoY = increment(-5.0, 0.1, 5.0);
    		double[][] matrizZ = f(eixoX, eixoY);					 	   
 
            Plot3DPanel plotGrafico = new Plot3DPanel();
            
            plotGrafico.addGridPlot("f(x) = 10 - x - x^2", eixoX, eixoY, matrizZ);     // adiciona a grade da fun��o ao painel
            plotGrafico.addScatterPlot("Ponto m�ximo da fun��o", pontoMinimoX, pontoMinimoY, pontoMinimoZ);   
            
            JFrame frameGrafico = new JFrame("Gr�fico da fun��o");
            frameGrafico.setSize(600, 600);
            frameGrafico.setContentPane(plotGrafico);
            frameGrafico.setVisible(true);
            
            ///////////////////////////// Gr�ficos 2D //////////////////////////////
            
            double[] historicoValoresZ = new double[tamanhoDaBusca]; 
            double[] historicoValoresX = new double[tamanhoDaBusca]; 
            double[] historicoValoresY = new double[tamanhoDaBusca];    
            
            double[] escalaEixoX = increment(0.0, 1.0, tamanhoDaBusca);
            
            for(int i = 0; i < tamanhoDaBusca; i++) {
            	historicoValoresZ[i] = historicoValoresDeZ[i];
            	historicoValoresX[i] = historicoValoresDeX[i];
            	historicoValoresY[i] = historicoValoresDeY[i];
            }
            
            ///////////////////////////// Gr�fico de Z ///////////////////////////
            
            Plot2DPanel plotConvergenciaFuncao = new Plot2DPanel();
            
            plotConvergenciaFuncao.addLinePlot("Converg�ncia da fun��o", escalaEixoX, historicoValoresZ); // adiciona a linha da fun��o ao painel
            plotConvergenciaFuncao.setAxisLabel(0, "Itera��es");
            plotConvergenciaFuncao.setAxisLabel(1, "Valor da fun��o de X e Y");
            plotConvergenciaFuncao.setFixedBounds(0, 0.0, escalaEixoX[escalaEixoX.length-1]);
            
            JFrame frameConvergenciaFuncao = new JFrame("Converg�ncia do valor da fun��o");
            frameConvergenciaFuncao.setSize(600, 600);
            frameConvergenciaFuncao.setContentPane(plotConvergenciaFuncao);
            frameConvergenciaFuncao.setVisible(true);
            
            
            /////////////////////////// Gr�fico das vari�veis //////////////////////
            
            Plot2DPanel plotConvergenciaVariaveis = new Plot2DPanel();
            
            plotConvergenciaVariaveis.addLinePlot("Valor de X", escalaEixoX, historicoValoresX); // adiciona a linha da fun��o ao painel
            plotConvergenciaVariaveis.addLinePlot("Valor de Y", escalaEixoX, historicoValoresY); // adiciona a linha da fun��o ao painel
            plotConvergenciaVariaveis.setAxisLabel(0, "Itera��es");
            plotConvergenciaVariaveis.setAxisLabel(1, "Valor das vari�veis");
            plotConvergenciaVariaveis.setFixedBounds(0, 0.0, escalaEixoX[escalaEixoX.length-1]);
            
            JFrame frameConvergenciaVariaveis = new JFrame("Converg�ncia do valor das vari�veis");
            frameConvergenciaVariaveis.setSize(600, 600);
            frameConvergenciaVariaveis.setContentPane(plotConvergenciaVariaveis);
            frameConvergenciaVariaveis.setVisible(true);
            
            System.out.println("X:" + pontoMinimoX[0]);
            System.out.println("Y:" + pontoMinimoY[0]);
            System.out.println("Z:" + pontoMinimoZ[0]);
            System.out.println("O programa fez " + tamanhoDaBusca + " itera��es");
        }
        
        ////////////////////////////////// M�todos privados ///////////////////////////////
        
        private double[][] f(double[] x, double[] y) {
        	double[][] z = new double[x.length][y.length]; // cria uma matriz dos mesmos tamanhos dos vetores x e y
        	for (int i = 0; i < x.length; i++) 			   // percorre os vetores calculando a fun��o de z para cada valor de x e y
        		for (int j = 0; j < y.length; j++)
        			z[i][j] = f(x[i], y[j]);
        	
        	return z; // devolve a matriz z
        }
        
        private static double f(double x, double y) { // procedimento que calcula a fun��o de x e y
        	double z = x*x + y*y - 10;	
        	return z;
        }
        
        private void buscarMinimo() { // fun��o que realiza o processo de Hill Climbing buscando o valor m�ximo da fun��o
        	double x = Math.random() * 20;
        	double y = Math.random() * 20;
        	double z = f(x, y);
        	
        	double variacaoX;
        	double variacaoY;
        	
        	int qtsVezesIgual = 0;
        	
        	for (int i = 0; i < numeroMaximo; i++) {
        		variacaoX = amplitudeX * Math.random() - amplitudeX/2; // indica uma varia��o aleat�rio. OBS: definir limite de aleatoriedade
        		variacaoY = amplitudeY * Math.random() - amplitudeY/2;
        		
        		z = f(x, y);
        		double zAux = f(x+variacaoX, y+variacaoY);
        		
        		qtsVezesIgual++;
        		
        		if (z > zAux) {
        			if ((z - zAux) > precisao) 
        				qtsVezesIgual = 0;
        			else if (qtsVezesIgual > quantiaMaxima/2) {
    					amplitudeX *= 5;
    					amplitudeY *= 5;
            		}
        			
        			z = zAux;
        			x += variacaoX;
        			y += variacaoY;
        			
        			if (amplitudeX > 1) 
        				amplitudeX *= 0.95;
        			if (amplitudeY > 1) 
        				amplitudeY *= 0.95;
        		}
        		
        		historicoValoresDeX[i] = z;
        		historicoValoresDeY[i] = x;
        		historicoValoresDeZ[i] = y;
        		tamanhoDaBusca++;
        		
        		if (qtsVezesIgual > quantiaMaxima)
        			break;
        	}
        	
        	pontoMinimoX[0] = x;		// preenche o vetor com as coordenadas que ser�o
        	pontoMinimoY[0] = y;		// devolvidas
        	pontoMinimoZ[0] = z;
        }
}