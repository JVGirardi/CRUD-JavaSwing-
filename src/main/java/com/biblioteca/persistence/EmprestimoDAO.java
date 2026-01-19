package com.biblioteca.persistence;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.biblioteca.domain.Emprestimo;
import com.biblioteca.domain.Livro;

public class EmprestimoDAO {
	
	public Emprestimo registrarEmprestimo(Emprestimo emprestimo) {
		Session session = sessionBegin();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Emprestimo emprestimoMerged = session.merge(emprestimo);
			transaction.commit();
			return emprestimoMerged;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Erro ao salvar emprestimo. ");
		} finally {
			session.close();
		}
	}
	
	public void registrarDevolucao(Emprestimo emprestimo) {
		Session session = sessionBegin();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.merge(emprestimo);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new RuntimeException("Erro ao salvar data efetiva de devolução.");
		} finally {
			session.close();
		}
	}
	
	public List<Emprestimo> findEmprestimoEmAberto() {
		Session session = sessionBegin();
		try {
			return session.createQuery("SELECT e FROM Emprestimo e "
										+ "JOIN FETCH e.client "
										+ "JOIN FETCH e.livro "
										+ "WHERE e.dataDevolucaoEfetiva IS NULL", Emprestimo.class).list();
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
	
	public List<Livro> findAllLivrosDisponiveis() {
		Session session = sessionBegin();
		try {
			String hql = "FROM Livro l WHERE l NOT IN (SELECT e.livro FROM Emprestimo e WHERE e.dataDevolucaoEfetiva IS NULL)";
			return session.createQuery(hql, Livro.class).list();
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
