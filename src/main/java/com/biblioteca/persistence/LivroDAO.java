package com.biblioteca.persistence;

import java.util.Collections;
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
			String hql = "FROM Livro l LEFT JOIN FETCH l.autor";
			return session.createQuery(hql, Livro.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		} finally {
			session.close();
		}
	}
	
	public boolean isLivroDisponivel(Livro livro) {
		Session session = sessionBegin();
		try {
			String hql = "SELECT 1 FROM Emprestimo e WHERE e.livro = :livro AND e.dataDevolucaoEfetiva is NULL";
			
			List<?> result = session.createQuery(hql).setParameter("livro", livro).setMaxResults(1).getResultList();
			
			return result.isEmpty();
		
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
		
	}
	

	private Session sessionBegin() {
		return HibernateUtil.getSessionFactory().openSession();
	}
	
	
	
	

}
