package com.biblioteca.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.biblioteca.domain.Client;


public class ClientDAO {
	
	public Client saveOrUpdate(Client client) {
		Session session = session();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			Client managedClient = (Client) session.merge(client);//persist para novos cadastro, merge p para os dois
			transaction.commit();
			return managedClient;
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
	
	
	public Client findById(Long id) {
		Session session = session();
		
		try {
			return session.get(Client.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	public void delete(Client client) {
		Session session = session();
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
		Session session = session();
		try {
			return session.createQuery("FROM Client", Client.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}


	private Session session() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session;
	}

}
	