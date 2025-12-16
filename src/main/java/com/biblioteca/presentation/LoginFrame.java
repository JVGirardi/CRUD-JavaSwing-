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

public class LoginFrame extends JPanel {
	
	private PresentationModel<Usuario> model;
	private Usuario usuarioBean;
	
	private JTextField loginField;
	private JPasswordField passwordField;
	private JButton enterButton;
	
	public LoginFrame() { 
		usuarioBean = new Usuario(); 
		model = new PresentationModel<Usuario>(usuarioBean); //faz o papel do presenter, ligação entre model(usuário e a view)
		
		initComponents();
		buildUI();
		this.setPreferredSize(new Dimension(400, 200));
		
	}
	
	private void initComponents() {
		loginField = BasicComponentFactory.createTextField(model.getModel("login"));
		
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
			fazerLogin();
		});
	}
	
	public void fazerLogin() {
		String loginDigitado = usuarioBean.getLogin();
		String passwordDigitada = new String(passwordField.getPassword());
		
		UsuarioDAO dao = new UsuarioDAO();
		
		Usuario usuario = dao.findByLogin(loginDigitado);
		
		if (usuario != null && dao.validarSenha(usuario, passwordDigitada)) {
			JOptionPane.showMessageDialog(this, "Sucesso, bem vindo! " + usuario.getLogin());
		} else {
			JOptionPane.showMessageDialog(this, "Login ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	public static void main(String[] args) {
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
		
		SwingUtilities.invokeLater(() -> {
			
			JFrame frame = new JFrame("teste");
			frame.add(new LoginFrame());
			frame.pack();
			frame.setResizable(false);
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
	

}
