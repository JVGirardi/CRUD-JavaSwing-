package com.biblioteca.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.biblioteca.domain.Client;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.ButtonBarBuilder;

public class ClientTablePanel extends JPanel {
	
	public interface ListListener {
		void onEdit(Client client);
	}
	
	private ClientListModel listModel;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton editButton;
	private JButton deleteButton;
	private JButton refreshButton;
	
	private ListListener listener;
	
	public ClientTablePanel(ListListener listener) {
		this.listener = listener;
		this.listModel = new ClientListModel();
		
		this.setLayout(new BorderLayout());
		initComponents();
		initListeners();
		buildTablePanel();
		refreshTable();
	}
	
	private void initComponents() {
		String[] colunas = {"ID", "Nome", "E-mail", "Telefone"};
		tableModel = new DefaultTableModel(colunas, 0) {
			@Override 
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		
		//conecta a seleção da tabela ao SelectionInList do Model
		table.setSelectionModel(new SingleListSelectionAdapter(listModel.getSelection().getSelectionIndexHolder()));
		
		editButton = new JButton("Editar");
		deleteButton = new JButton("Remover");
		refreshButton = new JButton("Atualizar");	
		
	}
	
	public void refreshTable() {
		listModel.loadClients();
		tableModel.setRowCount(0);
		
		for (Client c : listModel.getSelection().getList()) {
			tableModel.addRow(new Object[] { c.getId(), c.getName(), c.getEmail(), c.getPhone() });
		}
	}
	
	public void initListeners() {
		
		refreshButton.addActionListener(e -> {
			refreshTable();
		});
		
		deleteButton.addActionListener(e -> { 
			if (listModel.getSelection().hasSelection()) {
				Client clienteSelecionado = listModel.getSelection().getValue();
				int opt = JOptionPane.showConfirmDialog(this, 
						"Deseja mesmo excluir?\n" +
				"ID: [" + clienteSelecionado.getId() + " ]\n" +
								"Nome: [" + clienteSelecionado.getName() + "]\n" +
				"E-mail: [" + clienteSelecionado.getEmail() + "]\n" +
			"Número de Celular: [" + clienteSelecionado.getPhone() + "]", "Deletar Cliente", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (opt == JOptionPane.YES_OPTION) {
					listModel.deleteSelection();
					refreshTable();
					JOptionPane.showMessageDialog(this, "Excluído com sucesso!");
				}
			} else {
				JOptionPane.showMessageDialog(this, "Selecione um cliente na lista para excluir.");
			}
		});
		
		editButton.addActionListener(e -> {
			if (listModel.getSelection().hasSelection()) {
				if (listener != null) {
					listener.onEdit(listModel.getSelection().getValue());
				}
			} else {
				JOptionPane.showMessageDialog(this, "Selecione um Cliente para editar. ");
			}
		});
	}
	
	public void buildTablePanel() {
		this.setBorder(new EmptyBorder(20, 20, 10, 20));
		JLabel titleLabel = new JLabel("Listagem de Clientes");
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		titleLabel.setForeground(new Color(50, 50, 50));
		
		this.add(titleLabel, BorderLayout.NORTH);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		ButtonBarBuilder btnBuilder = new ButtonBarBuilder();
		btnBuilder.addGlue();
		btnBuilder.addButton(refreshButton);
		btnBuilder.addRelatedGap();
		btnBuilder.addButton(deleteButton);
		btnBuilder.addRelatedGap();
		btnBuilder.addButton(editButton);
		
		JPanel buttonPanel = btnBuilder.getPanel();
		buttonPanel.setBorder(new EmptyBorder(20,20,20,20));
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

}
