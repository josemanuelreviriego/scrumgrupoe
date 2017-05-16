// Esta Clase extiende la Clase mapa

package mapa;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import mapa.cuadro.Cuadro;

public class MapaCargado extends Mapa {

	private int[] pixeles;

	// Sobreescribe el metodo CargarMapa de la clase Mapa
	// A partir de un mapa de colores rellena el Array CuadrosCatalogo
	protected void cargarMapa(String ruta) {
		try {
			// Lee el mapa de colores y lo guarda en imagen
			BufferedImage imagen = ImageIO.read(MapaCargado.class.getResource(ruta));

			ancho = imagen.getWidth();
			alto = imagen.getHeight();

			cuadrosCatalogo = new Cuadro[ancho * alto];
			pixeles = new int[ancho * alto];

			// Guarda las secuencias de colores en el array pixeles
			imagen.getRGB(0, 0, ancho, alto, pixeles, 0, ancho);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public MapaCargado(String ruta) {
		super(ruta);
	}

	// Sobreescribe el metodo GenerarMapa de la Clase Mapa
	// Dependiendo del codigo RGB guardado en el array pixeles, Guarda en el
	// array cuadros Catalogo un sprite del mapa
	protected void generarMapa() {
		for (int i = 0; i < pixeles.length; i++) {
			// mira los colores en el mapa y lo sustituye por el sprite elegido.
			switch (pixeles[i]) {
				case 0xff13efd2 :
					cuadrosCatalogo[i] = Cuadro.LAVA;
					continue;
				case 0xfff015f3 :
					cuadrosCatalogo[i] = Cuadro.PAREDO;
					continue;
				case 0xffe80404 :
					cuadrosCatalogo[i] = Cuadro.ESQUINANO;
					continue;
				case 0xffefae13 :
					cuadrosCatalogo[i] = Cuadro.PAREDN;
					continue;
				case 0xff18ef13 :
					cuadrosCatalogo[i] = Cuadro.CENTRO;
					continue;
				case 0xff1380ef :
					cuadrosCatalogo[i] = Cuadro.ESQUINANE;
					continue;
				case 0xff1313ef :
					cuadrosCatalogo[i] = Cuadro.ESQUINASE;
					continue;
				case 0xffb313ef :
					cuadrosCatalogo[i] = Cuadro.ESQUINASO;
					continue;
				case 0xff6613ef :
					cuadrosCatalogo[i] = Cuadro.PAREDS;
					continue;
				case 0xffe7ef13 :
					cuadrosCatalogo[i] = Cuadro.PAREDE;
					continue;
				default :
					cuadrosCatalogo[i] = Cuadro.VACIO;

			}
		}
	}
}
