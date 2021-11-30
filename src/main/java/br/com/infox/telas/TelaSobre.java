package br.com.infox.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class TelaSobre extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	public TelaSobre() {
		setResizable(false);
		setTitle("Sobre");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProgramaParaControle = new JLabel("Sistema para controle de Ordem de Serviço");
		lblProgramaParaControle.setBounds(31, 39, 387, 15);
		contentPane.add(lblProgramaParaControle);
		
		JLabel lblDesenvolvidoPorRmulo = new JLabel("Desenvolvido por: Rômulo Carvalho");
		lblDesenvolvidoPorRmulo.setBounds(31, 66, 276, 15);
		contentPane.add(lblDesenvolvidoPorRmulo);
		
		JLabel lblSobALicena = new JLabel("Sob a licença GPL");
		lblSobALicena.setBounds(31, 93, 169, 15);
		contentPane.add(lblSobALicena);
	}

}
