package graficos;

import ente.criaturas.Jugador;
import mapa.cuadro.Cuadro;

public class Pantalla {
	private final int ancho;
	private final int alto;

	// mediran como me muevo por el mapa
	private int diferenciaX;
	private int diferenciaY;

	// Coleccion de todos los pixeles que representan la pantalla
	public final int[] pixeles;

	public Pantalla(final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;

		pixeles = new int[ancho * alto];
	}

	// actualiza de izquierda a derecha y de arriba abajo,
	public void mostrarCuadro(int compensacionX, int compensacionY, Cuadro cuadro) {
		compensacionX -= diferenciaX;
		compensacionY -= diferenciaY;

		for (int y = 0; y < cuadro.sprite.getlado(); y++) {
			int posicionY = y + compensacionY;
			for (int x = 0; x < cuadro.sprite.getlado(); x++) {
				int posicionX = x + compensacionX;
				// Si se sale de la pantalla, rompe el bucle
				if (posicionX < -cuadro.sprite.getlado() || posicionX >= ancho || posicionY < 0 || posicionY >= alto) {
					break;
				}

				if (posicionX < 0) {
					posicionX = 0;
				}

				// Copia los pixeles que representan nuestro cuadro.
				pixeles[posicionX + posicionY * ancho] = cuadro.sprite.pixeles[x + y * cuadro.sprite.getlado()];
			}
		}
	}
	
	
	public void mostrarJugador(int compensacionX, int compensacionY, Jugador jugador){
		compensacionX -= diferenciaX;
		compensacionY -= diferenciaY;

		for (int y = 0; y < jugador.obtenSprite().getlado(); y++) {
			int posicionY = y + compensacionY;
			for (int x = 0; x < jugador.obtenSprite().getlado(); x++) {
				int posicionX = x + compensacionX;
				// Si se sale de la pantalla, rompe el bucle
				if (posicionX < -jugador.obtenSprite().getlado() || posicionX >= ancho || posicionY < 0 || posicionY >= alto) {
					break;
				}

				if (posicionX < 0) {
					posicionX = 0;
				}

				// Copia los pixeles que representan nuestro cuadro.
				pixeles[posicionX + posicionY * ancho] = jugador.obtenSprite().pixeles[x + y 
				        * jugador.obtenSprite().getlado()];
				
				/* Para obtener el color actual del sprite 	*/
				int colorPixelJugador = jugador.obtenSprite().pixeles[x+y*jugador.obtenSprite().getlado()];
				if (colorPixelJugador != 0xff00ff ) {
					pixeles[posicionX + posicionY* ancho] = colorPixelJugador;
				
				}
				
			}
			
		}
			
		}		
	
	
	public void estableceDiferencia(final int diferenciaX, final int diferenciaY) {
		this.diferenciaX = diferenciaX;
		this.diferenciaY = diferenciaY;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

}
