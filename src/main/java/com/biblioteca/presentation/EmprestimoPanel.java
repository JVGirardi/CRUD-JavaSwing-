package com.biblioteca.presentation;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.biblioteca.domain.Client;
import com.biblioteca.domain.Emprestimo;
import com.biblioteca.domain.Livro;
import com.formdev.flatlaf.FlatLightLaf;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class EmprestimoPanel extends JPanel{
	
	private EmprestimoPresentationModel emprestimoModel;
	private EmprestimoListModel listModel;
	
	private JList<Client> clientList;
	private JList<Livro> booksList;
	
	private JButton lendButton;
	
	public EmprestimoPanel() {
		initComponents();
		buildUI();
		
	}
	
	public void initComponents() {
		this.listModel = new EmprestimoListModel();
		this.emprestimoModel = new EmprestimoPresentationModel(new Emprestimo());

		this.lendButton = new JButton("Emprestar");
		
		
		this.clientList = BasicComponentFactory.createList(listModel.loadClientes());
		
		
		
		
		this.booksList = new JList();
	}
	
	
	public void buildUI() {
		JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
        JLabel titleLabel = new JLabel("Novo Empréstimo");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(new JSeparator(), BorderLayout.SOUTH);
        
   
        FormLayout layout = new FormLayout("right:pref, 10dlu, fill:150dlu:grow");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.border(new EmptyBorder(20, 20, 20, 20));
        
        builder.appendSeparator("Seleção de Dados");
        
        builder.append("Cliente:", new JScrollPane(clientList)); 
        builder.nextLine();
        
        builder.append("Livro Disponível:", new JScrollPane(booksList));
        builder.nextLine();

        ButtonBarBuilder barBuilder = new ButtonBarBuilder();
        barBuilder.addGlue();
        barBuilder.addButton(lendButton); 

        this.setLayout(new BorderLayout());
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(builder.getPanel(), BorderLayout.CENTER);
        this.add(barBuilder.getPanel(), BorderLayout.SOUTH);
        ((JPanel)this.getComponent(2)).setBorder(new EmptyBorder(0, 20, 20, 20));
		
		
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));
		} catch (Exception e) {
		}
		SwingUtilities.invokeLater(() -> {
			
			JFrame frame = new JFrame("teste");
			frame.add(new EmprestimoPanel());
			frame.pack();
			frame.setResizable(false);
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
	
	

}
