package principal.entes;

import java.awt.Graphics;

import principal.Constantes;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;

public class Peon extends Enemigo {

	private static HojaSprites hojaPeon;

	public Peon(int idEnemigo, String nombre, int vidaMaxima) {
		super(idEnemigo, nombre, vidaMaxima);

		if (hojaPeon == null) {
			hojaPeon = new HojaSprites(Constantes.RUTA_SHADOW, Constantes.LADO_SPRITE, false);
		}
	}

	public void dibujar(final Graphics g, final int puntoX, final int puntoY) {
		DibujoDebug.dibujarImagen(g, hojaPeon.getSprite(0).getImagen(), puntoX, puntoY);
		super.dibujar(g, puntoX, puntoY);
	}

}
