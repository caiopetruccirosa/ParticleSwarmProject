package particleswarm;

/**
 * @author Caio Petrucci dos Santos Rosa
 *
 */

public class Problema {
	
	public double f(double x) { // função que será otimizada
		double y = -x*x + 5*x - 6; 
		return y;
	}
}
