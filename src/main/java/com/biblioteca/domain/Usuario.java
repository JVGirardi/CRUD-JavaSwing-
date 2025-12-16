package com.biblioteca.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

//"avisa" que este é uma classe de banco de dados,
import jakarta.persistence.Transient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//faz com que os @ virem anotacoes do banco de dados


@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //PK

	@Column(name = "login", nullable = false, unique =true)
	private String login;
	
	@Column(name = "senha", nullable = false)
	private String password;
	
	@Transient
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	

	public Usuario() {
		super();
	}

	public Usuario(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		String oldLogin = this.login;
		this.login = login;
		changeSupport.firePropertyChange("login", oldLogin, login);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
