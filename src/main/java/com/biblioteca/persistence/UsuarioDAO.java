package com.biblioteca.persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.biblioteca.domain.Usuario;


public class UsuarioDAO {
	
	public void salvar(Usuario usuario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.persist(usuario);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		
	}
	
	public Usuario findByLogin(String login) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			//from classe Usuario 
			String hql = "FROM Usuario u WHERE u.login = :login";
			
			Query<Usuario> query = session.createQuery(hql, Usuario.class);
			
			query.setParameter("login", login);
			
			return query.uniqueResult();//retorna o objeto Usuario
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	public boolean validarSenha(Usuario usuario, String senhaDigitada) {
		if (usuario == null) return false;
		
		return usuario.getPassword().equals(senhaDigitada);
	}
	
	
}
	
	
	
	
