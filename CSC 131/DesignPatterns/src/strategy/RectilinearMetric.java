package strategy;

public class RectilinearMetric implements Metric {
	public double distance(double[] x, double[] y) {
		double result;
		int n;

		result = 0.0;
		n = Math.min(x.length, y.length);

		for (int i = 0; i < n; i++) {
			result += Math.abs(x[i] - y[i]);
		}

		return result;
	}

}