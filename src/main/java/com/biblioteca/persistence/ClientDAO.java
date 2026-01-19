package com.biblioteca.persistence;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.biblioteca.domain.Client;
import com.biblioteca.domain.Livro;

import jakarta.transaction.Transactional;


public class ClientDAO {
	
	public Client saveOrUpdate(Client client) {
		Session session = sessionBegin();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			Client mergedClient = (Client) session.merge(client);//persist para novos cadastro, merge p para os dois
			transaction.commit();
			return mergedClient;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Erro ao salvar ou atualizar cliente.");
		} finally {
			session.close();
		}
	}
	
	public boolean existeHistorico(Long id) {
		Session session = sessionBegin();
		
		try {
			String hql = "SELECT COUNT(e) FROM Emprestimo e WHERE e.client.id = :id";
			
			Long count = session.createQuery(hql, Long.class).setParameter("id", id).uniqueResult();
			
			return count > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return true;
		} finally {
			session.close();
		}
	}
	
	
	public Client findById(Long id) {
		Session session = sessionBegin();
		
		try {
			return session.get(Client.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	@Transactional
	public void delete(Client client) {
		Session session = sessionBegin();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			session.remove(client);
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
	
	public List<Client> findAll() {
		Session session = sessionBegin();
		try {
			return session.createQuery("FROM Client", Client.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		} finally {
			session.close();
		}
	}
	
	
	public boolean IsClientDisponivel(Client client) {
		Session session = sessionBegin();
		try {
			String hql = "SELECT 1 FROM Emprestimo e WHERE e.client = :client AND e.dataDevolucaoEfetiva IS NULL";
			
			List<?> result = session.createQuery(hql).setParameter("client", client).setMaxResults(1).getResultList();
			return result.isEmpty();
			//retorna false quando há um emprestimo em aberto vinculado ao cliente
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}

	private Session sessionBegin() {
		Session sessionBegin = HibernateUtil.getSessionFactory().openSession();
		return sessionBegin;
	}

}
	