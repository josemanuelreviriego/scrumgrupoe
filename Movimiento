package movimiento;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class teclado implements KeyListener{

	private static int numeroTeclas =  120;
	private boolean[] teclas = new boolean[numeroTeclas];
	
	public boolean up;
	public boolean down;
	public boolean right;
	public boolean left;
	
	public void actualizar(){
		up = teclas[KeyEvent.VK_UP];
		down = teclas[KeyEvent.VK_DOWN];
		right = teclas[KeyEvent.VK_RIGHT];
		left = teclas[KeyEvent.VK_LEFT];
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
