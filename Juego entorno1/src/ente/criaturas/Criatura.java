package ente.criaturas;

import ente.Ente;
import graficos.Sprite;

public class Criatura extends Ente {
	protected Sprite sprite;
	protected char direccion = 'n'; // 'n' es norte 'o' oeste 's' sur 'e' este 
	protected boolean enMovimiento = false;
	
	public void actualizar()
	{
		
	}
	
	public void mostrar(){
		
	}
	
	public void mover(int desplazamientoX, int desplazamientoY){
		if (desplazamientoX > 0) {
			direccion = 'e';
		}
		if (desplazamientoX > 0){
			direccion = 'o';
		}
		if(desplazamientoY > 0){
			direccion = 's';
		}
		if(desplazamientoY < 0){
			direccion = 'n';
		}
		
		if(!estaEliminado()){
			modificarPosicionX(desplazamientoX);
			modificarPosicionY(desplazamientoY);
		}
	}
		
		private boolean enColision() {
			return false;
		}

		public Sprite obtenSprite(){ 
			return sprite;
			
		}
		
		
		 
	}
	

