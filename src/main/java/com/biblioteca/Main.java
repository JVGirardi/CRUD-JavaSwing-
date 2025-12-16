package com.biblioteca;

import com.biblioteca.domain.Usuario;
import com.biblioteca.persistence.HibernateUtil;
import com.biblioteca.persistence.UsuarioDAO;

public class Main {
	public static void main(String[] args) {
		
		UsuarioDAO dao = new UsuarioDAO();
		
		Usuario user = new Usuario("admin", "admin");
		
		try {
			dao.salvar(user);
			System.out.println("Sucesso, usuário cadastrado no banco");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro, talvez o usuário já exista.");
		}
		
		HibernateUtil.shutdown();
		
	}

}
