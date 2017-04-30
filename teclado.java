package movimiento;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class teclado implements KeyListener{

	private static int numeroTeclas =  120;
	private boolean[] teclas = new boolean[numeroTeclas];
	
	public boolean arriba;
	public boolean abajo;
	public boolean derecha;
	public boolean izquierda;
	
	public void actualizar(){
		arriba = teclas[KeyEvent.VK_UP];
		abajo = teclas[KeyEvent.VK_DOWN];
		derecha = teclas[KeyEvent.VK_RIGHT];
		izquierda = teclas[KeyEvent.VK_LEFT];
	}
	
	// se pone KeyEvent porque le estas pasando un evento de teclado
	// y la e es de evento que es lo que se suele poner
	
	public void keyTyped(KeyEvent e) {
	//pulsar la tecla, al pulsar una tecla se ejecuta este metodo
		teclas[e.getKeyCode()] = true;
	}

	
	public void keyPressed(KeyEvent e) {
	//soltar la tecla, al soltar una tecla se ejecuta este metodo	
		teclas[e.getKeyCode()] = false;
	}

	
	public void keyReleased(KeyEvent e) {
	//las dos cosas a la vez pulsar y soltar se deja sin usar	
		
	}

}
