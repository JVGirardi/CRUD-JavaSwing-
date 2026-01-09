package com.biblioteca.persistence;

import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.biblioteca.domain.Autor;

public class AutorDAO {

	public Autor saveOrUpdate(Autor autor) {
		Session session = sessionBegin();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Autor mergedAutor = session.merge(autor);
			transaction.commit();
			return mergedAutor;

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Erro ao salvar ou atualizar autor.");
		} finally {
			session.close();
		}
	}

	public Autor finById(Long id) {
		Session session = sessionBegin();

		try {
			return session.get(Autor.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}

	}

	public void delete(Autor autor) {
		Session session = sessionBegin();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.remove(autor);
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

	public List<Autor> findAll() {
		Session session = sessionBegin();

		try {
			return session.createQuery("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.livros	", Autor.class).list();

		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		} finally {
			session.close();
		}

	}

	private Session sessionBegin() {
		return HibernateUtil.getSessionFactory().openSession();
	}

}
