package com.biblioteca.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.biblioteca.domain.Genero;

public class GeneroDAO {
	
	public Genero saveOrUpdate(Genero genero) {
		Session session = sessionBegin();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			Genero managedGenero = (Genero) session.merge(genero);
			transaction.commit();
			return managedGenero;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Erro ao salvar ou atualizar genero.");
		} finally {
			session.close();
		}
	}
	
	public Genero findById(Long id) {
		Session session = sessionBegin();
		
		try {
			return session.get(Genero.class, id);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	public void delete(Genero genero) {
		Session session = sessionBegin();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.remove(genero);
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
	
	public List<Genero> findAll() {
		Session session = sessionBegin();
		
		try {
			return session.createQuery("FROM Genero", Genero.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	
	public Session sessionBegin() {
		return HibernateUtil.getSessionFactory().openSession();
	}
	
	

}
