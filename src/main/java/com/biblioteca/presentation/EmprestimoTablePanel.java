package com.biblioteca.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.biblioteca.domain.Emprestimo;
import com.formdev.flatlaf.FlatLightLaf;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.ButtonBarBuilder;

public class EmprestimoTablePanel extends JPanel {
	
	private JTable table;
	private DefaultTableModel tableModel;
	private EmprestimoPresentationModel model;
	private JButton returnButton;
	private SelectionInList<Emprestimo> selection;
	private JButton gerarRelatorioButton;
	
	private Runnable onReturnRefreshFormPanel;
	
	public EmprestimoTablePanel() {
		this.model = new EmprestimoPresentationModel(new Emprestimo());
		this.selection = new SelectionInList<Emprestimo>();
		this.setLayout(new BorderLayout());
		
		initComponents();
		buildTablePanel();
		initListeners();
		
		refreshTable();
		
	}
	
	public void setOnReturnRefreshFormPanel(Runnable refreshFormPanel) {
		this.onReturnRefreshFormPanel = refreshFormPanel;
	}
	
	private void initComponents() {
		String[] colunas = {"ID", "Cliente", "Livro", "Data Emprestimo", "Retorno Esperado", "Retorno Efetivo"};
		tableModel = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		
		//conecta a seleção da tabela ao SelectionInList do Model
		table.setSelectionModel(new SingleListSelectionAdapter(selection.getSelectionIndexHolder()));
		
		returnButton = new JButton("Devolver");
		gerarRelatorioButton = new JButton("Gerar Relatório");
	
		
	}
	
	public void refreshTable() {
		selection.setList(model.buscarEmprestimosEmAberto());
		tableModel.setRowCount(0);
		for (Emprestimo e : selection.getList()) {
			DateTimeFormatter formatadorBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String dataEmprestimoFormatada = e.getDataEmprestimo().format(formatadorBrasileiro);
			String dataRetornoEsperadoFormatado = e.getDataDevolucaoPrevista().format(formatadorBrasileiro);
			String dataDevolucaoEfetiva = e.getDataDevolucaoEfetiva() != null ? e.getDataDevolucaoEfetiva().format(formatadorBrasileiro) : "Pendente";
				
			tableModel.addRow(new Object[] { e.getId(), e.getClient(), e.getLivro(), dataEmprestimoFormatada, dataRetornoEsperadoFormatado, dataDevolucaoEfetiva});
		}
		
	}
	
	public void initListeners() {
		returnButton.addActionListener(l -> {
			Emprestimo emprestimoSelecionado = selection.getSelection();
			if (emprestimoSelecionado != null) {
				model.efetivarDevolucao(emprestimoSelecionado);
				String tituloLivro = emprestimoSelecionado.getLivro().getTitulo();
				String nomeClient = emprestimoSelecionado.getClient().getName();
				JOptionPane.showMessageDialog(this, "Livro: \n" + tituloLivro + "\nRetornado por:\n" + nomeClient);
				onReturnRefreshFormPanel.run();
				refreshTable();
				
			} else {
				JOptionPane.showConfirmDialog(this, "Selecione uma linha da tabela para devolver.");
			}
		});
		
		selection.addPropertyChangeListener(l -> {
			atualizarBotaoDevolver();
		});
		
		gerarRelatorioButton.addActionListener(l -> {
			gerarRelatorioButton.setEnabled(false);
			model.gerarRelatorioEmprestimo();
		});
	}
	
	public void atualizarBotaoDevolver() {
		boolean temSelecao = selection.hasSelection();
		returnButton.setEnabled(temSelecao);
	}
	
	public void buildTablePanel() {
		this.setBorder(new EmptyBorder(20, 20, 10, 20));
		JLabel titleLabel = new JLabel("Empréstimos em Aberto");
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		titleLabel.setForeground(new Color(50, 50, 50));
		
		this.add(titleLabel, BorderLayout.NORTH);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		ButtonBarBuilder btnBuilder = new ButtonBarBuilder();
		btnBuilder.addGlue();
		btnBuilder.addButton(returnButton);
		btnBuilder.addRelatedGap();
		btnBuilder.addButton(gerarRelatorioButton);
		
		JPanel buttonPanel = btnBuilder.getPanel();
		buttonPanel.setBorder(new EmptyBorder(20,20,20,20));
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));
		} catch (Exception e) {
		}
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("teste");
			frame.add(new EmprestimoTablePanel());
			frame.setSize(800, 600);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}




}

