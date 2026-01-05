package com.biblioteca.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.biblioteca.domain.Livro;

public class LivroDAO {
	
	public Livro saveOrUpdate(Livro livro) {
		Session session = sessionBegin();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			Livro managedBook = (Livro) session.merge(livro);
			transaction.commit();
			return managedBook;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Erro ao salvar ou atualizar livro");
		} finally {
			session.close();
		}
	}
	
	public Livro findbyId(Long id) {
		Session session = sessionBegin();
		Transaction transaction = null;
		
		try {
			return session.get(Livro.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	public void delete(Livro livro) {
		Session session = sessionBegin();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.remove(livro);
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
	
	public List<Livro> findAll() {
		Session session = sessionBegin();
		try {
			String hql = "FROM livro l " +
		"JOIN FETCH l.autor " +
					"JOIN FETCH l.genero";
			return session.createQuery(hql, Livro.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	private Session sessionBegin() {
		return HibernateUtil.getSessionFactory().openSession();
	}
	
	
	
	

}
