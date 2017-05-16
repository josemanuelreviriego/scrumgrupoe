package ente.criaturas;

	import graficos.Pantalla;
import graficos.Sprite; 
	import control.Teclado;
	

	public class Jugador extends Criatura {
		
		private Teclado teclado;

		private int animacion;
		
		// 1� Constructor con esto tendremos una clase para usar para moverse
		
		
		public Jugador(Teclado teclado, Sprite sprite){
		this.teclado = teclado;	
		this.sprite = sprite;
		
			
		}
		
		/* Asignamos un metodo para movernos
		Aqui podremos pasarle al constructor las cordenadas en las que querramos que nuestro jugador
		se cree en el mapa, de forma que aparezca en un lugar determinado */
		
		
		
		public Jugador(Teclado teclado, Sprite sprite, int posicionX, int posicionY){
			this.teclado = teclado;	  // en juego/ente cambiar de privado a protected sino dara error
			this.sprite = sprite;
			this.x = posicionX;
			this.y = posicionY;
		}
		// Se encagara de actualizar datos y variables

		public void actualizar() {   
			
			int desplazamientoX = 0;
			int desplazamientoY = 0;
			
			if (animacion < 32767){
				animacion++;
			}else{
			animacion = 0;
			}
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
				enMovimiento = true;
			}else{
				enMovimiento = false;
			}
				if (direccion == 'n'){
					sprite = Sprite.ARRIBA0;
					if (enMovimiento){
						if (animacion % 30 > 15){
							sprite= Sprite.ARRIBA1;
						}else{
							sprite = Sprite.ARRIBA2;	
						}
					}	
				}		
				if (direccion == 's'){
					sprite = Sprite.ABAJO0;
					if (enMovimiento){
						if (animacion % 30 > 15){
							sprite= Sprite.ABAJO1;
						}else{
							sprite = Sprite.ABAJO2;	
						}
					}	
				}
				
				if (direccion == 'o'){
					sprite = Sprite.IZQUIERDA0;
					if (enMovimiento){
						if (animacion % 30 > 15){
							sprite= Sprite.IZQUIERDA1;
						}else{
							sprite = Sprite.IZQUIERDA2;	
						}
					}	
				}
				
				if (direccion == 'n'){
					sprite = Sprite.DERECHA0;
					if (enMovimiento){
						if (animacion % 30 > 15){
							sprite= Sprite.DERECHA1;
						}else{
							sprite = Sprite.DERECHA2;	
						}
					}	
				}
		}
			
	

		public void mostrar(Pantalla pantalla)  {
				pantalla.mostrarJugador(x, y, this);
		}
}
