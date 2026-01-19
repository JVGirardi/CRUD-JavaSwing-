package com.biblioteca.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.biblioteca.domain.Emprestimo;
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
	
	private ImageLoaderWorker currentWorker;
	
	private Runnable onRemoveRefreshEmprestimo;
	
	public LivroTablePanel(ListListener listener) {
		this.listModel = new LivroListModel();
		this.setLayout(new BorderLayout());
		this.listener = listener;
		
		initComponents();
		initListeners();
		buildTablePanel();
		
		refreshTable();
		
	}
	
	public void setOnRemoveRefreshEmprestimo(Runnable onRemoveRefreshEmprestimo) {
		this.onRemoveRefreshEmprestimo = onRemoveRefreshEmprestimo;
	}
	
	public void initComponents() {
		String[] colunas = {"ID", "Capa" ,"Titulo", "Autor", "Nacionalidade", "Gênero", "Ano", "ISBN"};
		tableModel = new DefaultTableModel(colunas, 0) {
			@Override 
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 1) return Icon.class;
				return super.getColumnClass(columnIndex);
			}
		};
		
		table = new JTable(tableModel);
		
		//conecta a seleção da tabela ao SelectionInList do Model
		table.setSelectionModel(new SingleListSelectionAdapter(listModel.getSelection().getSelectionIndexHolder()));
		table.setRowHeight(80);
		
		
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			 public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		        setHorizontalAlignment(CENTER);

		        setFont(getFont().deriveFont(Font.PLAIN));

		        return this;
		    }
		});
		
		
		table.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
		
		setColumnWidght(table, 0, 5);
		setColumnWidght(table, 1, 50);
		setColumnWidght(table, 2, 30);
		setColumnWidght(table, 3, 30);
		setColumnWidght(table, 4, 30);
		setColumnWidght(table, 5, 30);
		setColumnWidght(table, 6, 30);
		setColumnWidght(table, 7, 70);
		
		editButton = new JButton("Editar");
		deleteButton = new JButton("Remover");
		refreshButton = new JButton("Atualizar");
	}
	
	private static void setColumnWidght(JTable table, int coluna, int largura) {
		table.getColumnModel().getColumn(coluna).setPreferredWidth(largura);
	}
	
	public void refreshTable() {
		
		if (currentWorker != null && !currentWorker.isDone()) {
			currentWorker.cancel(true);	
		}
		
		listModel.loadLivros();
		tableModel.setRowCount(0);
		
		List<Livro> livros = listModel.getSelection().getList();
		
		for (Livro l : livros) {
			tableModel.addRow(new Object[] { l.getId(), null, l.getTitulo(), l.getAutor().getId() + " - " + l.getAutor().getName(), 
					l.getAutor().getNationality(), l.getGenero(), l.getPublicationYear(), l.getIsbn()});
		
		}
		
		if (!livros.isEmpty()) {
			ImageLoaderWorker worker = new ImageLoaderWorker(livros);
			worker.execute();
		}
		
	}
	
	public void initListeners() {
		
		refreshButton.addActionListener(e -> {
			refreshTable();
		});
		
		deleteButton.addActionListener(e -> {
			
			if (listModel.getSelection().hasSelection()) {
				Livro livroSelecionado = listModel.getSelection().getSelection();
				if (!listModel.isLivroDisponivel(livroSelecionado)) {
					JOptionPane.showMessageDialog(this, "Não é possivel excluir um livro vinculado a um emprestimo.", "Erro ao excluir este livro", JOptionPane.ERROR_MESSAGE);
				} else {
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
						try {
							listModel.deleteSelection();
							refreshTable();
							onRemoveRefreshEmprestimo.run();
							JOptionPane.showMessageDialog(this, "Livro excluido com sucesso!");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao excluir este livro", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Selecione um livro da lista para excluir.");
			}
		});
		
		
		editButton.addActionListener(e -> {
			if (listModel.getSelection().hasSelection()) {
				Livro livroSelecionado = listModel.getSelection().getValue();
				if (!listModel.isLivroDisponivel(livroSelecionado)) {
					JOptionPane.showMessageDialog(this, "Não é possivel editar um livro vinculado a um emprestimo.", "Erro ao editar este livro", JOptionPane.ERROR_MESSAGE);
				} else {
					if (livroSelecionado != null ) {
						listener.onEdit(livroSelecionado);
					}
				}
			} else {
			JOptionPane.showMessageDialog(this, "Selecione um Livro para editar.");
			}
		});
	}
	
	private static class CelulaImagem {
		int linha;
		ImageIcon imagem;
		
		public CelulaImagem(int linha, ImageIcon capa) {
			this.linha = linha;
			this.imagem = capa;
		}
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
	        setHorizontalAlignment(CENTER);
	        
	        if (value instanceof Icon) {
	        	setIcon((Icon) value);
	        } else {
	        	setIcon(null);
	        	setText(value == null ? "Carregando..." : "Sem img");
	        }
	        setFont(getFont().deriveFont(Font.BOLD));
	        return this;
		}
	}
	
	private class ImageLoaderWorker extends SwingWorker<Void, CelulaImagem> {
		
		private final List<Livro> livros;
		private final int rowHeight;
		
		public ImageLoaderWorker(List<Livro> livros) {
			this.livros = livros;
			this.rowHeight = table.getRowHeight();
		}
		
		@Override
		protected Void doInBackground() throws Exception {
			for (int i = 0; i < livros.size(); i++) {
				Livro l = livros.get(i);
			
				byte[] imgByte = null;
				imgByte = l.getCapaImagem();
				
				if (imgByte != null && imgByte.length > 0) {
					ImageIcon icon = new ImageIcon(imgByte);
					
					if (icon.getIconWidth() > 0) {
						int alturaLinha = rowHeight - 4;
						int larguraProporcional = (alturaLinha * icon.getIconWidth() / icon.getIconHeight());
						
						Image imgEscalada = icon.getImage().getScaledInstance(larguraProporcional, alturaLinha, Image.SCALE_SMOOTH);
						
						publish(new CelulaImagem(i, new ImageIcon(imgEscalada)));
					}
				}
			}
			return null;
		}
		
		@Override
		protected void process(List<CelulaImagem> chunks) {
			
			for (CelulaImagem celula : chunks) {
				if (celula.linha < tableModel.getRowCount()) {
					tableModel.setValueAt(celula.imagem, celula.linha, 1);
				}
				
			}
			
		}
	}
	
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));
		} catch (Exception e) {
		}
		SwingUtilities.invokeLater(() -> {
			
			LivroFormPanel livroFormPanel = new LivroFormPanel();
			
			JFrame frame = new JFrame("teste");
			frame.add(new LivroTablePanel(l -> {
				livroFormPanel.setLivro(l);
			}));
			frame.setSize(1000,500);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
	
}



