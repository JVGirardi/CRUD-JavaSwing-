package com.biblioteca.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.biblioteca.domain.Livro;
import com.formdev.flatlaf.FlatLightLaf;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.ButtonBarBuilder;



public class LivroTablePanel extends JPanel {
	
	public interface ListListener {
		void onEdit(Livro livro);
	};
	
	private LivroListModel listModel;
	private JTable table;
	private DefaultTableModel tableModel;
	
	private JButton editButton;
	private JButton deleteButton;
	private JButton refreshButton;
	
	private ListListener listener;

	
	
	public LivroTablePanel() {
		this.listModel = new LivroListModel();
		this.setLayout(new BorderLayout());
		this.listener = listener;
		
		initComponents();
		initListeners();
		buildTablePanel();
		refreshTable();
		
	}
	
	public void initComponents() {
		String[] colunas = {"ID", "Capa" ,"Titulo", "Autor", "Nacionalidade", "Gênero", "Ano", "ISBN"};
		tableModel = new DefaultTableModel(colunas, 0) {
			@Override 
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		
		//conecta a seleção da tabela ao SelectionInList do Model
		table.setSelectionModel(new SingleListSelectionAdapter(listModel.getSelection().getSelectionIndexHolder()));
		table.setRowHeight(100);
		
		DefaultTableCellRenderer standartCells = new DefaultTableCellRenderer() {
			@Override
			 public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		        setHorizontalAlignment(CENTER);

		        setFont(getFont().deriveFont(Font.BOLD));

		        return this;
		    }
		};
		
		table.setDefaultRenderer(Object.class, standartCells);
		
		
		table.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
		
		
		
		editButton = new JButton("Editar");
		deleteButton = new JButton("Remover");
		refreshButton = new JButton("Atualizar");	
		
		
	}
	
	public void refreshTable() {
		listModel.loadDados();
		tableModel.setRowCount(0);
		for (Livro l : listModel.getSelection().getList()) {
			tableModel.addRow(new Object[] { l.getId(), l.getCapaImagem(), l.getTitulo(), l.getAutor().getId() + " - " + l.getAutor().getName(), 
					l.getAutor().getNationality(), l.getGenero(), l.getPublicationYear(), l.getIsbn()});
		
		}
		
	}
	
	public void initListeners() {
		
		refreshButton.addActionListener(e -> {
			refreshTable();
		});
		
		deleteButton.addActionListener(e -> {
			if (listModel.getSelection().hasSelection()) {
				Livro livroSelecionado = listModel.getSelection().getSelection();
				int opc = JOptionPane.showConfirmDialog(this, "Deseja mesmo excluir? \n"
						+ "ID: " + livroSelecionado.getTitulo() + "\n"
						+ "Titulo: " + livroSelecionado.getTitulo() + "\n"
						+ "Autor: " + livroSelecionado.getAutor().getName() + "\n"
						+ "Gênero: " + livroSelecionado.getGenero() + "\n"
						+ "Ano: " + livroSelecionado.getPublicationYear() + "\n"
						+ "ISBN: " + livroSelecionado.getIsbn(), 
						"Deletar cliente",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (opc == JOptionPane.YES_OPTION) {
					listModel.deleteSelection();
					refreshTable();
					JOptionPane.showMessageDialog(this, "Excluido com sucesso!");
				}
			} else {
				JOptionPane.showMessageDialog(this, "Selecione um livro da lista para excluir.");
			}
		});
		
		
		editButton.addActionListener(e -> {
			if (listModel.getSelection().hasSelection()) {
				Livro livroSelecionado = listModel.getSelection().getValue();
				if (livroSelecionado != null ) {
					listener.onEdit(livroSelecionado);
				} else {
					JOptionPane.showMessageDialog(this, "Selecione um Livro para editar.");
				}
			}
		});
	}
	
	public void buildTablePanel() {
		this.setBorder(new EmptyBorder(20, 20, 10, 20));
		JLabel titleLabel = new JLabel("Listagem de Livros");
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
		
	
	public class ImageRenderer extends DefaultTableCellRenderer {
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, 
	            boolean isSelected, boolean hasFocus, int row, int column) {
	        
	        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        
	        setText("");
	        setIcon(null);

	        if (value instanceof byte[]) {
	            byte[] bytes = (byte[]) value;
	            
	            if (bytes.length > 0) {
	                ImageIcon icon = new ImageIcon(bytes);
	                
	                int alturaLinha = table.getRowHeight(row);
	                int larguraProporcional = (alturaLinha * icon.getIconWidth()) / icon.getIconHeight();
	                
	                if (alturaLinha > 0) {
	                     Image img = icon.getImage().getScaledInstance(larguraProporcional, alturaLinha, Image.SCALE_SMOOTH);
	                     setIcon(new ImageIcon(img));
	                } else {
	                     setIcon(icon);
	                }
	            }
	        } else if (value == null) {
	            setText("Sem Imagem");
	        }

	        setHorizontalAlignment(CENTER);
	        
	        setFont(getFont().deriveFont(Font.BOLD));
	        
	        return this;
		}
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));
		} catch (Exception e) {
		}
		SwingUtilities.invokeLater(() -> {
			
			JFrame frame = new JFrame("teste");
			frame.add(new LivroTablePanel());
			frame.setSize(1000,500);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
	
}



