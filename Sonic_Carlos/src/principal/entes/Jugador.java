package principal.entes;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.control.GestorControles;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;

public class Jugador {
	private double posicionX;
	private double posicionY;

	private final static int N = 3;
	private final static int E = 1;
	private final static int S = 0;
	private final static int W = 2;

	private int direccion;

	private double velocidad;

	private boolean enMovimiento;

	private HojaSprites hsN;
	private HojaSprites hsC;

	private BufferedImage imagenActual;

	private final int ANCHO_JUGADOR = 16;
	private final int ALTO_JUGADOR = 16;

	private final Rectangle LIMITE_ARRIBA = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_JUGADOR / 2,
			Constantes.CENTRO_VENTANA_Y, ANCHO_JUGADOR, 1);
	private final Rectangle LIMITE_ABAJO = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_JUGADOR / 2,
			Constantes.CENTRO_VENTANA_Y + ALTO_JUGADOR, ANCHO_JUGADOR, 1);
	private final Rectangle LIMITE_IZQUIERDA = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_JUGADOR / 2,
			Constantes.CENTRO_VENTANA_Y, 1, ALTO_JUGADOR);
	private final Rectangle LIMITE_DERECHA = new Rectangle(Constantes.CENTRO_VENTANA_X + ANCHO_JUGADOR / 2,
			Constantes.CENTRO_VENTANA_Y, 1, ALTO_JUGADOR);

	private int animacion;
	private int estado;

	public Jugador() {
		posicionX = ElementosPrincipales.mapa.getPosicionInicial().getX();
		posicionY = ElementosPrincipales.mapa.getPosicionInicial().getY();

		direccion = S;

		enMovimiento = false;

		hsN = new HojaSprites(Constantes.RUTA_SONIC, Constantes.LADO_SPRITE, false);
		hsC = new HojaSprites(Constantes.RUTA_SONIC_CORRIENDO, Constantes.LADO_SPRITE, false);

		imagenActual = hsN.getSprite(0).getImagen();

		animacion = 0;
		estado = 0;
	}

	public void actualizar() {
		cambiarAnimacionEstado(1, 2);
		enMovimiento = false;
		determinarDireccion();
		animar();
	}

	// Animacion de sprites
	private void cambiarAnimacionEstado(final int PR, final int SE) {
		if (animacion < 40) {
			animacion++;
		} else {
			animacion = 0;
		}

		if (animacion < 20) {
			estado = PR;
		} else {
			estado = SE;
		}
	}

	private void determinarDireccion() {
		final int velocidadX = evaluarVelocidadX();
		final int velocidadY = evaluarVelocidadY();

		if (velocidadX == 0 && velocidadY == 0) {
			return;
		}

		if ((velocidadX != 0 && velocidadY == 0) || (velocidadX == 0 && velocidadY != 0)) {
			mover(velocidadX, velocidadY);
		} else {
			// izqueirda y arriba
			if (velocidadX == -1 && velocidadY == -1) {
				if (GestorControles.teclado.izquierda.getUltimaPulsacion() > GestorControles.teclado.arriba
						.getUltimaPulsacion()) {
					mover(velocidadX, 0);
				} else {
					mover(0, velocidadY);
				}
			}
			// izquierda y abajo
			if (velocidadX == -1 && velocidadY == 1) {
				if (GestorControles.teclado.izquierda.getUltimaPulsacion() > GestorControles.teclado.abajo
						.getUltimaPulsacion()) {
					mover(velocidadX, 0);
				} else {
					mover(0, velocidadY);
				}
			}
			// derecha y arriba
			if (velocidadX == 1 && velocidadY == -1) {
				if (GestorControles.teclado.derecha.getUltimaPulsacion() > GestorControles.teclado.arriba
						.getUltimaPulsacion()) {
					mover(velocidadX, 0);
				} else {
					mover(0, velocidadY);
				}
			}
			// derecha y abajo
			if (velocidadX == 1 && velocidadY == 1) {
				if (GestorControles.teclado.derecha.getUltimaPulsacion() > GestorControles.teclado.abajo
						.getUltimaPulsacion()) {
					mover(velocidadX, 0);
				} else {
					mover(0, velocidadY);
				}
			}
		}
	}

	private int evaluarVelocidadX() {
		int velocidadX = 0;

		if (GestorControles.teclado.izquierda.getPulsada() && !GestorControles.teclado.derecha.getPulsada()) {
			velocidadX = -1;
		} else if (GestorControles.teclado.derecha.getPulsada() && !GestorControles.teclado.izquierda.getPulsada()) {
			velocidadX = 1;
		}

		return velocidadX;
	}

	private int evaluarVelocidadY() {
		int velocidadY = 0;

		if (GestorControles.teclado.arriba.getPulsada() && !GestorControles.teclado.abajo.getPulsada()) {
			velocidadY = -1;
		} else if (GestorControles.teclado.abajo.getPulsada() && !GestorControles.teclado.arriba.getPulsada()) {
			velocidadY = 1;
		}

		return velocidadY;
	}

	private void cambiarVelocidad() {
		if (GestorControles.teclado.correr) {
			if (velocidad < 8) {
				velocidad += 0.05;
			}
		} else {
			if (velocidad > 1) {
				velocidad -= 0.1;
			} else {
				velocidad = 1;
			}
		}
	}

	private void mover(final int velocidadX, final int velocidadY) {
		enMovimiento = true;

		cambiarVelocidad();

		cambiarDireccion(velocidadX, velocidadY);

		if (!fueraMapa(velocidadX, velocidadY)) {
			if (velocidadX == -1 && !enColisionIzquierda(velocidadX)) {
				posicionX += velocidadX * velocidad;
			}
			if (velocidadX == 1 && !enColisionDerecha(velocidadX)) {
				posicionX += velocidadX * velocidad;
			}
			if (velocidadY == -1 && !enColisionArriba(velocidadY)) {
				posicionY += velocidadY * velocidad;
			}
			if (velocidadY == 1 && !enColisionAbajo(velocidadY)) {
				posicionY += velocidadY * velocidad;
			}
		}
	}

	private boolean enColisionArriba(int velocidadY) {
		for (int r = 0; r < ElementosPrincipales.mapa.areasColisionPorActualizacion.size(); r++) {
			final Rectangle area = ElementosPrincipales.mapa.areasColisionPorActualizacion.get(r);

			int origenX = area.x;
			int origenY = area.y + velocidadY * (int) velocidad + 3 * (int) velocidad;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

			if (LIMITE_ARRIBA.intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}

	private boolean enColisionAbajo(int velocidadY) {
		for (int r = 0; r < ElementosPrincipales.mapa.areasColisionPorActualizacion.size(); r++) {
			final Rectangle area = ElementosPrincipales.mapa.areasColisionPorActualizacion.get(r);

			int origenX = area.x;
			int origenY = area.y + velocidadY * (int) velocidad - 3 * (int) velocidad;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

			if (LIMITE_ABAJO.intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}

	private boolean enColisionIzquierda(int velocidadX) {
		for (int r = 0; r < ElementosPrincipales.mapa.areasColisionPorActualizacion.size(); r++) {
			final Rectangle area = ElementosPrincipales.mapa.areasColisionPorActualizacion.get(r);

			int origenX = area.x + velocidadX * (int) velocidad + 3 * (int) velocidad;
			int origenY = area.y;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

			if (LIMITE_IZQUIERDA.intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}

	private boolean enColisionDerecha(int velocidadX) {
		for (int r = 0; r < ElementosPrincipales.mapa.areasColisionPorActualizacion.size(); r++) {
			final Rectangle area = ElementosPrincipales.mapa.areasColisionPorActualizacion.get(r);

			int origenX = area.x + velocidadX * (int) velocidad - 3 * (int) velocidad;
			int origenY = area.y;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

			if (LIMITE_DERECHA.intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}

	private boolean fueraMapa(final int velocidadX, final int velocidadY) {
		int posicionFuturaX = (int) posicionX + velocidadX * (int) velocidad;
		int posicionFuturaY = (int) posicionY + velocidadY * (int) velocidad;

		final Rectangle bordesMapa = ElementosPrincipales.mapa.getBordes(posicionFuturaX, posicionFuturaY);

		final boolean fuera;

		if (LIMITE_ARRIBA.intersects(bordesMapa) || LIMITE_ABAJO.intersects(bordesMapa)
				|| LIMITE_IZQUIERDA.intersects(bordesMapa) || LIMITE_DERECHA.intersects(bordesMapa)) {
			fuera = false;
		} else {
			fuera = true;
		}

		return fuera;
	}

	private void cambiarDireccion(int velocidadX, int velocidadY) {
		if (velocidadX == 1) {
			direccion = E;
		} else if (velocidadX == -1) {
			direccion = W;
		}
		if (velocidadY == 1) {
			direccion = S;
		} else if (velocidadY == -1) {
			direccion = N;
		}
	}

	private void animar() {
		if (!enMovimiento) {
			cambiarAnimacionEstado(0, 3);
		}

		// Animacion
		if (velocidad > 4) {
			imagenActual = hsC.getSprite(direccion, estado).getImagen();
		} else {
			imagenActual = hsN.getSprite(direccion, estado).getImagen();
		}
	}

	public void dibujar(Graphics g) {
		final int centroX = Constantes.ANCHO_VENTANA / 2 - Constantes.LADO_SPRITE / 2;
		final int centroY = Constantes.ALTO_VENTANA / 2 - Constantes.LADO_SPRITE / 2;

		DibujoDebug.dibujarImagen(g, imagenActual, centroX, centroY);
	}

	public void setPosicionX(double posicionX) {
		this.posicionX = posicionX;
	}

	public double getPosicionX() {
		return posicionX;
	}

	public void setPosicionY(double posicionY) {
		this.posicionY = posicionY;
	}

	public double getPosicionY() {
		return posicionY;
	}

	public int getPosicionXInt() {
		return (int) posicionX;
	}

	public int getPosicionYInt() {
		return (int) posicionY;
	}

	public int getAncho() {
		return ANCHO_JUGADOR;
	}

	public int getAlto() {
		return ALTO_JUGADOR;
	}

	public Rectangle get_LIMITE_ARRIBA() {
		return LIMITE_ARRIBA;
	}
}