// Esta clase Recoge los Cuadrados 32*32 de la hoja fondo 
package graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HojaSprites {
	private final int ancho;
	private final int alto;
	public final int[] pixeles;

	// Colección de hojas de sprites
	public static HojaSprites FondoLava = new HojaSprites("/mapa/Fondo.png", 320, 320);
	public static HojaSprites Sonic = new HojaSprites("/texturas/Sonic_mov.png", 90, 120);

	// Fin de la colección

	public HojaSprites(final String ruta, final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;

		// Crea un array por imagen cargada
		pixeles = new int[ancho * alto];

		// Creamos una imagen y asignamos una ruta
		BufferedImage imagen;

		// Las operaciones arriesgada, se rodea con try catch, por si no existe
		try {
			imagen = ImageIO.read(HojaSprites.class.getResource(ruta));

			// vuelca los valores de la imagen en el array pixeles
			imagen.getRGB(0, 0, ancho, alto, pixeles, 0, ancho);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
}