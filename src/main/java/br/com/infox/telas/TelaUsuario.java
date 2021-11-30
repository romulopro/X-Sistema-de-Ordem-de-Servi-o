package br.com.infox.telas;

import javax.swing.JInternalFrame;
import java.awt.Dimension;

public class TelaUsuario extends JInternalFrame {



	/**
	 * Create the frame.
	 */
	public TelaUsuario() {
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setMaximumSize(new Dimension(640, 480));
		
		setSize(new Dimension(640, 480));
		setPreferredSize(new Dimension(640, 480));
		setMinimumSize(new Dimension(640, 480));
		setTitle("Usuário");
		//setBounds(100, 100, 450, 300);

	}

}
