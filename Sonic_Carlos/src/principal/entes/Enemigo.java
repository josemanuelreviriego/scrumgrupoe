package principal.entes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.herramientas.CalculadoraDistancia;
import principal.herramientas.DibujoDebug;

public class Enemigo {
	private int idEnemigo;

	private double posicionX;
	private double posicionY;

	private String nombre;
	private int vidaMaxima;
	private float vidaActual;

	// private Nodo siguienteNodo;

	public Enemigo(final int idEnemigo, final String nombre, final int vidaMaxima) {
		this.idEnemigo = idEnemigo;
		this.nombre = nombre;
		this.vidaMaxima = vidaMaxima;
		this.vidaActual = vidaMaxima;

		this.posicionX = 0;
		this.posicionY = 0;
	}

	public void actualizar() {
		// moverHaciaSiguienteNodo();
	}

	/*
	 * private void moverHaciaSiguienteNodo() { if (siguienteNodo == null) {
	 * return; }
	 * 
	 * double velocidad = 0.5;
	 * 
	 * int xSiguienteNodo = siguienteNodo.getPosicion().x *
	 * Constantes.LADO_SPRITE; int ySiguienteNodo =
	 * siguienteNodo.getPosicion().y * Constantes.LADO_SPRITE;
	 * 
	 * if (posicionX < xSiguienteNodo) { posicionX += velocidad; }
	 * 
	 * if (posicionX > xSiguienteNodo) { posicionX -= velocidad; }
	 * 
	 * if (posicionY < ySiguienteNodo) { posicionY += velocidad; }
	 * 
	 * if (posicionY > ySiguienteNodo) { posicionY -= velocidad; } }
	 */

	public void dibujar(final Graphics g, final int puntoX, final int puntoY) {
		if (vidaActual <= 0) {
			return;
		}

		dibujarBarraVida(g, puntoX, puntoY);
		DibujoDebug.dibujarRectanguloContorno(g, getArea());
		dibujarDistancia(g, puntoX, puntoY);

	}

	private void dibujarBarraVida(final Graphics g, final int puntoX, final int puntoY) {
		g.setColor(Color.red);
		DibujoDebug.dibujarRectanguloRelleno(g, puntoX, puntoY, Constantes.LADO_SPRITE * (int) vidaActual / vidaMaxima,
				2);
	}

	private void dibujarDistancia(final Graphics g, final int puntoX, final int puntoY) {
		Point puntoJugador = new Point(ElementosPrincipales.jugador.getPosicionXInt() / Constantes.LADO_SPRITE,
				ElementosPrincipales.jugador.getPosicionYInt() / Constantes.LADO_SPRITE);

		Point puntoEnemigo = new Point((int) posicionX, (int) posicionY);

		Double distancia = CalculadoraDistancia.getdistanciaEntrePuntos(puntoJugador, puntoEnemigo);

		DibujoDebug.dibujarString(g, String.format("%.2f", distancia), puntoX, puntoY - 8);
	}

	public void getPosicion(final double posicionX, final double posicionY) {
		this.posicionX = posicionX;
		this.posicionY = posicionY;
	}

	public double getPosicionX() {
		return posicionX;
	}

	public double getPosicionY() {
		return posicionY;
	}

	public int getIdEnemigo() {
		return idEnemigo;
	}

	public float getVidaActual() {
		return vidaActual;
	}

	public Rectangle getArea() {
		final int puntoX = (int) posicionX * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getPosicionXInt()
				+ Constantes.MARGEN_X;
		final int puntoY = (int) posicionY * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getPosicionYInt()
				+ Constantes.MARGEN_Y;
		return new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
	}

	public Rectangle getAreaPosicional() {
		return new Rectangle((int) posicionX, (int) posicionY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
	}

	/*
	 * public void setSiguienteNodo(Nodo nodo) { siguienteNodo = nodo; }
	 * 
	 * public Nodo getSiguienteNodo() { return siguienteNodo; }
	 */
}
