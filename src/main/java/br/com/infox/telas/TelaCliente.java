package br.com.infox.telas;

import javax.swing.JInternalFrame;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
//import javax.swing.table.DefaultTableModel;

import br.com.infox.dal.ModuloConexao;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class TelaCliente extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtFCliNome;
	private JTextField txtFCliEndereco;
	private JTextField txtFCliTelefone;
	private JTextField txtFCliEmail;
	private JTextField txtFCliPesquisar;
	private JTable tblClientes;
	private JButton BtnCliAdicionar;
	private Optional<Connection> conexao;
	private PreparedStatement pst;
	private ResultSet rs;
	private JTextField txtFCIdCliente;

	/**
	 * Create the frame.
	 */
	public TelaCliente() {
		

		conexao = ModuloConexao.conector();
		construirFrame();
		adicionarLabels();
		adicionarTextFields();
		montarBotoes();
		montarTabela();

	}

	private void montarBotoes() {
		BtnCliAdicionar = new JButton("");
		BtnCliAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adicionarCliente();
			}
		});
		BtnCliAdicionar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/Adicionar.png")));
		BtnCliAdicionar.setPreferredSize(new Dimension(80, 80));
		BtnCliAdicionar.setBounds(70, 320, 120, 120);
		getContentPane().add(BtnCliAdicionar);

		JButton btnCliEditar = new JButton("");
		btnCliEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarDadosCliente();
			}
		});
		btnCliEditar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/Editar.png")));
		btnCliEditar.setBounds(260, 320, 120, 120);
		getContentPane().add(btnCliEditar);

		JButton btnCliDeletar = new JButton("");
		btnCliDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deletarCliente();
			}
		});
		btnCliDeletar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/Remover.png")));
		btnCliDeletar.setBounds(450, 320, 120, 120);
		getContentPane().add(btnCliDeletar);

		JButton btnCliPesquisar = new JButton("");
		btnCliPesquisar.setIconTextGap(0);
		btnCliPesquisar
				.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/search_textField_32.png")));
		btnCliPesquisar.setBounds(329, 12, 32, 32);
		getContentPane().add(btnCliPesquisar);
	}

	private void montarTabela() {
		Object row[][] = { { null, null, null, null, null, }, { null, null, null, null, null, },
				{ null, null, null, null, null, }, { null, null, null, null, null, } };
		String columnName[] = { "ID", "Nome", "Endereço", "Telefone", "Email" };
		tblClientes = new JTable(row, columnName) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		};
		tblClientes.setFocusable(false);
		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});

		JScrollPane scrollPane = new JScrollPane(tblClientes);
		scrollPane.setBounds(12, 53, 606, 80);
		getContentPane().add(scrollPane);
	}

	private void adicionarTextFields() {
		txtFCliNome = new JTextField();
		txtFCliNome.setBounds(110, 184, 302, 19);
		getContentPane().add(txtFCliNome);
		txtFCliNome.setColumns(10);

		txtFCliEndereco = new JTextField();
		txtFCliEndereco.setBounds(110, 214, 302, 19);
		getContentPane().add(txtFCliEndereco);
		txtFCliEndereco.setColumns(10);

		txtFCliTelefone = new JTextField();
		txtFCliTelefone.setBounds(110, 244, 302, 19);
		getContentPane().add(txtFCliTelefone);
		txtFCliTelefone.setColumns(10);

		txtFCliEmail = new JTextField();
		txtFCliEmail.setBounds(110, 274, 302, 19);
		getContentPane().add(txtFCliEmail);
		txtFCliEmail.setColumns(10);

		txtFCliPesquisar = new JTextField();
		txtFCliPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarCliente();
			}
		});
		txtFCliPesquisar.setBounds(12, 22, 307, 19);
		getContentPane().add(txtFCliPesquisar);
		txtFCliPesquisar.setColumns(10);

		txtFCIdCliente = new JTextField();
		txtFCIdCliente.setEditable(false);
		txtFCIdCliente.setDisabledTextColor(new Color(184, 207, 229));
		txtFCIdCliente.setBounds(110, 153, 44, 19);
		getContentPane().add(txtFCIdCliente);
		txtFCIdCliente.setColumns(10);
	}

	private void adicionarLabels() {

		JLabel lblCamposObrigatorios = new JLabel("*Campos Obrigatórios");
		lblCamposObrigatorios.setBounds(393, 24, 164, 15);
		getContentPane().add(lblCamposObrigatorios);

		JLabel lblClienteId = new JLabel("Cliente Id");
		lblClienteId.setBounds(12, 155, 70, 15);
		getContentPane().add(lblClienteId);

		JLabel lblCliNome = new JLabel("*Nome");
		lblCliNome.setBounds(12, 184, 70, 15);
		getContentPane().add(lblCliNome);

		JLabel lblCliEndereco = new JLabel("Endereço");
		lblCliEndereco.setBounds(12, 214, 70, 15);
		getContentPane().add(lblCliEndereco);

		JLabel lblCliTelefone = new JLabel("*Telefone");
		lblCliTelefone.setBounds(12, 244, 85, 15);
		getContentPane().add(lblCliTelefone);

		JLabel lblCliEmail = new JLabel("E-mail");
		lblCliEmail.setBounds(12, 274, 85, 15);
		getContentPane().add(lblCliEmail);
	}

	private void construirFrame() {
		setMaximizable(true);
		getContentPane().setEnabled(false);

		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setMaximumSize(new Dimension(640, 480));

		setSize(new Dimension(640, 480));
		setPreferredSize(new Dimension(640, 480));
		setMinimumSize(new Dimension(640, 480));
		getContentPane().setLayout(null);
	}

	private void adicionarCliente() {
		int adicionado = 0;
		String query = "INSERT INTO tbclientes (nomecli, endcli, fonecli, emailcli)" + "values(?,?,?,?)";
		if (isAlgumCampoObrigatorioVazio()) {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			return;
		}
		try {
			pst = conexao.get().prepareStatement(query);
			pst.setString(1, txtFCliNome.getText());
			pst.setString(2, txtFCliEndereco.getText());
			pst.setString(3, txtFCliTelefone.getText());
			pst.setString(4, txtFCliEmail.getText());
			adicionado = pst.executeUpdate();
			if (isAtualizadoComSucesso(adicionado)) {
				JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso");
				limparTodosOsCamposETabela();
			} else {
				JOptionPane.showMessageDialog(null, adicionado);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return;
	}

	private void limparTodosOsCamposETabela() {
		txtFCliPesquisar.setText(null);
		txtFCIdCliente.setText(null);
		txtFCliNome.setText(null);
		txtFCliEndereco.setText(null);
		txtFCliTelefone.setText(null);
		txtFCliEmail.setText(null);
		// ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
	}

	private boolean isAlgumCampoObrigatorioVazio() {
		return txtFCliNome.getText().isEmpty() || txtFCliTelefone.getText().isEmpty();
	}

	private void pesquisarCliente() {
		String sqlQuery = "SELECT idcli as ID, " + "nomecli as Nome, " + "endcli as Endereço, "
				+ "fonecli as Telefone, " + "emailcli as Email " + "FROM tbclientes WHERE nomecli LIKE ?";
		try {
			pst = conexao.get().prepareStatement(sqlQuery);
			pst.setString(1, txtFCliPesquisar.getText() + "%");
			rs = pst.executeQuery();
			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void setarCampos() {
		int setar = tblClientes.getSelectedRow();
		txtFCIdCliente.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
		txtFCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
		txtFCliEndereco.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
		txtFCliTelefone.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
		txtFCliEmail.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
		BtnCliAdicionar.setEnabled(false);
	}

	private void atualizarDadosCliente() {
		String query = "UPDATE tbclientes SET nomecli=?,  endcli=?, fonecli=?, emailcli=? WHERE nomecli=?";
		int alterado = 0;
		if (isAlgumCampoObrigatorioVazio()) {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			return;
		}
		try {
			pst = conexao.get().prepareStatement(query);
			pst.setString(1, txtFCliNome.getText());
			pst.setString(2, txtFCliEndereco.getText());
			pst.setString(3, txtFCliTelefone.getText());
			pst.setString(4, txtFCliEmail.getText());
			pst.setString(5, txtFCliNome.getText());
			alterado = pst.executeUpdate();
			if (isAtualizadoComSucesso(alterado)) {
				JOptionPane.showMessageDialog(null, "Usuário alterado com sucesso");
				limparTodosOsCamposETabela();
				BtnCliAdicionar.setEnabled(true);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	

	private void deletarCliente() {
		int confirma = 0;
		int removido = 0;
		String query = "DELETE FROM tbclientes WHERE idcli=?";
		confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (isOpcaoNaoSelecionada(confirma)) {
			return;
		}

		try {
			pst = conexao.get().prepareStatement(query);
			pst.setString(1, txtFCIdCliente.getText());
			removido = pst.executeUpdate();
			if (isAtualizadoComSucesso(removido)) {
				JOptionPane.showMessageDialog(null, "Cliente removido com sucesso");
				limparTodosOsCamposETabela();

				BtnCliAdicionar.setEnabled(true);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private boolean isOpcaoNaoSelecionada(int confirma) {
		return confirma != JOptionPane.YES_OPTION;
	}
	
	private boolean isAtualizadoComSucesso(int alterado) {
		return alterado > 0;
	}
}
