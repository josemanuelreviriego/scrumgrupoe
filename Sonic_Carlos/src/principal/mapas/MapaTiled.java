package principal.mapas;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.entes.Enemigo;
import principal.entes.RegistroEnemigos;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

public class MapaTiled {
	private int anchoMapaEnTiles;
	private int altoMapaEnTiles;
	private Point puntoInicial;
	private Point cambiarMapa;
	private String nuevoMapa;

	private Rectangle zonaSalida;

	private ArrayList<CapaSprites> capaSprites;
	private ArrayList<CapaColisiones> capasColisiones;

	private ArrayList<Rectangle> areasColisionOriginales;
	public ArrayList<Rectangle> areasColisionPorActualizacion;

	private Sprite[] paletaSprites;

	private ArrayList<Enemigo> enemigosMapa;

	public MapaTiled(final String ruta) {
		String contenido = CargadorRecursos.leerArchivoTexto(ruta);

		// ANCHO, ALTO
		JSONObject globalJSON = getObjetoJSON(contenido);
		anchoMapaEnTiles = getIntDesdeJSON(globalJSON, "width");
		altoMapaEnTiles = getIntDesdeJSON(globalJSON, "height");

		// PUNTO INICIAL
		JSONObject puntoInicial = getObjetoJSON(globalJSON.get("start").toString());
		this.puntoInicial = new Point(getIntDesdeJSON(puntoInicial, "x"), getIntDesdeJSON(puntoInicial, "y"));

		// CAPAS
		JSONArray capas = getArrayJSON(globalJSON.get("layers").toString());

		this.capaSprites = new ArrayList<>();
		this.capasColisiones = new ArrayList<>();

		// INICIAR CAPAS
		for (int i = 0; i < capas.size(); i++) {
			JSONObject datosCapa = getObjetoJSON(capas.get(i).toString());

			int anchoCapa = getIntDesdeJSON(datosCapa, "width");
			int altoCapa = getIntDesdeJSON(datosCapa, "height");
			int xCapa = getIntDesdeJSON(datosCapa, "x");
			int yCapa = getIntDesdeJSON(datosCapa, "y");
			String tipo = datosCapa.get("type").toString();

			switch (tipo) {
			case "tilelayer":
				JSONArray sprites = getArrayJSON(datosCapa.get("data").toString());
				int[] spritesCapa = new int[sprites.size()];
				for (int j = 0; j < sprites.size(); j++) {
					int codigoSprite = Integer.parseInt(sprites.get(j).toString());
					spritesCapa[j] = codigoSprite - 1;
				}
				this.capaSprites.add(new CapaSprites(anchoCapa, altoCapa, xCapa, yCapa, spritesCapa));
				break;
			case "objectgroup":
				JSONArray rectangulos = getArrayJSON(datosCapa.get("objects").toString());

				Rectangle[] rectangulosCapa = new Rectangle[rectangulos.size()];

				for (int j = 0; j < rectangulos.size(); j++) {
					JSONObject datosRectangulo = getObjetoJSON(rectangulos.get(j).toString());

					int x = getIntDesdeJSON(datosRectangulo, "x");
					int y = getIntDesdeJSON(datosRectangulo, "y");
					int ancho = getIntDesdeJSON(datosRectangulo, "width");
					int alto = getIntDesdeJSON(datosRectangulo, "height");

					if (x == 0)
						x = 1;
					if (y == 0)
						y = 1;
					if (ancho == 0)
						ancho = 1;
					if (alto == 0)
						alto = 1;

					Rectangle rectangulo = new Rectangle(x, y, ancho, alto);
					rectangulosCapa[j] = rectangulo;
				}
				this.capasColisiones.add(new CapaColisiones(anchoCapa, altoCapa, xCapa, yCapa, rectangulosCapa));
				break;
			}
		}

		// COMBINAR COLISIONES EN UN SOLO ARRAYLIST
		areasColisionOriginales = new ArrayList<>();
		for (int i = 0; i < capasColisiones.size(); i++) {
			Rectangle[] rectangulos = capasColisiones.get(i).getColisionables();

			for (int j = 0; j < rectangulos.length; j++) {
				areasColisionOriginales.add(rectangulos[j]);
			}
		}

		// AVERIGUAR TOTAL DE SPRITES EXIXTENTES EN TODAS LAS CAPAS
		JSONArray coleccionesSprites = getArrayJSON(globalJSON.get("tilesets").toString());
		int totalSprites = 0;
		for (int i = 0; i < coleccionesSprites.size(); i++) {
			JSONObject datosGrupo = getObjetoJSON(coleccionesSprites.get(i).toString());
			totalSprites += getIntDesdeJSON(datosGrupo, "tilecount");
		}
		paletaSprites = new Sprite[totalSprites];

		// ASIGNAR SPRITES NECESARIOS A LA PALETA A PARTIR DE LAS CAPAS
		for (int i = 0; i < coleccionesSprites.size(); i++) {
			JSONObject datosGrupo = getObjetoJSON(coleccionesSprites.get(i).toString());

			String nombreImagen = datosGrupo.get("image").toString();
			int anchoTiles = getIntDesdeJSON(datosGrupo, "tilewidth");
			int altoTiles = getIntDesdeJSON(datosGrupo, "tileheight");
			System.out.println("/imagenes/hojasTexturas/" + nombreImagen);
			HojaSprites hoja = new HojaSprites("/imagenes/hojasTexturas/" + nombreImagen, altoTiles, false);

			int primerSpriteColeccion = getIntDesdeJSON(datosGrupo, "firstgid") - 1;
			int ultimoSpritecoleccion = primerSpriteColeccion + getIntDesdeJSON(datosGrupo, "tilecount") - 1;

			for (int j = 0; j < this.capaSprites.size(); j++) {
				CapaSprites capaActual = this.capaSprites.get(j);
				int[] spritesCapa = capaActual.getArraySprites();

				for (int k = 0; k < spritesCapa.length; k++) {
					int idSpriteActual = spritesCapa[k];
					if (idSpriteActual >= primerSpriteColeccion && idSpriteActual <= ultimoSpritecoleccion) {
						if (paletaSprites[idSpriteActual] == null) {
							paletaSprites[idSpriteActual] = hoja.getSprite(idSpriteActual - primerSpriteColeccion);
						}
					}
				}
			}
		}

		// Obtener objetos del mapa
		// JSONArray coleccionObjetos =
		// getArrayJSON(globalJSON.get("objetos").toString());
		// for (int i = 0; i < coleccionObjetos.size(); i++) {
		// JSONObject datosObjeto =
		// getObjetoJSON(coleccionObjetos.get(i).toString());
		//
		// int idObjeto = getIntDesdeJSON(datosObjeto, "id");
		// int cantidadObjeto = getIntDesdeJSON(datosObjeto, "cantidad");
		// int xObjeto = getIntDesdeJSON(datosObjeto, "x");
		// int yObjeto = getIntDesdeJSON(datosObjeto, "y");
		//
		// Point posicionObjeto = new Point(xObjeto, yObjeto);
		// Objeto objeto = RegistroObjetos.obtenerObjeto(idObjeto);
		// }

		// Obtener Enemigos
		enemigosMapa = new ArrayList<>();
		JSONArray coleccionEnemigos = getArrayJSON(globalJSON.get("enemigos").toString());
		for (int i = 0; i < coleccionEnemigos.size(); i++) {
			JSONObject datosEnemigo = getObjetoJSON(coleccionEnemigos.get(i).toString());

			int idEnemigo = getIntDesdeJSON(datosEnemigo, "id");
			int xEnemigo = getIntDesdeJSON(datosEnemigo, "x");
			int yEnemigo = getIntDesdeJSON(datosEnemigo, "y");

			Point posicionEnemigo = new Point(xEnemigo, yEnemigo);
			Enemigo enemigo = RegistroEnemigos.getEnemigo(idEnemigo);
			enemigo.getPosicion(posicionEnemigo.x, posicionEnemigo.y);

			enemigosMapa.add(enemigo);
		}

		areasColisionPorActualizacion = new ArrayList<>();

		// Cambio de mapa
		JSONArray puntoCambio = getArrayJSON(globalJSON.get("map_nuevo").toString());
		for (int i = 0; i < puntoCambio.size(); i++) {
			JSONObject cambioMapa = getObjetoJSON(puntoCambio.get(i).toString());

			nuevoMapa = getStringDesdeJSON(cambioMapa, "map");
			this.cambiarMapa = new Point(getIntDesdeJSON(cambioMapa, "x"), getIntDesdeJSON(cambioMapa, "y"));
		}

		zonaSalida = new Rectangle();
	}

	public void actualizarZonaSalida() {
		int puntoX = calculo((int) cambiarMapa.getX(), ElementosPrincipales.jugador.getPosicionXInt(),
				Constantes.MARGEN_X);
		int puntoY = calculo((int) cambiarMapa.getY(), ElementosPrincipales.jugador.getPosicionYInt(),
				Constantes.MARGEN_Y);

		zonaSalida = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
	}

	private int calculo(final int punto, final int posicion, final int margen) {
		return punto - posicion + margen;
	}

	public void actualizar() {
		actualizarAreasColision();
		actualizarZonaSalida();
		actualizarEnemigos();
	}

	private void actualizarAreasColision() {
		if (!areasColisionPorActualizacion.isEmpty()) {
			areasColisionPorActualizacion.clear();
		}

		for (int i = 0; i < areasColisionOriginales.size(); i++) {
			Rectangle rInicial = areasColisionOriginales.get(i);

			int puntoX = rInicial.x - ElementosPrincipales.jugador.getPosicionXInt() + Constantes.MARGEN_X;
			int puntoY = rInicial.y - ElementosPrincipales.jugador.getPosicionYInt() + Constantes.MARGEN_Y;

			final Rectangle rFinal = new Rectangle(puntoX, puntoY, rInicial.width, rInicial.height);

			areasColisionPorActualizacion.add(rFinal);
		}
	}

	private void actualizarEnemigos() {
		if (!enemigosMapa.isEmpty()) {
			for (Enemigo enemigo : enemigosMapa) {
				enemigo.actualizar();
			}
		}
	}

	public void dibujar(Graphics g) {
		for (int i = 0; i < capaSprites.size(); i++) {
			int[] spritesCapa = capaSprites.get(i).getArraySprites();
			for (int y = 0; y < altoMapaEnTiles; y++) {
				for (int x = 0; x < anchoMapaEnTiles; x++) {
					int idSpriteActual = spritesCapa[x + y * anchoMapaEnTiles];
					if (idSpriteActual != -1) {
						int puntoX = x * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getPosicionXInt()
								+ Constantes.MARGEN_X;
						int puntoY = y * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getPosicionYInt()
								+ Constantes.MARGEN_Y;

						DibujoDebug.dibujarImagen(g, paletaSprites[idSpriteActual].getImagen(), puntoX, puntoY);
					}
				}
			}
		}
	}

	private JSONObject getObjetoJSON(final String codigoJSON) {
		JSONParser lector = new JSONParser();
		JSONObject objetoJSON = null;

		try {
			Object recuperado = lector.parse(codigoJSON);
			objetoJSON = (JSONObject) recuperado;
		} catch (ParseException e) {
			System.out.println("Posicion: " + e.getPosition());
			System.out.println(e);
		}

		return objetoJSON;
	}

	private JSONArray getArrayJSON(final String codigoJSON) {
		JSONParser lector = new JSONParser();
		JSONArray arrayJSON = null;

		try {
			Object recuperado = lector.parse(codigoJSON);
			arrayJSON = (JSONArray) recuperado;
		} catch (ParseException e) {
			System.out.println("Posicion: " + e.getPosition());
			System.out.println(e);
		}

		return arrayJSON;
	}

	private int getIntDesdeJSON(final JSONObject objetoJSON, final String clave) {
		System.out.println(objetoJSON.get(clave).toString());
		return Integer.parseInt(objetoJSON.get(clave).toString());
	}

	private String getStringDesdeJSON(final JSONObject objetoJSON, final String clave) {
		return objetoJSON.get(clave).toString();
	}

	public Point getPosicionInicial() {
		return puntoInicial;
	}

	public Point getPosicionCambio() {
		return cambiarMapa;
	}

	public String getSiguienteMapa() {
		return nuevoMapa;
	}

	public Rectangle getZonaSalida() {
		return zonaSalida;
	}

	public Rectangle getBordes(final int posicionX, final int posicionY) {
		int x = Constantes.MARGEN_X - posicionX + ElementosPrincipales.jugador.getAncho();
		int y = Constantes.MARGEN_Y - posicionY + ElementosPrincipales.jugador.getAlto();
		int ancho = this.anchoMapaEnTiles * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getAncho() * 2;
		int alto = this.altoMapaEnTiles * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getAlto() * 2;

		return new Rectangle(x, y, ancho, alto);
	}
}
