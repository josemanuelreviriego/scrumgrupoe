

// Aqui importamos lo de josep
import control.teclado; 


public class Jugador {
	
	private Teclado teclado;
	
	// 1º Constrcutor con esto tendremos una clase para usar para moverse
	
	
	public Jugador(Teclado teclado){
	
	this.teclado = teclado;	
		
	}
	
	/* Asginamos un metodo para movernos
	Aqui podremos pasarle al constructor las cordenadas en las que querramos que nuestro jugador
	se cree en el mapa, de forma que aparezca en un lugar determinado */
	
	
	
	public Jugador(Teclado teclado, int poscionX, int posicionY)
		this.teclado = teclado;	  // en juego/ente cambiar de privado a protected sino dara error 	
		this.x = posicionX;
		this.y = posicionY;
	
}
	// Se encagara de actualizar datos y variables

	public void actualizar() {   
		
		int desplazamientox = 0;
		int desplazamientoy = 0;

	//Necesitamos saber si el jugador se ha movido y donde se ha movido, aqui entra la clase teclado		
		
		if (teclado.arriba){
			desplazamientoY--;
		}
		if (teclado.abajo){
			desplazamientoY++;
		}
		
		if (teclado.izquierda){
			desplazamientox--;
		}
		
		if (teclado.derecha){
			desplazamientox++;
		}
		
		/* esta funcion hace que si no hemos pulsado ninguna tecla para movernos, es decir que "Y" y "X" es 0.
		nos movremos si "x" o "y" no son "0" */
		
		if (desplazamientoX != 0 || desplazamientoY != 0){  
			
			mover(desplazamientoX, desplazamientoY);
			
	
		
	}
		
}