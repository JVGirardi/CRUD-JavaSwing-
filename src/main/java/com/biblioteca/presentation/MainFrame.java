package com.biblioteca.presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.biblioteca.domain.Client;
import com.formdev.flatlaf.FlatLightLaf;



public class MainFrame extends JFrame {
	
	private CardLayout cardLayout;
	private JPanel mainPanel;
	
	private LoginPanel loginPanel;
	private ClientFormPanel clientFormPanel;
	private ClientTablePanel clientTablePanel;
	private AutorFormPanel autorFormPanel;
	private AutorTablePanel autorTablePanel;
	
	private JTabbedPane tabbedPane;
	
	public MainFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 600);
		this.setLocationRelativeTo(null);
		initComponents();
		buildUI();	
	}
	
	
	public void initComponents() {
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		loginPanel = new LoginPanel();
		
		loginPanel.setOnLoginSuccess(() -> {
			cardLayout.show(mainPanel, "Sistema");
		});
		
		tabbedPane = new JTabbedPane();
		
		clientFormPanel = new ClientFormPanel();
		
		clientFormPanel.setOnSaveCallBack(() -> {
			clientTablePanel.refreshTable();
			tabbedPane.setSelectedIndex(1);
		});
		
		clientTablePanel = new ClientTablePanel(c -> {
			clientFormPanel.setClient(c);
			tabbedPane.setSelectedIndex(0);
				
			});
		
		tabbedPane.addTab("Cadastro / Edição de Clientes", clientFormPanel);
		tabbedPane.addTab("Listagem de Clientes", clientTablePanel);
		
		
		autorFormPanel = new AutorFormPanel();
		
		autorFormPanel.setOnSaveCallBack(() -> {
			autorTablePanel.refreshTable();
			tabbedPane.setSelectedIndex(2);
		});
		
		autorTablePanel = new AutorTablePanel(a -> { //a = listModel.getSelection().getValue()
			autorFormPanel.setAutor(a);
			tabbedPane.setSelectedIndex(3);
		});
		
		
		
		tabbedPane.addTab("Listagem de autores", autorTablePanel);
		tabbedPane.addTab("Cadastro / Edição de autores", autorFormPanel);
		
		
	
		
	}
	
	public void buildUI() {
		mainPanel.add(loginPanel, "Login");
		mainPanel.add(tabbedPane, "Sistema");
		
		this.add(mainPanel);
		
		cardLayout.show(mainPanel, "Login");
		
	}
	
	public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
	
	
	
	
}
