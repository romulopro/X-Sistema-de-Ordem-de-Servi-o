package br.com.infox.telas;

import javax.swing.JInternalFrame;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.com.infox.dal.ModuloConexao;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class TelaUsuario extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFUsuId;
	private JTextField textFUsuNome;
	private JTextField textFUsuFone;
	private JTextField textFUsuLogin;
	private JTextField textFUsuSenha;
	private JComboBox<String> cBoxUsuPerfil;
	private static Connection conexao = null;
	private PreparedStatement pst = null;
	private ResultSet rs;

	/**
	 * Create the frame.
	 */
	public TelaUsuario() {
		initComponents();
		conexao = ModuloConexao.conector();
		
	}
	
	private void consultar(){
		String sql = "SELECT * FROM tbusuarios WHERE iduser=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, textFUsuId.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				textFUsuNome.setText(rs.getString(2));
				textFUsuFone.setText(rs.getString(3));
				textFUsuLogin.setText(rs.getString(4));
				textFUsuSenha.setText(rs.getString(5));
				cBoxUsuPerfil.setSelectedItem(rs.getString(6));
			} else {
				JOptionPane.showMessageDialog(null, "Usuário não cadastrado");
				limpaTodosOsCamposExcetoId();
			}
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		
	}
	
	private void adicionar() {
		int adicionado = 0;
		String query = "INSERT INTO tbusuarios (iduser, usuario, fone, login, senha, perfil)" + 
	"values(?,?,?,?,?,?)";
		if(ha1ouMaisCamposObrigatoriosVazios()) {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			return;
		}
		try {
			pst = conexao.prepareStatement(query);
			pst.setString(1, textFUsuId.getText());
			pst.setString(2, textFUsuNome.getText());
			pst.setString(3, textFUsuFone.getText());
			pst.setString(4, textFUsuLogin.getText());
			pst.setString(5, textFUsuSenha.getText());
			pst.setString(6, cBoxUsuPerfil.getSelectedItem().toString());
			adicionado  = pst.executeUpdate();
			if(adicionado > 0) {
				JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso");
				limpaTodosOsCamposExcetoId();
				textFUsuId.setText(null);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return;
	}
	private void atualizarDadosUsuario() {
		String query = "UPDATE tbusuarios SET usuario=?, fone=?, login=?, senha=?, perfil=? WHERE iduser=?";
		int alterado = 0;
		if(ha1ouMaisCamposObrigatoriosVazios()) {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			return;
		}
		try {
			pst=  conexao.prepareStatement(query);
			pst.setString(1, textFUsuNome.getText());
			pst.setString(2, textFUsuFone.getText());
			pst.setString(3, textFUsuLogin.getText());
			pst.setString(4, textFUsuSenha.getText());
			pst.setString(5, cBoxUsuPerfil.getSelectedItem().toString());
			pst.setString(6, textFUsuId.getText());
			alterado = pst.executeUpdate();
			if(alterado > 0) {
				JOptionPane.showMessageDialog(null, "Usuário alterado com sucesso");
				limpaTodosOsCamposExcetoId();
				textFUsuId.setText(null);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}

	private void deletarUsuario(){
		int confirma = 0; 
		int removido = 0;
		String query = "DELETE FROM tbusuarios where iduser=?";
		if(isNumeric(textFUsuId.getText()) == false){
			JOptionPane.showMessageDialog(null, "Digite um id de usuário válido");
			return;
		}
		confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuário?", "Atenção", JOptionPane.YES_NO_OPTION);
		if(confirma != JOptionPane.YES_OPTION){
			return;
		}
		
		try {
			pst = conexao.prepareStatement(query);
			pst.setString(1, textFUsuId.getText());
			removido = pst.executeUpdate();
			if (removido > 0){
				JOptionPane.showMessageDialog(null, "Usuário removido com sucesso");
				limpaTodosOsCamposExcetoId();
				textFUsuId.setText(null);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	private void limpaTodosOsCamposExcetoId() {
		textFUsuNome.setText(null);
		textFUsuFone.setText(null);
		textFUsuLogin.setText(null);
		textFUsuSenha.setText(null);
		cBoxUsuPerfil.setSelectedItem("user");
	}
	
	private boolean ha1ouMaisCamposObrigatoriosVazios() {
		return textFUsuId.getText().isEmpty() 
				|| textFUsuNome.getText().isEmpty()
				|| textFUsuLogin.getText().isEmpty()
				|| textFUsuSenha.getText().isEmpty();
	}
	private boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private void initComponents() {
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setMaximumSize(new Dimension(640, 480));
		
		setSize(new Dimension(640, 480));
		setPreferredSize(new Dimension(640, 480));
		setMinimumSize(new Dimension(640, 480));
		getContentPane().setLayout(null);
		
		JLabel lblId = new JLabel("* id");
		lblId.setBounds(30, 26, 42, 15);
		getContentPane().add(lblId);
		
		JLabel lblNome = new JLabel("* Nome");
		lblNome.setBounds(89, 26, 70, 15);
		getContentPane().add(lblNome);
		
		JLabel lblFone = new JLabel("Fone");
		lblFone.setBounds(30, 86, 42, 15);
		getContentPane().add(lblFone);
		
		JLabel lblLogin = new JLabel("* Login");
		lblLogin.setBounds(30, 146, 70, 15);
		getContentPane().add(lblLogin);
		
		JLabel lblSenha = new JLabel("* Senha");
		lblSenha.setBounds(226, 146, 70, 15);
		getContentPane().add(lblSenha);
		
		JLabel lblPerfilDeUsurio = new JLabel("* Perfil de Usuário");
		lblPerfilDeUsurio.setBounds(30, 206, 170, 15);
		getContentPane().add(lblPerfilDeUsurio);
		
		textFUsuId = new JTextField();
		textFUsuId.setBounds(30, 46, 47, 19);
		getContentPane().add(textFUsuId);
		textFUsuId.setColumns(10);
		
		textFUsuNome = new JTextField();
		textFUsuNome.setBounds(88, 46, 333, 19);
		getContentPane().add(textFUsuNome);
		textFUsuNome.setColumns(10);
		
		textFUsuFone = new JTextField();
		textFUsuFone.setBounds(30, 106, 184, 19);
		getContentPane().add(textFUsuFone);
		textFUsuFone.setColumns(10);
		
		textFUsuLogin = new JTextField();
		textFUsuLogin.setBounds(30, 166, 184, 19);
		getContentPane().add(textFUsuLogin);
		textFUsuLogin.setColumns(10);
		
		textFUsuSenha = new JTextField();
		textFUsuSenha.setBounds(226, 166, 184, 19);
		getContentPane().add(textFUsuSenha);
		textFUsuSenha.setColumns(10);
		
		cBoxUsuPerfil = new JComboBox<String>();
		cBoxUsuPerfil.setModel(new DefaultComboBoxModel<String>(new String[] {"user", "admin"}));
		cBoxUsuPerfil.setSelectedIndex(0);
		cBoxUsuPerfil.setBounds(30, 226, 223, 24);
		getContentPane().add(cBoxUsuPerfil);
		
		JButton btnUsuCreate = new JButton("");
		btnUsuCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adicionar();
			}
		});
		btnUsuCreate.setToolTipText("Adicionar");
		btnUsuCreate.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/Adicionar.png")));
		btnUsuCreate.setBounds(32, 285, 120, 120);
		getContentPane().add(btnUsuCreate);
		
		JButton btnUsuUpdate = new JButton("");
		btnUsuUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarDadosUsuario();
			}
		});
		btnUsuUpdate.setToolTipText("Editar");
		btnUsuUpdate.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/Editar.png")));
		btnUsuUpdate.setBounds(184, 285, 120, 120);
		getContentPane().add(btnUsuUpdate);
		
		JButton btnUsuRead = new JButton("");
		btnUsuRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consultar();
			}
		});
		btnUsuRead.setToolTipText("Consultar");
		btnUsuRead.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/Procurar.png")));
		btnUsuRead.setBounds(336, 285, 120, 120);
		getContentPane().add(btnUsuRead);
		
		JButton btnUsuDelete = new JButton("");
		btnUsuDelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				deletarUsuario();
			}
		});
		btnUsuDelete.setToolTipText("Excluir");
		btnUsuDelete.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/Remover.png")));
		btnUsuDelete.setBounds(488, 285, 120, 120);
		getContentPane().add(btnUsuDelete);
		
		JLabel lblCamposObrigatorios = new JLabel("* Campos obrigatórios");
		lblCamposObrigatorios.setFont(new Font("Dialog", Font.BOLD, 10));
		lblCamposObrigatorios.setBounds(444, 26, 164, 15);
		getContentPane().add(lblCamposObrigatorios);
		//setBounds(100, 100, 450, 300);
		return;

		
	}
}
