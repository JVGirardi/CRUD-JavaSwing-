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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.biblioteca.domain.Autor;
import com.biblioteca.domain.Client;
import com.formdev.flatlaf.FlatLightLaf;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.ButtonBarBuilder;

public class AutorTablePanel extends JPanel {
	
	public interface ListListener {
		void onEdit(Autor autor);
	}
	
	private ListListener listener;
	
	private JTable table;
	private DefaultTableModel tableModel;
	private AutorListModel listModel;
	private JButton removeButton;
	private JButton refreshButton;
	private JButton editButton;
	
	
	public AutorTablePanel(ListListener listener) {
		this.listener = listener;
		initComponents();
		initListeners();
		buildTablePanel();
		refreshTable();
	}
	
	
	private void initComponents() {
		
		String[] colunas = {"ID", "Nome", "Nacionalidade" };
		tableModel = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		listModel = new AutorListModel();
		
		table = new JTable(tableModel);
		
		//conect a seleção da tabela ao selectioninlist do model
		table.setSelectionModel(new SingleListSelectionAdapter(listModel.getSelection().getSelectionIndexHolder()));
		
		removeButton = new JButton("Remover");
		refreshButton = new JButton("Atualizar");
		editButton = new JButton("Editar");
	
	}
	
	public void refreshTable() {
		listModel.loadAutors();
		tableModel.setRowCount(0);
		
		for (Autor a : listModel.getSelection().getList()) {
			tableModel.addRow(new Object[] { a.getId(), a.getName(), a.getNationality()});
	};
	}
	
	private void initListeners() {
		
		refreshButton.addActionListener(e -> {
			refreshTable();
			});
		
		removeButton.addActionListener(e -> {
			if (!listModel.getSelection().hasSelection()) {
				JOptionPane.showMessageDialog(this, "Selecione um autor para deletar");
				return;
			}
			
			int opt = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este autor?", "Confirmação", JOptionPane.YES_NO_OPTION);
			
			if (opt == JOptionPane.YES_OPTION) {
				try {
					listModel.deleteSelection();
					JOptionPane.showMessageDialog(this, "Autor removido com sucesso");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao excluir este autor", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			refreshTable();
				
		});
		
		editButton.addActionListener(e -> {
			if (listModel.getSelection().hasSelection()) {
				if (listener != null) {
					listener.onEdit(listModel.getSelection().getValue());
				}
			} else {
				JOptionPane.showMessageDialog(this, "Selecione um autor para editar.");
			}
		});
		
	}
	
	
	private void buildTablePanel() {
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(20, 20, 10, 20));
		JLabel titleLabel = new JLabel("Listagem de Autores");
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		titleLabel.setForeground(new Color(50, 50, 50));
		
		this.add(titleLabel, BorderLayout.NORTH);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		ButtonBarBuilder btnBuilder = new ButtonBarBuilder();
		btnBuilder.addGlue();
		btnBuilder.addButton(refreshButton);
		btnBuilder.addRelatedGap();
		btnBuilder.addButton(removeButton);
		btnBuilder.addRelatedGap();
		btnBuilder.addButton(editButton);
		
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
			AutorFormPanel autor = new AutorFormPanel();
			
			JFrame frame = new JFrame("teste");
			frame.add(new AutorTablePanel(a -> {
				autor.setAutor(a);
			}));
			frame.pack();
			frame.setResizable(false);
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}

}
