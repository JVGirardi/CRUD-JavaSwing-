package com.biblioteca.presentation;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;

public class AutorTablePanel extends JPanel {
	
	private JTable table;
	private DefaultTableModel tableModel;
	private AutorListModel listModel;
	private JButton removeButton;
	private JButton refreshButton;
	private JButton editButton;
	
	public AutorTablePanel() {
		
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
	
	private void refreshTable() {
		
	}
	
	
	private void buildTablePanel() {
		
	}

}
