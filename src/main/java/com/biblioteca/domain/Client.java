package com.biblioteca.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "client")
public class Client {
	
	@Transient
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "phone", nullable = false)
	private String phone;
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}
	
	
	public Client() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		Long oldId = this.id;
		this.id = id;
		changeSupport.firePropertyChange("id", oldId, id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		changeSupport.firePropertyChange("name", oldName, name);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		String oldEmail = this.email;
		this.email = email;
		changeSupport.firePropertyChange("email", oldEmail, email);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phoneNumber) {
		String oldPhone = this.phone;
		this.phone = phoneNumber;
		changeSupport.firePropertyChange("phone", oldPhone, phoneNumber);
	}
	
}
