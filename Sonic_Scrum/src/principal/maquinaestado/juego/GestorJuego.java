// Ejecutara el juego
package principal.maquinaestado.juego;

import java.awt.Color;
import java.awt.Graphics;

import principal.Constantes;
import principal.entes.Jugador;
import principal.mapas.Mapa;
import principal.maquinaestado.EstadoJuego;

public class GestorJuego implements EstadoJuego {

	Mapa mapa = new Mapa(Constantes.RUTA_MAPA);
	Jugador jugador = new Jugador(76, 65, mapa);

	public void actualizar() {
		jugador.actualizar();
		mapa.actualizar((int) jugador.getPosicionX(), (int) jugador.getPosicionY());
	}

	public void dibujar(Graphics g) {
		mapa.dibujar(g, (int) jugador.getPosicionX(), (int) jugador.getPosicionY());
		jugador.dibujar(g);
		g.setColor(Color.blue);
		g.drawString("X = " + jugador.getPosicionX(), 20, 20);
		g.drawString("Y = " + jugador.getPosicionY(), 20, 30);
	}

}
