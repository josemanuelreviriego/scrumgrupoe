// Esta clase Relaciona los Cuadros de HojaSprites con Cuadros nuevos
package mapa.cuadro;

import graficos.Pantalla;
import graficos.Sprite;

public class Cuadro {
	public int x;
	public int y;

	public Sprite sprite;

	public static final int LADO = 32;

	// Colección de hojas de sprites
	public static final Cuadro VACIO = new Cuadro(Sprite.VACIO);
	public static final Cuadro LAVA = new Cuadro(Sprite.LAVA);
	public static final Cuadro CENTRO = new Cuadro(Sprite.CENTRO);
	public static final Cuadro ESQUINANO = new Cuadro(Sprite.ESQUINANO);
	public static final Cuadro ESQUINANE = new Cuadro(Sprite.ESQUINANE);
	public static final Cuadro ESQUINASE = new Cuadro(Sprite.ESQUINASE);
	public static final Cuadro ESQUINASO = new Cuadro(Sprite.ESQUINASO);
	public static final Cuadro PAREDN = new Cuadro(Sprite.PAREDN);
	public static final Cuadro PAREDS = new Cuadro(Sprite.PAREDS);
	public static final Cuadro PAREDO = new Cuadro(Sprite.PAREDO);
	public static final Cuadro PAREDE = new Cuadro(Sprite.PAREDE);
	// Fin de la colección

	public Cuadro(Sprite sprite) {
		this.sprite = sprite;
	}

	public void mostrar(int x, int y, Pantalla pantalla) {
		pantalla.mostrarCuadro(x << 5, y << 5, this);
	}
}
