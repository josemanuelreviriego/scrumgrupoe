package graficos;

public final class Sprite {
	// Tamaño del sprite cuadrado
	private final int lado;

	private int x;
	private int y;

	// Array que guarda la coleccion de colores del sprite
	public int[] pixeles;
	private HojaSprites hoja;

	// Colección de sprites
	// Sprite color = negro
	public static final Sprite VACIO = new Sprite(32, 0);
	public static final Sprite LAVA = new Sprite(32, 3, 0, 0, HojaSprites.FondoLava);
	public static final Sprite ESQUINANO = new Sprite(32, 0, 0, 0, HojaSprites.FondoLava);
	public static final Sprite ESQUINANE = new Sprite(32, 0, 0, 1, HojaSprites.FondoLava);
	public static final Sprite ESQUINASO = new Sprite(32, 0, 0, 2, HojaSprites.FondoLava);
	public static final Sprite ESQUINASE = new Sprite(32, 0, 0, 3, HojaSprites.FondoLava);
	public static final Sprite PAREDN = new Sprite(32, 1, 0, 0, HojaSprites.FondoLava);
	public static final Sprite PAREDS = new Sprite(32, 1, 0, 2, HojaSprites.FondoLava);
	public static final Sprite PAREDO = new Sprite(32, 0, 1, 0, HojaSprites.FondoLava);
	public static final Sprite PAREDE = new Sprite(32, 0, 1, 1, HojaSprites.FondoLava);
	public static final Sprite CENTRO = new Sprite(32, 1, 1, 0, HojaSprites.FondoLava);
	// Fin de la colección

	// Constructor para Sprites normales
	public Sprite(final int lado, final int columna, final int fila, final int version, final HojaSprites hoja) {
		this.lado = lado;

		// Da tamaño al array Sprite de lado por lado segun se le mande
		pixeles = new int[lado * lado];

		this.x = columna * lado;
		this.y = fila * lado;
		this.hoja = hoja;

		// recorre la imagen
		cargaManipulada(version);
	}

	// Rellena de un color donde no se representen Sprites
	public Sprite(final int lado, final int color) {
		this.lado = lado;
		pixeles = new int[lado * lado];

		for (int i = 0; i < pixeles.length; i++) {
			pixeles[i] = color;
		}
	}

	public int getlado() {
		return lado;
	}

	// Almacena el Sprite temporalmente para manipularlo
	private int[] iniciarPixelesTemporales() {
		int[] pixelesTemporales = new int[lado * lado];
		for (int y = 0; y < lado; y++) {
			for (int x = 0; x < lado; x++) {
				// copia a un array de pixeles los colores de la imagen.
				pixelesTemporales[x + y * lado] = hoja.pixeles[(x + this.x) + (y + this.y) * hoja.getAncho()];
			}
		}
		return pixelesTemporales;
	}

	// Segun la version carga el Sprite de una manera u otra
	private void cargaManipulada(int version) {
		int[] pixelesTemporales = iniciarPixelesTemporales();

		switch (version) {
			case 1 :
				invertirX(pixelesTemporales);
				break;
			case 2 :
				invertirY(pixelesTemporales);
				break;
			case 3 :
				invertirXY(pixelesTemporales);
				break;
			case 4 :
				rotar90I(pixelesTemporales);
				break;
			case 5 :
				rotar90D(pixelesTemporales);
				break;
			case 6 :
				rotarI90InvertidoY(pixelesTemporales);
				break;
			case 7 :
				rotarD90InvertidoY(pixelesTemporales);
				break;
			default :
				cargaNormal();
		}
	}

	// Carga ek sprite con la X invertida
	private void invertirX(int[] pixelesTemporales) {
		int i = 0;
		for (int y = 0; y < lado; y++) {
			for (int x = lado - 1; x > -1; x--) {
				pixeles[i] = pixelesTemporales[x + y * lado];
				i++;
			}
		}
	}

	// Carga el Sprite invirtiendo la Y
	private void invertirY(int[] pixelesTemporales) {
		int i = 0;
		for (int y = lado - 1; y > -1; y--) {
			for (int x = 0; x < lado; x++) {
				pixeles[i] = pixelesTemporales[x + y * lado];
				i++;
			}
		}
	}

	// 3 Carga el Sprite invirtiendo XY
	private void invertirXY(int[] pixelesTemporales) {
		for (int i = 0; i < pixeles.length; i++) {
			pixeles[i] = pixelesTemporales[pixelesTemporales.length - 1 - i];
		}
	}

	// 4 Carga el Sprite rotandolo 90º a la Izquierda
	private void rotar90I(int[] pixelesTemporales) {
		int i = 0;
		for (int x = lado - 1; x > -1; x--) {
			for (int y = 0; y < lado; y++) {
				pixeles[i] = pixelesTemporales[x + y * lado];
				i++;
			}
		}
	}

	// 5 Carga el Sprite rotandolo 90º a la Derecha
	private void rotar90D(int[] pixelesTemporales) {
		int i = 0;
		for (int x = 0; x < lado; x++) {
			for (int y = lado - 1; y > -1; y--) {
				pixeles[i] = pixelesTemporales[x + y * lado];
				i++;
			}
		}
	}

	// 6 Carga el Sprite rotandolo 90º a la Izquierda e invierte la Y
	private void rotarI90InvertidoY(int[] pixelesTemporales) {
		int i = 0;
		for (int x = 0; x < lado; x++) {
			for (int y = 0; y < lado; y++) {
				pixeles[i] = pixelesTemporales[x + y * lado];
				i++;
			}
		}
	}

	// 7 Carga el Sprite rotandolo 90º a la Derecha e invierte la Y
	private void rotarD90InvertidoY(int[] pixelesTemporales) {
		int i = 0;
		for (int x = lado - 1; x > -1; x--) {
			for (int y = lado - 1; y > -1; y--) {
				pixeles[i] = pixelesTemporales[x + y * lado];
				i++;
			}
		}
	}

	// Carga el Sprite de forma normal
	private void cargaNormal() {
		for (int y = 0; y < lado; y++) {
			for (int x = 0; x < lado; x++) {
				// copia a un array de pixeles los colores de la imagen.
				pixeles[x + y * lado] = hoja.pixeles[(x + this.x) + (y + this.y) * hoja.getAncho()];
			}
		}
	}
}
