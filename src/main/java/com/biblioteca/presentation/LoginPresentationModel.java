package com.biblioteca.presentation;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.biblioteca.domain.Usuario;
import com.biblioteca.persistence.UsuarioDAO;
import com.jgoodies.binding.PresentationModel;

public class LoginPresentationModel {
	
	private PresentationModel<Usuario> presenter;
	private Usuario usuarioBean;
	private UsuarioDAO dao;
	
	
	public LoginPresentationModel() {
		initComponents();
		
	}
	
	public void initComponents() {
		usuarioBean = new Usuario();
		presenter = new PresentationModel<Usuario>(usuarioBean); //faz a ligacao entre o model e a view
		dao = new UsuarioDAO();
		
	}
	
	public boolean tentarLogar(String senhaDigitada) {
		String loginDigitado = usuarioBean.getLogin();
		
		Usuario usuario = dao.findByLogin(loginDigitado);
		
		return (usuario != null && dao.validarSenha(usuario, senhaDigitada));
		
	}
	
	public void limparCampos() {
		presenter.getModel("login").setValue(null);
	}

	public PresentationModel<Usuario> getPresenter() {
		return presenter;
	}
	
	public void setPresenter(PresentationModel<Usuario> presenter) {
		this.presenter = presenter;
	}

	public Usuario getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(Usuario usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	
	
	
	

}
