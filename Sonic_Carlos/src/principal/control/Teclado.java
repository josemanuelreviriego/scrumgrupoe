package principal.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//KeyListener interfaz para capturar teclas por teclado.
public class Teclado implements KeyListener {
	public Tecla arriba = new Tecla();
	public Tecla abajo = new Tecla();
	public Tecla izquierda = new Tecla();
	public Tecla derecha = new Tecla();

	public Tecla salir = new Tecla();

	public boolean correr = false;

	// al pulsar la tecla
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			arriba.teclaPulsada();
			break;
		case KeyEvent.VK_S:
			abajo.teclaPulsada();
			break;
		case KeyEvent.VK_D:
			derecha.teclaPulsada();
			break;
		case KeyEvent.VK_A:
			izquierda.teclaPulsada();
			break;
		case KeyEvent.VK_ESCAPE:
			salir.teclaPulsada();
			break;
		case KeyEvent.VK_SHIFT:
			correr = true;
			break;
		// // Optiene el codigo de la tecla y lo pasa a true
		// teclas[e.getKeyCode()] = true;
		}
	}

	// al liberar la tecla
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			arriba.teclaLiberada();
			break;
		case KeyEvent.VK_S:
			abajo.teclaLiberada();
			break;
		case KeyEvent.VK_D:
			derecha.teclaLiberada();
			break;
		case KeyEvent.VK_A:
			izquierda.teclaLiberada();
			break;
		case KeyEvent.VK_ESCAPE:
			salir.teclaLiberada();
			break;
		case KeyEvent.VK_SHIFT:
			correr = false;
			break;
		}
		// Optiene el codigo de la tecla y lo pasa a false
		// teclas[e.getKeyCode()] = false;

	}

	// pulsar y soltar la tecla
	public void keyTyped(KeyEvent e) {

	}
}
