package mapa;

import graficos.Pantalla;
import mapa.cuadro.Cuadro;

// abstract Impide instanciar la clase
public abstract class Mapa {
	protected int ancho;
	protected int alto;

	// almacena los tipos de cuadros generados aleatoriamente
	protected int[] cuadros;

	// Guarda el mapa a mostrar en orden.
	protected Cuadro[] cuadrosCatalogo;

	// Coge una imagen y la Carga como un mapa
	public Mapa(String ruta) {
		cargarMapa(ruta);
		generarMapa();
	}

	protected void generarMapa() {

	}

	protected void cargarMapa(String ruta) {

	}

	public void mostrar(final int compensacionX, final int compensacionY, Pantalla pantalla) {

		pantalla.estableceDiferencia(compensacionX, compensacionY);

		int oeste = compensacionX >> 5;
		int este = (compensacionX + pantalla.getAncho() + Cuadro.LADO) >> 5;
		int norte = compensacionY >> 5;
		int sur = (compensacionY + pantalla.getAlto() + Cuadro.LADO) >> 5;

		// recorre todo el vector de tiles
		for (int y = norte; y < sur; y++) {
			for (int x = oeste; x < este; x++) {
				// obtenCuadro(x, y).mostrar(x, y, pantalla);
				if (x < 0 || y < 0 || x >= ancho || y >= alto) {
					Cuadro.VACIO.mostrar(x, y, pantalla);
				} else {
					cuadrosCatalogo[x + y * ancho].mostrar(x, y, pantalla);
				}
			}
		}
	}

}
