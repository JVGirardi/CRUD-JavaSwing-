package com.biblioteca.presentation;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.biblioteca.domain.Client;
import com.biblioteca.persistence.ClientDAO;
import com.formdev.flatlaf.FlatLightLaf;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class ClientFormPanel extends JPanel {
	
	private ClientPresentationModel clientModel;
	
	private JTextField nameField;
	private JTextField emailField;
	private JTextField phoneField;
	private JButton registerButton;
	
	public ClientFormPanel() {
		initComponents();
		buildPanel();
		this.setPreferredSize(new Dimension(500, 350));
	}
	
	public void initComponents() {
		clientModel = new ClientPresentationModel(new Client());
		
		nameField = BasicComponentFactory.createTextField(clientModel.getNameModel());
		emailField = BasicComponentFactory.createTextField(clientModel.getEmailModel());
		phoneField = BasicComponentFactory.createTextField(clientModel.getPhoneNumber());
		
		registerButton = new JButton("Registrar");
	}
	
	public void buildPanel() {
		FormLayout layout = new FormLayout("right:pref, 10dlu, fill:100dlu:grow"); //Colunas
    
		
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		
		builder.border(new EmptyBorder(
				20,20,20,20));
		
		builder.append("Nome Completo ", nameField);
		builder.nextLine();
		builder.append("E-mail ", emailField);
		builder.nextLine();
		builder.append("Número de Telefone ", phoneField);
		builder.nextLine();
		JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonBar.add(registerButton);
		builder.append("", buttonBar);
		this.add(builder.getPanel());
		
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
				
				clientModel.setBean(new Client());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
			}
		}); 
		
	}

	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
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
	
	


