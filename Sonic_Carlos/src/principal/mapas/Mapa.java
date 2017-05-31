package principal.mapas;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.entes.Enemigo;
import principal.entes.RegistroEnemigos;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

public class Mapa {

	private String[] partes;

	private final int ancho;
	private final int alto;

	private final Point posicionInicial;
	private final Point puntoSalida;

	private Rectangle zonaSalida;

	private String siguienteMapa;

	private final Sprite[] paleta;

	private final boolean[] colisiones;

	public final ArrayList<Rectangle> areasColision = new ArrayList<>();
	public final ArrayList<Enemigo> enemigos;

	private final int[] sprites;

	public Mapa(final String ruta) {
		String contenido = CargadorRecursos.leerArchivoTexto(ruta);

		// Divide el documento en partes, poniendo como cortes el simbolo
		// indicado
		partes = contenido.split("\\*");

		// Tamanho del mapa
		ancho = Integer.parseInt(partes[0]);
		alto = Integer.parseInt(partes[1]);

		// Hojas de sprites utilizadas
		String hojasUtilizadas = partes[2];
		String[] hojasSeparadas = hojasUtilizadas.split(",");

		// Lectura de paleta de sprites
		String paletaEntera = partes[3];
		String[] partesPaleta = paletaEntera.split("#");

		// Asignar sprites aqui

		paleta = asignarSprites(partesPaleta, hojasSeparadas);

		// Leer las colisiones para el mapa
		String colisionesEnteras = partes[4];
		colisiones = extraerColisiones(colisionesEnteras);

		// Leer la distribucion de los sprites en el mapa
		String spritesEnteros = partes[5];
		String[] cadenasSprites = spritesEnteros.split(" ");

		sprites = extraerSprites(cadenasSprites);

		// Posicion de salida
		String posicion = partes[6];
		String[] posiciones = posicion.split("-");

		posicionInicial = new Point();
		posicionInicial.x = Integer.parseInt(posiciones[0]) * Constantes.LADO_SPRITE;
		posicionInicial.y = Integer.parseInt(posiciones[1]) * Constantes.LADO_SPRITE;

		// Transicion entre mapas
		String salida = partes[7];
		String[] datosSalida = salida.split("-");

		puntoSalida = new Point();
		puntoSalida.x = Integer.parseInt(datosSalida[0]);
		puntoSalida.y = Integer.parseInt(datosSalida[1]);
		siguienteMapa = datosSalida[2];

		zonaSalida = new Rectangle();

		// Enemigos
		String informacionEnemigos = partes[9];
		enemigos = asignarEnemigos(informacionEnemigos);
	}

	private ArrayList<Enemigo> asignarEnemigos(final String informacionEnemigos) {
		ArrayList<Enemigo> enemigos = new ArrayList<>();

		String[] informacionEnemigosSeparada = informacionEnemigos.split("#");
		for (int i = 0; i < informacionEnemigosSeparada.length; i++) {
			String[] informacionEnemigoActual = informacionEnemigosSeparada[i].split(":");
			String[] coordenadas = informacionEnemigoActual[0].split(",");
			String idEnemigo = informacionEnemigoActual[1];

			Enemigo enemigo = RegistroEnemigos.getEnemigo(Integer.parseInt(idEnemigo));
			enemigo.getPosicion(Double.parseDouble(coordenadas[0]), Double.parseDouble(coordenadas[1]));
			enemigos.add(enemigo);
		}

		return enemigos;
	}

	private Sprite[] asignarSprites(final String[] partesPaleta, final String[] hojasSeparadas) {
		Sprite[] paleta = new Sprite[partesPaleta.length];

		HojaSprites hoja = new HojaSprites("/imagenes/hojasTexturas/" + hojasSeparadas[0] + ".png", 32, true);

		for (int i = 0; i < partesPaleta.length; i++) {
			String spriteTemporal = partesPaleta[i];

			String[] partesSprite = spriteTemporal.split("-");

			int indicePaleta = Integer.parseInt(partesSprite[0]);
			int indiceSpriteHoja = Integer.parseInt(partesSprite[2]);

			paleta[indicePaleta] = hoja.getSprite(indiceSpriteHoja);
		}

		return paleta;
	}

	private boolean[] extraerColisiones(final String cadenaColisiones) {
		boolean[] colisiones = new boolean[cadenaColisiones.length()];

		for (int i = 0; i < cadenaColisiones.length(); i++) {
			if (cadenaColisiones.charAt(i) == '0') {
				colisiones[i] = false;
			} else {
				colisiones[i] = true;
			}
		}

		return colisiones;
	}

	private int[] extraerSprites(final String[] cadenasSprites) {
		ArrayList<Integer> sprites = new ArrayList<Integer>();

		for (int i = 0; i < cadenasSprites.length; i++) {
			if (cadenasSprites[i].length() == 2) {
				sprites.add(Integer.parseInt(cadenasSprites[i]));
			} else {
				String uno = "";
				String dos = "";

				String error = cadenasSprites[i];

				uno += error.charAt(0);
				uno += error.charAt(1);

				dos += error.charAt(2);
				dos += error.charAt(3);

				sprites.add(Integer.parseInt(uno));
				sprites.add(Integer.parseInt(dos));
			}
		}

		int[] vectorSprites = new int[sprites.size()];

		for (int i = 0; i < sprites.size(); i++) {
			vectorSprites[i] = sprites.get(i);
		}

		return vectorSprites;
	}

	public void dibujar(Graphics g) {
		for (int y = 0; y < this.alto; y++) {
			for (int x = 0; x < this.ancho; x++) {
				BufferedImage imagen = paleta[sprites[x + y * this.ancho]].getImagen();

				int puntoX = calculo(x, ElementosPrincipales.jugador.getPosicionXInt(), Constantes.MARGEN_X);
				int puntoY = calculo(y, ElementosPrincipales.jugador.getPosicionYInt(), Constantes.MARGEN_Y);

				DibujoDebug.dibujarImagen(g, imagen, puntoX, puntoY);
			}
		}

		if (!enemigos.isEmpty()) {
			for (Enemigo enemigo : enemigos) {
				final int puntoX = (int) enemigo.getPosicionX() * Constantes.LADO_SPRITE
						- ElementosPrincipales.jugador.getPosicionXInt() + Constantes.MARGEN_X;
				final int puntoY = (int) enemigo.getPosicionY() * Constantes.LADO_SPRITE
						- ElementosPrincipales.jugador.getPosicionYInt() + Constantes.MARGEN_Y;
				enemigo.dibujar(g, puntoX, puntoY);
			}
		}
	}

	public void actualizar() {
		actualizarAreasColision();
		actualizarZonaSalida();
	}

	private void actualizarAreasColision() {
		if (!areasColision.isEmpty()) {
			areasColision.clear();
		}

		for (int y = 0; y < this.alto; y++) {
			for (int x = 0; x < this.ancho; x++) {
				int puntoX = calculo(x, ElementosPrincipales.jugador.getPosicionXInt(), Constantes.MARGEN_X);
				int puntoY = calculo(y, ElementosPrincipales.jugador.getPosicionYInt(), Constantes.MARGEN_Y);

				if (colisiones[x + y * this.ancho]) {
					final Rectangle r = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
					areasColision.add(r);
				}
			}
		}
	}

	private void actualizarZonaSalida() {
		int puntoX = calculo((int) puntoSalida.getX(), ElementosPrincipales.jugador.getPosicionXInt(),
				Constantes.MARGEN_X);
		int puntoY = calculo((int) puntoSalida.getY(), ElementosPrincipales.jugador.getPosicionYInt(),
				Constantes.MARGEN_Y);

		// Cambio de mapas
		zonaSalida = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
	}

	private int calculo(final int punto, final int posicion, final int margen) {
		return punto * Constantes.LADO_SPRITE - posicion + margen;
	}

	public Rectangle getBordes(final int posicionX, final int posicionY) {
		int x = Constantes.MARGEN_X - posicionX + ElementosPrincipales.jugador.getAncho();
		int y = Constantes.MARGEN_Y - posicionY + ElementosPrincipales.jugador.getAlto();
		int ancho = this.ancho * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getAncho() * 2;
		int alto = this.alto * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getAlto() * 2;

		return new Rectangle(x, y, ancho, alto);
	}

	public Point getPosicionInicial() {
		return posicionInicial;
	}

	public Point getPuntosalida() {
		return puntoSalida;
	}

	public String getSiguienteMapa() {
		return siguienteMapa;
	}

	public Rectangle getZonaSalida() {
		return zonaSalida;
	}
}
