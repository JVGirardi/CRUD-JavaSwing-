package com.biblioteca.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.biblioteca.domain.Client;
import com.biblioteca.persistence.ClientDAO;
import com.formdev.flatlaf.FlatLightLaf;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class ClientFormPanel extends JPanel {
	
	private ClientPresentationModel clientModel;
	
	private Runnable onSaveCallback;
	
	private JTextField nameField;
	private JTextField emailField;
	private JTextField phoneField;
	private JButton registerButton;
	private JButton cleanButton;
	
	public ClientFormPanel() {
		super(new BorderLayout());
		initComponents();
		initListeners();
		buildPanel();
		this.setPreferredSize(new Dimension(600, 400));
	}
	
	public void setOnSaveCallBack(Runnable onSaveCallback) {
		this.onSaveCallback = onSaveCallback;
	}
	
	public void setClient(Client client) {
		this.clientModel.setBean(client);
	}
	
	
	public void initComponents() {
		clientModel = new ClientPresentationModel(new Client());
		
		nameField = BasicComponentFactory.createTextField(clientModel.getNameModel());
		emailField = BasicComponentFactory.createTextField(clientModel.getEmailModel());
		phoneField = BasicComponentFactory.createTextField(clientModel.getPhoneNumber());
		
		registerButton = new JButton("Salvar");
		cleanButton = new JButton("Cancelar");
	}
	
	public void buildPanel() {
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
		
		JLabel titleLabel = new JLabel("Cadastro de Cliente");
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		titleLabel.setForeground(new Color(50, 50, 50));//cinza escuro
		
		headerPanel.add(titleLabel, BorderLayout.WEST);
		headerPanel.add(new JSeparator(), BorderLayout.SOUTH);
		
		FormLayout layout = new FormLayout("right:pref, 10dlu, fill:100dlu:grow"); //Colunas
		
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		
		builder.border(new EmptyBorder(
				20,20,20,20));
		
		builder.appendSeparator("Dados pessoais");
		builder.nextLine();
		
		builder.append("Nome Completo ", nameField);
		builder.nextLine();
		
		builder.appendSeparator("Contato");
		builder.nextLine();
		
		builder.append("E-mail ", emailField);
		builder.nextLine();
		
		builder.append("Número de Telefone ", phoneField);
		builder.nextLine();
		
		
		ButtonBarBuilder btnBuilder = new ButtonBarBuilder();
		btnBuilder.addGlue(); //empurra tudo para a direita
		btnBuilder.addButton(registerButton);
		//btnBuilder.addRelatedGap(); 
		
		JPanel buttonPanel = btnBuilder.getPanel();
		buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		this.add(headerPanel, BorderLayout.NORTH);
		this.add(builder.getPanel(), BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void initListeners() {
		
		registerButton.addActionListener(e -> {
			if (!clientModel.getErrosValidacao().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Os seguintes campos não foram preenchidos corretamente: \n" +
			clientModel.getErrosValidacao(), "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			try {
			
				ClientDAO dao = new ClientDAO();
				
				dao.saveOrUpdate(clientModel.getBean());
				
				JOptionPane.showMessageDialog(this, "Cliente " + clientModel.getNameModel().getValue() + 
						" \n cadastrado com sucesso!");
				
				if (onSaveCallback != null) {
					onSaveCallback.run();
				}
				
				clientModel.setBean(new Client());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
			}
		}); 
		
		cleanButton.addActionListener(e -> {
			
			if (clientModel.getBean().getId() != null || !clientModel.getNameModel().getValue().toString().isEmpty()) {
			
				clientModel.setBean(new Client());
			};
		});
		
	}

	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));
		} catch (Exception e) {
		}
		SwingUtilities.invokeLater(() -> {
			
			JFrame frame = new JFrame("teste");
			frame.add(new ClientFormPanel());
			frame.pack();
			frame.setResizable(false);
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
	
}
	
	


