package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//KeyListener interfaz para capturar teclas por teclado.
public class Teclado implements KeyListener {
	// Numero de teclas a utilizar
	private final static int numeroTeclas = 120;
	private final boolean[] teclas = new boolean[numeroTeclas];

	public boolean arriba;
	public boolean abajo;
	public boolean izquierda;
	public boolean derecha;
	public boolean ataque;

	public void actualizar() {
		// Asignamos teclas a cada variable.
		arriba = teclas[KeyEvent.VK_UP];
		abajo = teclas[KeyEvent.VK_DOWN];
		izquierda = teclas[KeyEvent.VK_LEFT];
		derecha = teclas[KeyEvent.VK_RIGHT];
	    ataque = teclas[KeyEvent.VK_A];
	}

	// al pulsar la tecla
	@Override
	public void keyPressed(KeyEvent e) {
		// Optiene el codigo de la tecla y lo pasa a true
		teclas[e.getKeyCode()] = true;
	}

	// al liberar la tecla
	@Override
	public void keyReleased(KeyEvent e) {
		// Optiene el codigo de la tecla y lo pasa a false
		teclas[e.getKeyCode()] = false;
	}

	// pulsar y soltar la tecla
	@Override
	public void keyTyped(KeyEvent e) {

	}
}
