package principal.maquinaestado;

import java.awt.Graphics;

import principal.maquinaestado.juego.GestorJuego;

// Mecanismo que controla en que punto del juego estamos... 
public class GestorEstados {
	private EstadoJuego[] estados;
	private EstadoJuego estadoActual;

	public GestorEstados() {
		iniciarEstados();
		iniciarEstadosActual();
	}

	private void iniciarEstados() {
		estados = new EstadoJuego[1];
		estados[0] = new GestorJuego();
		// Añadir e iniciar los demás estados a medida que se creen
	}

	private void iniciarEstadosActual() {
		estadoActual = estados[0];
	}

	public void actualizar() {
		estadoActual.actualizar();
	}

	public void dibujar(final Graphics g) {
		estadoActual.dibujar(g);
	}

	public void setEstadoActual(final int nuevoEstado) {
		estadoActual = estados[nuevoEstado];
	}

	public EstadoJuego getEstadoActual() {
		return estadoActual;
	}
}
