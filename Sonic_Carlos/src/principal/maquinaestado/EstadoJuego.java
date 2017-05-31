package principal.maquinaestado;

import java.awt.Graphics;

// Preguntar esto
// Se utilizan para agrupar clases.
// Clase especial sin atributos y metodos concretos, siempre son publicas y abstractas
public interface EstadoJuego {
	void actualizar();

	void dibujar(final Graphics g);
}
