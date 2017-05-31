package principal.herramientas;

import java.awt.Point;

public class CalculadoraDistancia {
	public static double getdistanciaEntrePuntos(final Point p1, final Point p2) {
		return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2)); // Obtener
																									// raiz
																									// cuadrada
	}
}
