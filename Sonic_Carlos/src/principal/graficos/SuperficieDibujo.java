package principal.graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import principal.Constantes;
import principal.control.GestorControles;
import principal.herramientas.DibujoDebug;
import principal.maquinaestado.GestorEstados;

public class SuperficieDibujo extends Canvas {

	private static final long serialVersionUID = -6227038142688953660L;

	private int ancho;
	private int alto;

	public SuperficieDibujo(final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;

		// No intenta forzar el repintado automatico de canvas
		setIgnoreRepaint(true);
		setPreferredSize(new Dimension(ancho, alto));
		addKeyListener(GestorControles.teclado);
		setFocusable(true);
		requestFocus();
	}

	public void dibujar(final GestorEstados ge) {
		BufferStrategy buffer = getBufferStrategy();

		if (buffer == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = buffer.getDrawGraphics();

		DibujoDebug.reiniciarContadorObjeto();

		DibujoDebug.dibujarRectanguloRelleno(g, 0, 0, ancho, alto, Color.black);

		ge.dibujar(g);

		// DibujoDebug.dibujarString(g, "FPS: " + GestorPrincipal.this.getAps(),
		// 20, 20);
		DibujoDebug.dibujarString(g, "OPF: " + DibujoDebug.getContadorObjeto(), 20, 20);
		DibujoDebug.dibujarString(g, "FPS: " + Constantes.FPS, 20, 30);
		DibujoDebug.dibujarString(g, "APS: " + Constantes.APS, 20, 40);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();

		buffer.show();
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
}
