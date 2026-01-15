package com.biblioteca.presentation;

import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.biblioteca.domain.Emprestimo;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.SelectionInList;

public class EmprestimoTablePanel extends JPanel {
	
	private JTable table;
	private DefaultTableModel tableModel;
	private EmprestimoPresentationModel model;
	private JButton returnButton;
	private SelectionInList<Emprestimo> selection;
	
	
	
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
		
		
		
	}
	
	private void refreshTable() {
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


}

