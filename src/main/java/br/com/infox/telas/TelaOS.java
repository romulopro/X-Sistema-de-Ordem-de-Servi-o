package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.Optional;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import br.com.infox.dal.ModuloConexao;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JButton;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class TelaOS extends JInternalFrame {
	private JTextField txtFOSNumero;
	private JTextField txtFOSData;
	private final ButtonGroup buttonGroupOS = new ButtonGroup();
	private JRadioButton rdbtnOrcamento;
	private JTextField txtFOSPesquisarCliente;
	private JTextField txtFOSSubPainelClienteId;
	private JTable tableOSCliente;
	private JTextField txtFOSEquipamento;
	private JTextField txtFOSDefeito;
	private JTextField txtFOSServico;
	private JTextField txtFOSTecnico;
	private JTextField txtFOSValor;
	private String tipo;
	private Optional<Connection> conexao;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private JComboBox<String> comboBoxSituacao;
	private JRadioButton rdbtnOrdemDeServico;
	private JButton btnOSAdicionar;
	private JButton btnOSPesquisar;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public TelaOS() {
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				//Ao abriri o form, marcar o radioButton Orcamento
				rdbtnOrcamento.setSelected(true);
				tipo = rdbtnOrcamento.getText();
			}
		});
		conexao = ModuloConexao.conector();
		
		setTitle("OS");
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setMaximumSize(new Dimension(640, 480));
		
		setSize(new Dimension(640, 480));
		setPreferredSize(new Dimension(640, 480));
		setMinimumSize(new Dimension(640, 480));
		getContentPane().setLayout(null);
		
		JPanel subPainelOS = new JPanel();
		subPainelOS.setBounds(12, 12, 263, 94);
		subPainelOS.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		getContentPane().add(subPainelOS);
		subPainelOS.setLayout(null);
		
		JLabel lblOSNum = new JLabel("Nº OS");
		lblOSNum.setBounds(12, 12, 70, 15);
		subPainelOS.add(lblOSNum);
		
		JLabel lblOSData = new JLabel("Data");
		lblOSData.setBounds(68, 12, 70, 15);
		subPainelOS.add(lblOSData);
		
		txtFOSNumero = new JTextField();
		txtFOSNumero.setBounds(12, 39, 47, 19);
		subPainelOS.add(txtFOSNumero);
		txtFOSNumero.setColumns(10);
		
		txtFOSData = new JTextField();
		txtFOSData.setBounds(68, 39, 114, 19);
		subPainelOS.add(txtFOSData);
		txtFOSData.setColumns(10);
		
		rdbtnOrcamento = new JRadioButton("Orçamento");
		rdbtnOrcamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tipo = "Orçamento";
			}
		});
		rdbtnOrcamento.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonGroupOS.add(rdbtnOrcamento);
		rdbtnOrcamento.setBounds(12, 63, 95, 23);
		subPainelOS.add(rdbtnOrcamento);
		
		rdbtnOrdemDeServico = new JRadioButton("Ordem de Serviço");
		rdbtnOrdemDeServico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipo = "OS";
			}
		});
		rdbtnOrdemDeServico.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonGroupOS.add(rdbtnOrdemDeServico);
		rdbtnOrdemDeServico.setBounds(117, 63, 140, 23);
		subPainelOS.add(rdbtnOrdemDeServico);
		
		JLabel lblSituacao = new JLabel("Situação");
		lblSituacao.setBounds(12, 122, 70, 15);
		getContentPane().add(lblSituacao);
		
		comboBoxSituacao = new JComboBox<String>();
		comboBoxSituacao.setFont(new Font("Dialog", Font.PLAIN, 11));
		comboBoxSituacao.setModel(new DefaultComboBoxModel(new String[] {"Na bancada", "Entrega OK", "Orçamento Reprovado", "Aguardando Aprovação", "Aguardando Peças", "Abandonado Pelo Cliente", "Retornou"}));
		comboBoxSituacao.setBounds(80, 117, 183, 24);
		getContentPane().add(comboBoxSituacao);
		
		JPanel subPainelOSCliente = new JPanel();
		subPainelOSCliente.setBounds(280, 12, 338, 174);
		subPainelOSCliente.setBorder(BorderFactory.createTitledBorder( "Cliente"));
		getContentPane().add(subPainelOSCliente);
		subPainelOSCliente.setLayout(null);
		
		txtFOSPesquisarCliente = new JTextField();
		txtFOSPesquisarCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarCliente();
			}
		});
		txtFOSPesquisarCliente.setBounds(12, 24, 114, 19);
		subPainelOSCliente.add(txtFOSPesquisarCliente);
		txtFOSPesquisarCliente.setColumns(10);
		
		JLabel lblIconSearch = new JLabel("");
		lblIconSearch.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/search_textField_32.png")));
		lblIconSearch.setBounds(131, 15, 32, 32);
		subPainelOSCliente.add(lblIconSearch);
		
		JLabel lblIDCliente = new JLabel("*Id");
		lblIDCliente.setBounds(178, 26, 70, 15);
		subPainelOSCliente.add(lblIDCliente);
		
		txtFOSSubPainelClienteId = new JTextField();
		txtFOSSubPainelClienteId.setEditable(false);
		txtFOSSubPainelClienteId.setBounds(209, 24, 47, 19);
		subPainelOSCliente.add(txtFOSSubPainelClienteId);
		txtFOSSubPainelClienteId.setColumns(10);
		
		Object row[][]= {{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null}};
		String columnName[] = {"ID", "Nome", "Telefone"};
		
		tableOSCliente = new JTable(row, columnName) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		};
		tableOSCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});
		tableOSCliente.setBackground(Color.WHITE);
		tableOSCliente.setAutoCreateColumnsFromModel(false);
		tableOSCliente.setName("");
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setBounds(12, 55, 314, 101);
		jScrollPane.setViewportView(tableOSCliente);
		subPainelOSCliente.add(jScrollPane);
		
		txtFOSEquipamento = new JTextField();
		txtFOSEquipamento.setBounds(135, 198, 483, 19);
		getContentPane().add(txtFOSEquipamento);
		txtFOSEquipamento.setColumns(10);
		
		txtFOSDefeito = new JTextField();
		txtFOSDefeito.setBounds(135, 218, 483, 19);
		getContentPane().add(txtFOSDefeito);
		txtFOSDefeito.setColumns(10);
		
		txtFOSServico = new JTextField();
		txtFOSServico.setBounds(135, 239, 483, 19);
		getContentPane().add(txtFOSServico);
		txtFOSServico.setColumns(10);
		
		txtFOSTecnico = new JTextField();
		txtFOSTecnico.setBounds(135, 259, 181, 19);
		getContentPane().add(txtFOSTecnico);
		txtFOSTecnico.setColumns(10);
		
		JLabel lblEquipamento = new JLabel("Equipamento*");
		lblEquipamento.setBounds(12, 200, 105, 15);
		getContentPane().add(lblEquipamento);
		
		JLabel lblDefeito = new JLabel("Defeito*");
		lblDefeito.setBounds(12, 220, 70, 15);
		getContentPane().add(lblDefeito);
		
		JLabel lblServico = new JLabel("Serviço");
		lblServico.setBounds(12, 241, 70, 15);
		getContentPane().add(lblServico);
		
		JLabel lblTcnico = new JLabel("Técnico");
		lblTcnico.setBounds(12, 260, 70, 15);
		getContentPane().add(lblTcnico);
		
		JLabel lblValorTotal = new JLabel("Valor Total");
		lblValorTotal.setBounds(334, 260, 83, 15);
		getContentPane().add(lblValorTotal);
		
		txtFOSValor = new JTextField();
		txtFOSValor.setText("0");
		txtFOSValor.setBounds(435, 259, 183, 19);
		getContentPane().add(txtFOSValor);
		txtFOSValor.setColumns(10);
		
		btnOSAdicionar = new JButton("");
		btnOSAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emitirOS();
			}
		});
		btnOSAdicionar.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/Adicionar.png")));
		btnOSAdicionar.setBounds(34, 340, 90, 90);
		getContentPane().add(btnOSAdicionar);
		
		btnOSPesquisar = new JButton("");
		btnOSPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarOS();
			}
		});
		btnOSPesquisar.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/Procurar.png")));
		btnOSPesquisar.setBounds(156, 340, 90, 90);
		getContentPane().add(btnOSPesquisar);
		
		JButton btnOSEditar = new JButton("");
		btnOSEditar.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/Editar.png")));
		btnOSEditar.setBounds(278, 340, 90, 90);
		getContentPane().add(btnOSEditar);
		
		JButton btnOSDeletar = new JButton("");
		btnOSDeletar.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/Remover.png")));
		btnOSDeletar.setBounds(400, 340, 90, 90);
		getContentPane().add(btnOSDeletar);
		
		JButton btnOSImprimir = new JButton("");
		btnOSImprimir.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/314492_printer_text_icon.png")));
		btnOSImprimir.setBounds(522, 340, 90, 90);
		getContentPane().add(btnOSImprimir);
		
		

	}
	private void pesquisarCliente() {
		String sqlQuery = "SELECT idcli as ID, "
				+ "nomecli as Nome, "
				+ "fonecli as Telefone "
				+ "FROM tbclientes WHERE nomecli LIKE ?";
		try {
			pst = conexao.get().prepareStatement(sqlQuery);
			pst.setString(1, txtFOSPesquisarCliente.getText() + "%");
			rs = pst.executeQuery();
			tableOSCliente.setModel(DbUtils.resultSetToTableModel(rs));
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void setarCampos() {
		int setar = tableOSCliente.getSelectedRow();
		txtFOSSubPainelClienteId.setText(tableOSCliente.getModel().getValueAt(setar, 0).toString());
		
	}
	
	private void emitirOS() {
		String sqlQuery = "INSERT INTO tbos (tipo, situacao, equipamento, defeito, servico, tecnico, valor, idcli)"
				+ "values(?,?,?,?,?,?,?,?)";
		if(haAlgumCampoObrigatorioVazio()) {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			return;
		}
		try {
			pst = conexao.get().prepareStatement(sqlQuery);
			pst.setString(1, tipo);
			pst.setString(2, comboBoxSituacao.getSelectedItem().toString());
			pst.setString(3, txtFOSEquipamento.getText());
			pst.setString(4, txtFOSDefeito.getText());
			pst.setString(5, txtFOSServico.getText());
			pst.setString(6, txtFOSTecnico.getText());
			pst.setString(7, txtFOSValor.getText().replace(",", "."));
			pst.setString(8, txtFOSSubPainelClienteId.getText());
			int adicionado = pst.executeUpdate();
			btnOSAdicionar.setEnabled(false);
			txtFOSPesquisarCliente.setEnabled(false);
			tableOSCliente.setVisible(false);
			if(adicionado>0) {
				JOptionPane.showMessageDialog(null, "OS adicionada com sucesso");
				limparTodosOsCampos();
			}
			// validacao de campos obrigatórios
				
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void pesquisarOS() {
		String numeroOS = JOptionPane.showInputDialog("Número da OS");
		String sqlQuery = "SELECT * FROM tbos where os=" + numeroOS;
		try {
			pst = conexao.get().prepareStatement(sqlQuery);
			rs = pst.executeQuery();
			if(rs.next()) {
				txtFOSNumero.setText(rs.getString(1));
				txtFOSData.setText(rs.getString(2));
				String rbtTipo = rs.getString(3);
				if(rbtTipo == "OS") {
					rdbtnOrcamento.setSelected(true);
					rdbtnOrdemDeServico.setSelected(false);
					tipo = "OS";
				}else {
					rdbtnOrcamento.setSelected(false);
					rdbtnOrdemDeServico.setSelected(true);
					tipo = "Orçamento";
				}
				comboBoxSituacao.setSelectedItem(rs.getString(4));
				txtFOSEquipamento.setText(rs.getString(5));
				txtFOSDefeito.setText(rs.getString(6));
				txtFOSServico.setText(rs.getString(7));
				txtFOSTecnico.setText(rs.getString(8));
				txtFOSValor.setText(rs.getString(9));
				txtFOSSubPainelClienteId.setText(rs.getString(10));
			}else {
				JOptionPane.showMessageDialog(null, "OS não cadastrada");
			}
			
		} 
		catch (java.sql.SQLSyntaxErrorException e) {
			JOptionPane.showMessageDialog(null, "Entrada Inválida, utilize somente números");
		}
		
		catch (SQLException e2) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e2);
		}
		
		
	}
	private boolean haAlgumCampoObrigatorioVazio() {
		return txtFOSSubPainelClienteId.getText().isEmpty() 
				|| txtFOSEquipamento.getText().isEmpty()
				|| txtFOSDefeito.getText().isEmpty();
	}
	private void limparTodosOsCampos() {
		txtFOSEquipamento.setText(null);
		txtFOSDefeito.setText(null);
		txtFOSServico.setText(null);
		txtFOSTecnico.setText(null);
		txtFOSValor.setText(null);
		txtFOSSubPainelClienteId.setText(null);
	}
}
