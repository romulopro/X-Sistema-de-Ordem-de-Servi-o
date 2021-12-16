package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JLabel;

public class TelaCliente extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCliente frame = new TelaCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaCliente() {
		setPreferredSize(new Dimension(640, 480));
		setNormalBounds(new Rectangle(0, 0, 640, 480));
		setMinimumSize(new Dimension(640, 480));
		setMaximumSize(new Dimension(640, 480));
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblCliNome = new JLabel("*Nome");
		lblCliNome.setBounds(12, 30, 70, 15);
		getContentPane().add(lblCliNome);
		
		JLabel lblCliEndereco = new JLabel("Endereço");
		lblCliEndereco.setBounds(12, 57, 70, 15);
		getContentPane().add(lblCliEndereco);
		
		JLabel lblCliTelefone = new JLabel("*Telefone");
		lblCliTelefone.setBounds(12, 84, 85, 15);
		getContentPane().add(lblCliTelefone);
		
		JLabel lblCliEmail = new JLabel("E-mail");
		lblCliEmail.setBounds(12, 111, 85, 15);
		getContentPane().add(lblCliEmail);

	}
}
