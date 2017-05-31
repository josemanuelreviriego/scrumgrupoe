package principal.graficos;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Ventana extends JFrame {

	private static final long serialVersionUID = 5979421777239930009L;

	private String titulo;

	public Ventana(String titulo, final SuperficieDibujo sd) {
		this.titulo = titulo;

		configurarVentana(sd);
	}

	private void configurarVentana(final SuperficieDibujo sd) {
		// Pone el titulo en la ventana
		setTitle(titulo);
		// Cuando cerremos la ventana, funcione automaticamente
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		// setIconImage()
		setLayout(new BorderLayout());
		add(sd, BorderLayout.CENTER);
		// setUndecorated(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
