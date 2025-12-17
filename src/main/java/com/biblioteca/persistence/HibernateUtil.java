package com.biblioteca.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	private static SessionFactory buildSessionFactory() {
		try {
			//cria a sessionFactory lendo as configurações do hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
			
		} catch (Throwable ex) {
			System.err.println("Falha na criação da SessionFactory inicial" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	//método que o resto do programa vai chamar p/ pegar a conexão
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	//fecha a conexão qnd o programa encerra
	public static void shutdown() {
		getSessionFactory().close();
	}
	
	

}
