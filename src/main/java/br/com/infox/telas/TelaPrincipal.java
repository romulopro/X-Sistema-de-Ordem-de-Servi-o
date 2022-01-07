package br.com.infox.telas;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;
import java.util.Optional;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import br.com.infox.dal.ModuloConexao;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblData;
	private JMenu mnRelatorio;
	private JMenuItem mntmUsuarios;
	private JMenuBar menuBar;
	private JMenu mnCadastro;
	private JMenu mnAjuda;
	private JMenu mnOpcoes;
	private JDesktopPane telaPrincipalDesktopPane;
	private Optional<Connection> conexao;
	public TelaPrincipal(String nomeUsuario, String perfilUsuario) {

		montaFrameDaJanela();
		montaBarraDeMenu();
		montaSubItensMenuCadastro();
		montaSubItensMenuRelatorio();
		montaSubItensMenuAjuda();
		montaSubItensMenuOpcoes();
		montaJanela();
		montaFrameDaJanelaInterna();
		montaLabels(nomeUsuario, perfilUsuario);
	}

	private void montaFrameDaJanelaInterna() {
		telaPrincipalDesktopPane = new JDesktopPane();
		telaPrincipalDesktopPane.setMaximumSize(new Dimension(640, 480));
		telaPrincipalDesktopPane.setMinimumSize(new Dimension(640, 480));
		telaPrincipalDesktopPane.setSize(new Dimension(640, 480));
		telaPrincipalDesktopPane.setBounds(12, 12, 640, 480);
		contentPane.add(telaPrincipalDesktopPane);
	}

	private void montaJanela() {
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(800, 600));
		contentPane.setSize(new Dimension(800, 600));
		contentPane.setMinimumSize(new Dimension(800, 600));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	private void montaLabels(String nomeUsuario, String perfilUsuario) {
		JLabel lblUsuario = new JLabel(nomeUsuario);
		lblUsuario.setBounds(670, 51, 130, 15);
		contentPane.add(lblUsuario);
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
		lblData = new JLabel(formatador.format(data));
		lblData.setBounds(670, 99, 112, 15);
		contentPane.add(lblData);

		if (perfilUsuario.equals("admin")) {
			mnRelatorio.setEnabled(true);
			mntmUsuarios.setEnabled(true);

		} else {
			mnRelatorio.setEnabled(false);
			mntmUsuarios.setEnabled(false);
		}
	}

	private void montaFrameDaJanela() {
		setBounds(new Rectangle(0, 0, 800, 600));

		setSize(new Dimension(800, 600));
		setTitle("X - Sistema para controle de OS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
	}

	private void montaSubItensMenuOpcoes() {
		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção",
						JOptionPane.YES_NO_OPTION);
				if (sair == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		mntmSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		mnOpcoes.add(mntmSair);
	}

	private void montaSubItensMenuAjuda() {
		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mntmSobre.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.ALT_DOWN_MASK));
		mnAjuda.add(mntmSobre);
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaSobre telaSobre = new TelaSobre();
				telaSobre.setVisible(true);
			}
		});
		
	}

	private void montaSubItensMenuCadastro() {
		JMenuItem mntmCliente = new JMenuItem("Cliente");
		mntmCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaCliente telaCliente = new TelaCliente();
				telaPrincipalDesktopPane.add(telaCliente);
				telaCliente.setVisible(true);
				
			}
		});
		mntmCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
		mnCadastro.add(mntmCliente);

		JMenuItem mntmOs = new JMenuItem("OS");
		mntmOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaOS telaOS = new TelaOS();
				telaOS.setVisible(true);
				telaPrincipalDesktopPane.add(telaOS);
			}
		});
		mntmOs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK));
		mnCadastro.add(mntmOs);

		


		
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
	}
	
	private void montaSubItensMenuRelatorio() {
		
		JMenuItem mntmClientes = new JMenuItem("Clientes");
		mntmClientes.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent arg0) {
				int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão deste relatório?", "Atenção", JOptionPane.YES_NO_OPTION);
				if(isOpcaoNaoEscolhida(confirma)) {
					return;
				}
				conexao = ModuloConexao.conector();
				try {
					JasperPrint print = JasperFillManager.fillReport("/home/note/JaspersoftWorkspace/MyReports/cliente.jasper",null, conexao.get());
					JasperViewer.viewReport(print, false);
				} catch (JRException e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}


		});
		mntmClientes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_DOWN_MASK));
		mnRelatorio.add(mntmClientes);
		
		JMenuItem mntmServicos = new JMenuItem("Serviços");
		mntmServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão deste relatório?", "Atenção", JOptionPane.YES_NO_OPTION);
				if(isOpcaoNaoEscolhida(confirma)) {
					return;
				}
				conexao = ModuloConexao.conector();
				try {
					JasperPrint print = JasperFillManager.fillReport("/home/note/JaspersoftWorkspace/MyReports/servicos.jasper",null, conexao.get());
					JasperViewer.viewReport(print, false);
				} catch (JRException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		mntmServicos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK));
		mnRelatorio.add(mntmServicos);
	}
	
	private boolean isOpcaoNaoEscolhida(int confirma) {
		return confirma == JOptionPane.NO_OPTION;
	}

	private void montaBarraDeMenu() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		mnCadastro = new JMenu("Cadastro");
		menuBar.add(mnCadastro);
		mnRelatorio = new JMenu("Relatório");
		menuBar.add(mnRelatorio);
		mnOpcoes = new JMenu("Opções");
		menuBar.add(mnOpcoes);
		mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);
		
		
	}
}
