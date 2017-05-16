package juego;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import control.Teclado;
import graficos.Pantalla;
import mapa.Mapa;
import mapa.MapaCargado;

//Canvas Superficie especializada de java para dibujar rapido y sin muchos requisitos
//Runnable Interfaz, encargada de ejecutar diferentes thread(Dividir el trabajo en varias partes)
public class Main extends Canvas implements Runnable {

	// Identificador de serie, Por si la clase cambia.
	private static final long serialVersionUID = 1L;

	private static final String NOMBRE = "Sonic SCRUM";

	// Tamaño de la ventana que se mostrara
	private static final int ANCHO = 800;
	private static final int ALTO = 600;

	// Esta en funcionamiento o no
	// volatile impide la utilizacion de forma simultanea de 2 thread
	private static volatile boolean enFuncionamiento = false;

	private static int x = 0;
	private static int y = 0;

	private static JFrame ventana;
	private static Thread thread;
	private static Pantalla pantalla;
	private static Teclado teclado;

	private static Mapa mapa;

	// Creamos una nueva imagen en buffer en blanco
	private static BufferedImage imagen = new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_RGB);

	// DataBufferInt devuelve un array de ints para la imagen
	// imagen get raster, devuelve la secuencia de pixeles de la imagen
	// getdatabuffer devuelve en formato que pueda usar el baffer
	private static int[] pixeles = ((DataBufferInt) imagen.getRaster().getDataBuffer()).getData();

	private Main() {
		setPreferredSize(new Dimension(ANCHO, ALTO));

		pantalla = new Pantalla(ANCHO, ALTO);

		mapa = new MapaCargado("/mapa/fondo2.png");

		teclado = new Teclado();
		addKeyListener(teclado);

		ventana = new JFrame(NOMBRE);
		// Cerrar la aplicaccion al cerrar la ventana
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Bloquear cambio de tamaños
		ventana.setResizable(false);
		// Diseño de ventana
		ventana.setLayout(new BorderLayout());
		// Añadir canvas en centro de ventana
		ventana.add(this, BorderLayout.CENTER);
		// reajusta el contenido de la ventana al definido
		ventana.pack();
		// fijar la ventana en el centro del escritorio
		ventana.setLocationRelativeTo(null);
		// hacer visible la ventana
		ventana.setVisible(true);
	}

	public static void main(String[] args) {
		Main juego = new Main();
		juego.iniciar();
	}

	// Inicia el Juego
	private synchronized void iniciar() {
		enFuncionamiento = true;
		// Indicarle la clase desde la que se inicia y poner nombre al hilo de
		// ejecución.
		thread = new Thread(this, "Graficos");
		// inicia el thread(hilo)
		thread.start();
	}

	private void actualizar() {
		// Llama a la funcion actualizar de la clase teclado y asigna teclas
		teclado.actualizar();

		if (teclado.arriba) {
			y--;
		}

		if (teclado.abajo) {
			y++;
		}

		if (teclado.izquierda) {
			x--;
		}

		if (teclado.derecha) {
			x++;
		}
	}

	private void mostrar() {
		// Espacio de memoria que guarda los recursos
		BufferStrategy estrategia = getBufferStrategy();

		if (estrategia == null) {
			// Cola de imagenes a cargar
			createBufferStrategy(3);
			return;
		}

		mapa.mostrar(x, y, pantalla);

		// copia el bucle for de pantalla al de main
		System.arraycopy(pantalla.pixeles, 0, pixeles, 0, pixeles.length);

		// Objeto encargado de dibujar las cosas
		Graphics g = estrategia.getDrawGraphics();

		g.drawImage(imagen, 0, 0, getWidth(), getHeight(), null);
		// borra la memoria de g
		g.dispose();
		estrategia.show();
	}

	@Override
	public void run() {
		final int NS_POR_SEGUNDOS = 1000000000;
		final byte APS_OBJETIVO = 60;
		final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDOS / APS_OBJETIVO;

		long referenciaActualizacion = System.nanoTime();
		long referenciaContador = System.nanoTime();

		double tiempoTranscurrido;
		// Delta cantidad de tiempo transcurrido entre actualizaciones.
		double delta = 0;

		// Iniciar directamente el foco en la ventana
		requestFocus();
		while (enFuncionamiento) {
			final long inicioBucle = System.nanoTime();

			tiempoTranscurrido = inicioBucle - referenciaActualizacion;
			referenciaActualizacion = inicioBucle;

			delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;

			while (delta >= 1) {
				actualizar();
				delta--;
			}

			mostrar();
		}
	}
}
