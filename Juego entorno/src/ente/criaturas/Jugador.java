package ente.criaturas;

	// Aqui importamos lo de josep
	import control.Teclado; 


	public class Jugador extends Criatura {
		
		private Teclado teclado;
		
		// 1º Constructor con esto tendremos una clase para usar para moverse
		
		
		public Jugador(Teclado teclado){
		
		this.teclado = teclado;	
			
		}
		
		/* Asignamos un metodo para movernos
		Aqui podremos pasarle al constructor las cordenadas en las que querramos que nuestro jugador
		se cree en el mapa, de forma que aparezca en un lugar determinado */
		
		
		
		public Jugador(Teclado teclado, int posicionX, int posicionY){
			this.teclado = teclado;	  // en juego/ente cambiar de privado a protected sino dara error 	
			this.x = posicionX;
			this.y = posicionY;
		
	}
		
		// Se encagara de actualizar datos y variables

		public void actualizar() {   
			
			int desplazamientoX = 0;
			int desplazamientoY = 0;

		//Necesitamos saber si el jugador se ha movido y donde se ha movido, aqui entra la clase teclado		
			
			if (teclado.arriba){
				desplazamientoY--;
			}
			if (teclado.abajo){
				desplazamientoY++;
			}
			
			if (teclado.izquierda){
				desplazamientoX--;
			}
			
			if (teclado.derecha){
				desplazamientoX++;
			}
			
			/* esta funcion hace que si no hemos pulsado ninguna tecla para movernos, es decir que "Y" y "X" es 0.
			nos movremos si "x" o "y" no son "0" */
			
			if (desplazamientoX != 0 || desplazamientoY != 0){  
				
				mover(desplazamientoX, desplazamientoY);
				
		
			
		}
			
	}

}
