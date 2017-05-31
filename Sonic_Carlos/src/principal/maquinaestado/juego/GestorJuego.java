// Ejecutara el juego
package principal.maquinaestado.juego;

import java.awt.Color;
import java.awt.Graphics;

import principal.ElementosPrincipales;
import principal.herramientas.DibujoDebug;
import principal.mapas.MapaTiled;
import principal.maquinaestado.EstadoJuego;

public class GestorJuego implements EstadoJuego {
	public GestorJuego() {
	}

	private void recargarJuego() {
		final String ruta = "/mapas/" + ElementosPrincipales.mapa.getSiguienteMapa();
		ElementosPrincipales.mapa = new MapaTiled(ruta);
	}

	public void actualizar() {
		if (ElementosPrincipales.jugador.get_LIMITE_ARRIBA().intersects(ElementosPrincipales.mapa.getZonaSalida())) {
			recargarJuego();
		}

		ElementosPrincipales.jugador.actualizar();
		ElementosPrincipales.mapa.actualizar();
	}

	public void dibujar(Graphics g) {
		ElementosPrincipales.mapa.dibujar(g);
		ElementosPrincipales.jugador.dibujar(g);

		DibujoDebug.dibujarString(g, "Salida X = " + ElementosPrincipales.mapa.getPosicionCambio().getX(), 20, 70,
				Color.red);
		DibujoDebug.dibujarString(g, "Salida Y = " + ElementosPrincipales.mapa.getPosicionCambio().getY(), 20, 80,
				Color.red);
		DibujoDebug.dibujarString(g, "X = " + ElementosPrincipales.jugador.getPosicionXInt(), 20, 50, Color.red);
		DibujoDebug.dibujarString(g, "Y = " + ElementosPrincipales.jugador.getPosicionYInt(), 20, 60, Color.red);
	}

}
