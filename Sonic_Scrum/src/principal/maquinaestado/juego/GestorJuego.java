// Ejecutara el juego
package principal.maquinaestado.juego;

import java.awt.Color;
import java.awt.Graphics;

import principal.Constantes;
import principal.entes.Jugador;
import principal.mapas.Mapa;
import principal.maquinaestado.EstadoJuego;

public class GestorJuego implements EstadoJuego {

	Mapa mapa;
	Jugador jugador;

	public GestorJuego() {
		iniciarMapa(Constantes.RUTA_MAPA);
		iniciarJugador();

	}

	private void recargarJuego() {
		final String ruta = "/mapas/" + mapa.getSiguienteMapa();

		iniciarMapa(ruta);
		iniciarJugador();
	}

	private void iniciarMapa(final String ruta) {
		mapa = new Mapa(ruta);
	}

	private void iniciarJugador() {
		jugador = new Jugador(mapa);
	}

	public void actualizar() {
		if (jugador.get_LIMITE_ARRIBA().intersects(mapa.getZonaSalida())) {
			recargarJuego();
		}

		jugador.actualizar();
		mapa.actualizar((int) jugador.getPosicionX(), (int) jugador.getPosicionY());
	}

	public void dibujar(Graphics g) {
		mapa.dibujar(g, (int) jugador.getPosicionX(), (int) jugador.getPosicionY());
		jugador.dibujar(g);
		g.setColor(Color.blue);
		g.drawString("X = " + jugador.getPosicionX(), 20, 20);
		g.drawString("Y = " + jugador.getPosicionY(), 20, 30);

		g.fillRect((int) mapa.getZonaSalida().x, (int) mapa.getZonaSalida().y, (int) mapa.getZonaSalida().width,
				(int) mapa.getZonaSalida().height);

		g.drawString("Siguiente mapa: " + mapa.getSiguienteMapa(), 20, 40);
		g.drawString(
				"Coordenadas de salida X = " + mapa.getPuntosalida().getX() + " Y = " + mapa.getPuntosalida().getY(),
				20, 50);
	}

}
