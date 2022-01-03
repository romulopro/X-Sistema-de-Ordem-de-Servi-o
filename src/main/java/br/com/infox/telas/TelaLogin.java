package br.com.infox.telas;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.infox.dal.ModuloConexao;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class TelaLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6300866051527523340L;
	private JPanel contentPane;
	private static final int COL_TABELA_SQL_NOME_COMPLETO_USUARIO = 2;
	private static final int COL_TABELA_SQL_PERFIL_USER = 6;
	private JTextField textFieldUsuario;
	private JPasswordField passwordFieldSenha;
	private static JLabel lblConectado;
	static Optional<Connection> conexao;
	PreparedStatement pst = null;
	ResultSet rs = null;

	/**
	 * Launch the application.
	 */
	
	public void logar() {
		String sql = "SELECT * from tbusuarios where login=? and senha=?";
		try {
			String usuario = textFieldUsuario.getText();
			pst = conexao.get().prepareStatement(sql);
			pst.setString(1, usuario);
			String captura = new String(passwordFieldSenha.getPassword());
			pst.setString(2, captura);
			rs = pst.executeQuery();
			if(rs.next()) {
				String perfil = rs.getString(COL_TABELA_SQL_PERFIL_USER);
				String nomeUsuario = rs.getString(COL_TABELA_SQL_NOME_COMPLETO_USUARIO);
				TelaPrincipal principal = new TelaPrincipal(nomeUsuario, perfil);
				principal.setVisible(true);
				this.dispose();
				conexao.get().close();
			}else {
				JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválido");
			}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public TelaLogin() {
		
		doLoginWindowFrame();
		doJLabels();
		doUserAndPassTxtFields();
		doLogonButton();
		
	}
	private void doLoginWindowFrame() {
		setPreferredSize(new Dimension(380, 190));
		setTitle("Tela de Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	private void doLogonButton() {
		JButton btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logar();
			}
		});
		btnLogar.setBounds(160, 145, 117, 25);
		contentPane.add(btnLogar);
	}
	private void doUserAndPassTxtFields() {
		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(138, 56, 139, 19);
		contentPane.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		passwordFieldSenha = new JPasswordField();
		passwordFieldSenha.setBounds(138, 96, 139, 19);
		contentPane.add(passwordFieldSenha);
	}
	private void doJLabels() {
		JLabel lblUsuario = new JLabel("Usuário");
		lblUsuario.setBounds(50, 60, 70, 15);
		contentPane.add(lblUsuario);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(50, 98, 70, 15);
		contentPane.add(lblSenha);
		
		lblConectado = new JLabel("");
		lblConectado.setBounds(50, 177, 86, 59);
		contentPane.add(lblConectado);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin();
					frame.setVisible(true);
					conexao = ModuloConexao.conector();
					if(conexao.isPresent()) {
						lblConectado.setIcon(new ImageIcon(TelaLogin.class.getResource("/br/com/infox/icones/dbok.png")));
					}else {
						lblConectado.setIcon(new ImageIcon(TelaLogin.class.getResource("/br/com/infox/icones/dberror.png")));
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
