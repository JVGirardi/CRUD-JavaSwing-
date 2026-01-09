package com.biblioteca.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "client")
public class Client extends AbstractBean{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@OneToMany(mappedBy = "client")
	
	private List<Emprestimo> emprestimos;
	
	public Client() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		Long oldId = this.id;
		this.id = id;
		firePropertyChange("id", oldId, id);
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
