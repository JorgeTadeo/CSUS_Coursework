package strategy;

import java.awt.*;
import java.awt.image.*;
import java.lang.Math;

public class Posterizer {
	private double[] x, y;
	private Metric metric;

	private static final int[] BLACK = { 0, 0, 0 };
	private static final int[] WHITE = { 255, 255, 255 };

	public Posterizer() {
		x = new double[3];
		y = new double[3];
	}

	private double distance(int[] a, int[] b) {
		double result;

		for (int i = 0; i < 3; i++) {
			x[i] = a[i];
			y[i] = b[i];
		}

		result = Double.POSITIVE_INFINITY;
		if (metric != null)
			result = metric.distance(x, y);

		return result;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}

	public void toBlackAndWhite(BufferedImage image) {
		ColorModel colorModel;
		double blackDistance, whiteDistance;
		int height, packedPixel, packedBlack, packedWhite, width;
		int[] pixel;

		pixel = new int[3];

		height = image.getHeight();
		width = image.getWidth();

		colorModel = image.getColorModel();

		packedBlack = colorModel.getDataElement(BLACK, 0);
		packedWhite = colorModel.getDataElement(WHITE, 0);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				packedPixel = image.getRGB(x, y);
				colorModel.getComponents(packedPixel, pixel, 0);

				blackDistance = distance(pixel, BLACK);
				whiteDistance = distance(pixel, WHITE);

				if (blackDistance < whiteDistance)
					image.setRGB(x, y, 0x00000000);
				else
					image.setRGB(x, y, 0xFFFFFFFF);
			}
		}
	}

}
