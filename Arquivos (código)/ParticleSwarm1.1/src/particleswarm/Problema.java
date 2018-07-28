package particleswarm;

/**
 * @author Caio Petrucci dos Santos Rosa
 *
 */

public class Problema {
	
	public double f(double[] x) { // função que será otimizada
		double y = 0.0;
		for (int i = 0; i < x.length; i++)
			y = y + x[i]*x[i];
		return y;
	}
}
