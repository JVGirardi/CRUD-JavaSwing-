package com.biblioteca.presentation;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.biblioteca.domain.Usuario;
import com.biblioteca.persistence.UsuarioDAO;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class LoginViewFrame extends JPanel {
	
	private LoginPresentationModel loginModel;
	
	private JTextField loginField;
	private JPasswordField passwordField;
	private JButton enterButton;
	
	public LoginViewFrame() { 
		initComponents();
		buildUI();
		this.setPreferredSize(new Dimension(400, 200));
		
	}
	
	private void initComponents() {
		loginModel = new LoginPresentationModel();
		
		loginField = BasicComponentFactory.createTextField(loginModel.getPresenter().getModel("login"));
		
		passwordField = new JPasswordField(20);
		enterButton = new JButton("Entrar");
	}
	
	private void buildUI() {
		
		FormLayout layout = new FormLayout(
                "right:pref, 4dlu, fill:pref:grow", //Colunas
                "pref, 25dlu, pref, 10dlu, pref"); //Linhas
		
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		
		builder.border(new EmptyBorder(20, 20, 20 , 20));
		
		builder.append("Login: ", loginField);
		builder.nextLine();
		builder.append("Senha: ", passwordField);
		builder.nextLine();
		builder.append(enterButton, 3);
		
		this.add(builder.getPanel());
		
		enterButton.addActionListener(e -> {
			
			boolean sucesso = loginModel.tentarLogar(new String(passwordField.getPassword()));
			
			Usuario usuario = new Usuario();
			
			if (sucesso) {
				String loginValido = loginModel.getUsuarioBean().getLogin();
				
				JOptionPane.showMessageDialog(this, "Sucesso, bem vindo! " + loginValido);
				//tela principal
			} else {
				JOptionPane.showMessageDialog(this, "Login ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
			}	
		});
	}
	
	public static void main(String[] args) {
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		} catch (Exception e) {
		}
		
		SwingUtilities.invokeLater(() -> {
			
			JFrame frame = new JFrame("teste");
			frame.add(new LoginViewFrame());
			frame.pack();
			frame.setResizable(false);
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
