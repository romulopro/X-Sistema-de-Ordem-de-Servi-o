package br.com.infox.telas;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.synth.ColorType;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JDesktopPane;
import java.awt.Rectangle;
import javax.swing.JLabel;

import java.text.Collator;
import java.text.DateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	private JLabel lblData;
	private JMenu mnRelatorio;
	private JMenuItem mntmUsuarios;


	/**
	 * Create the frame.
	 * @param usuario 
	 * @param perfil 
	 */
	public TelaPrincipal(String nomeUsuario, String perfilUsuario) {
		
		setBounds(new Rectangle(0, 0, 800, 600));
		
		setSize(new Dimension(800, 600));
		setTitle("X - Sistema para controle de OS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnCadastro = new JMenu("Cadastro");
		menuBar.add(mnCadastro);
		
		JMenuItem mntmCliente = new JMenuItem("Cliente");
		mntmCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
		mnCadastro.add(mntmCliente);
		
		JMenuItem mntmOs = new JMenuItem("OS");
		mntmOs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK));
		mnCadastro.add(mntmOs);
		
		
		
		
		
		mnRelatorio = new JMenu("Relatório");
		menuBar.add(mnRelatorio);
		
		
		
		JMenuItem mntmServicos = new JMenuItem("Serviços");
		mntmServicos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK));
		mnRelatorio.add(mntmServicos);
		
		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);
		
		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaSobre telaSobre = new TelaSobre();
				telaSobre.setVisible(true);
			}
		});
		mntmSobre.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.ALT_DOWN_MASK));
		mnAjuda.add(mntmSobre);
		
		JMenu mnOpcoes = new JMenu("Opções");
		menuBar.add(mnOpcoes);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?","Atenção",JOptionPane.YES_NO_OPTION);
				if (sair == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		mntmSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		mnOpcoes.add(mntmSair);
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(800, 600));
		contentPane.setSize(new Dimension(800, 600));
		contentPane.setMinimumSize(new Dimension(800, 600));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JDesktopPane telaPrincipalDesktopPane = new JDesktopPane();
		telaPrincipalDesktopPane.setMaximumSize(new Dimension(640, 480));
		telaPrincipalDesktopPane.setMinimumSize(new Dimension(640, 480));
		telaPrincipalDesktopPane.setSize(new Dimension(640, 480));
		telaPrincipalDesktopPane.setBounds(12, 12, 640, 480);
		contentPane.add(telaPrincipalDesktopPane);
		
		mntmUsuarios = new JMenuItem("Usuários");
		mntmUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaUsuario telaUsuario = new TelaUsuario();
				telaPrincipalDesktopPane.add(telaUsuario);
				telaUsuario.setVisible(true);
			}
		});
		mntmUsuarios.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK));
		mnCadastro.add(mntmUsuarios);
		
		JLabel lblUsuario = new JLabel(nomeUsuario);
		lblUsuario.setBounds(670, 51, 130, 15);
		contentPane.add(lblUsuario);
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
		lblData = new JLabel(formatador.format(data));
		lblData.setBounds(670, 99, 112, 15);
		contentPane.add(lblData);
		
		if(perfilUsuario.equals("admin")) {
			mnRelatorio.setEnabled(true);
			mntmUsuarios.setEnabled(true);
			
		}else {
			mnRelatorio.setEnabled(false);
			mntmUsuarios.setEnabled(false);
		} 
	}
}
