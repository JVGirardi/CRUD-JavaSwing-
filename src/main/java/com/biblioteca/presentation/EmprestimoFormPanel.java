package com.biblioteca.presentation;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.biblioteca.domain.Client;
import com.biblioteca.domain.Emprestimo;
import com.biblioteca.domain.Livro;
import com.formdev.flatlaf.FlatLightLaf;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.builder.ButtonBarBuilder;

public class EmprestimoFormPanel extends JPanel{
	
	private EmprestimoPresentationModel emprestimoModel;
	
	private JList<Client> clientList;
	private JList<Livro> booksList;
	
	private JTextField livroSelecionadoField;
	private JTextField clientSelecionadoField;
	
	private JButton lendButton;
	private Runnable onLendRefreshTablePanel;
	
	public EmprestimoFormPanel() {
		initComponents();
		refreshTable();
		initListeners();
		buildUI();
		
	}
	
	public void setOnLendRefreshTablePanel(Runnable refreshTablePanel) {
		this.onLendRefreshTablePanel = refreshTablePanel;
	}
	
	public void initComponents() {
		this.emprestimoModel = new EmprestimoPresentationModel(new Emprestimo());

		this.lendButton = new JButton("Emprestar");
		
		
		
		this.clientList = BasicComponentFactory.createList(emprestimoModel.getClientes());
		clientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.booksList = BasicComponentFactory.createList(emprestimoModel.getLivrosDisponiveis());
		booksList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.clientSelecionadoField = new JTextField();
		clientSelecionadoField.setEditable(false);
		
		this.livroSelecionadoField = new JTextField();
		livroSelecionadoField.setEditable(false);
	}
	
	
	public void buildUI() {
		
		JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
        JLabel titleLabel = new JLabel("Novo Empréstimo");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(new JSeparator(), BorderLayout.SOUTH);
        
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(1, 2, 20, 0));
        listPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        
        
        JScrollPane clientScrollPane = new JScrollPane(clientList);
        clientScrollPane.setBorder(BorderFactory.createTitledBorder("ID - Nome "));
        
        JPanel clientPanel = new JPanel(new BorderLayout(0, 10)); //gap vertical
        clientPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel clientHeader = new JLabel("Clientes");
        clientHeader.setHorizontalAlignment(JLabel.CENTER);
        clientHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        clientHeader.setBorder(new EmptyBorder(0, 0, 5, 0));
        clientPanel.setBorder(new EmptyBorder(20,20,20,20));
        
        JPanel clientFooter = new JPanel(new BorderLayout());
        clientFooter.add(new JLabel("Selecionado: "), BorderLayout.WEST);
        clientFooter.add(clientSelecionadoField, BorderLayout.CENTER);
        
        clientPanel.add(clientHeader, BorderLayout.NORTH);
        clientPanel.add(clientScrollPane, BorderLayout.CENTER);
        clientPanel.add(clientFooter, BorderLayout.SOUTH);
        
        listPanel.add(clientPanel);
        
        
        JScrollPane bookScrollPane = new JScrollPane(booksList);
        bookScrollPane.setBorder(BorderFactory.createTitledBorder("ID - Titulo "));
        
        JPanel livroPanel = new JPanel(new BorderLayout(0, 10));
        livroPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel livroHeader = new JLabel("Livros disponíveis para emprestimo");
        livroHeader.setHorizontalAlignment(JLabel.CENTER);
        livroHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        JPanel bookFooter = new JPanel(new BorderLayout());
        bookFooter.add(new JLabel("Selecionado: "), BorderLayout.WEST);
        bookFooter.add(livroSelecionadoField, BorderLayout.CENTER);
        
        livroPanel.add(livroHeader, BorderLayout.NORTH);
        livroPanel.add(bookScrollPane, BorderLayout.CENTER);
        livroPanel.add(bookFooter, BorderLayout.SOUTH); 
        
        listPanel.add(livroPanel);
        
        
       

        ButtonBarBuilder barBuilder = new ButtonBarBuilder();
        barBuilder.addGlue();
        barBuilder.addButton(lendButton); 

        this.setLayout(new BorderLayout());
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(listPanel, BorderLayout.CENTER);
        this.add(barBuilder.getPanel(), BorderLayout.SOUTH);
        ((JPanel)this.getComponent(2)).setBorder(new EmptyBorder(0, 20, 20, 20));
		
		
	}
	
	public void refreshTable() {
		emprestimoModel.listarClientes();
		emprestimoModel.listarLivros();
		
		clientList.clearSelection();
		booksList.clearSelection();
		
		atualizarBotaoEmprestar();
		
	}
	
	public void initListeners() {
		emprestimoModel.getClientModel().addValueChangeListener(evt -> {
			
			Client clientSelecionado = (Client) evt.getNewValue();
			
			if (clientSelecionado != null) {
				clientSelecionadoField.setText(clientSelecionado.toString());
			} else {
				clientSelecionadoField.setText("");
			}
			
			atualizarBotaoEmprestar();
		});
		
		emprestimoModel.getLivroModel().addValueChangeListener(evt -> {
			
			Livro livroSelecionado = (Livro) evt.getNewValue();
			
			if (livroSelecionado != null) {
				livroSelecionadoField.setText(livroSelecionado.toString());
				} else {
				livroSelecionadoField.setText("");
			}
			
			atualizarBotaoEmprestar();
		});
		
		lendButton.addActionListener(l -> {
			Client clienteAtual = (Client) emprestimoModel.getClientModel().getValue();
			Livro livroAtual = (Livro) emprestimoModel.getLivroModel().getValue();
			try {
				emprestimoModel.realizarEmprestimo(clienteAtual, livroAtual);
				JOptionPane.showMessageDialog(this, "Livro:\n[ " + livroAtual.toString() + " ]\nEmprestado para:\n[ " +
				clienteAtual.toString() + " ]");
				onLendRefreshTablePanel.run();
				refreshTable();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "ERRO ao emprestar livro: \n" + e);
			}
		});
		
	}
	
	private void atualizarBotaoEmprestar() {
		boolean temClient = emprestimoModel.getClientModel().getValue() != null;
		boolean temLivro = emprestimoModel.getLivroModel().getValue() != null;
		lendButton.setEnabled(temLivro && temClient); 
	}
	
	
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));
		} catch (Exception e) {
		}
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("teste");
			frame.add(new EmprestimoFormPanel());
			frame.setSize(800,500);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
	
	

}
