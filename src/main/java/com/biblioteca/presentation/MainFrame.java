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
		
		clientTablePanel = new ClientTablePanel(new ClientTablePanel.ListListener() {
			
			@Override
			public void onEdit(Client client) {
				clientFormPanel.setClient(client);
				tabbedPane.setSelectedIndex(0);
				
			}
		});
		
		tabbedPane.addTab("Cadastro / Edição", clientFormPanel);
		tabbedPane.addTab("Listagem de clientes", clientTablePanel);
		
	
		
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
