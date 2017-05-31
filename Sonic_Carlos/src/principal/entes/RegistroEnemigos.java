package principal.entes;

public class RegistroEnemigos {
	public static Enemigo getEnemigo(final int idEnemigo) {
		Enemigo enemigo = null;

		switch (idEnemigo) {
		case 1:
			enemigo = new Peon(idEnemigo, "Peon", 10);
			break;
		}

		return enemigo;
	}
}
