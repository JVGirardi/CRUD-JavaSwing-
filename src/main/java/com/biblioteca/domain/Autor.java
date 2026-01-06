package com.biblioteca.domain;

import java.beans.PropertyChangeSupport;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "autor")
public class Autor extends AbstractBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "nationality", nullable = false)
	private String nationality;
	
	@OneToMany(mappedBy = "autor")
	private List<Livro> livros;
	
	public Autor(String name, String nationality) {
		super();
		this.name = name;
		this.nationality = nationality;
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

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		String oldNationality = this.nationality;
		this.nationality = nationality;
		changeSupport.firePropertyChange("nationality", oldNationality, nationality);
	}
	
	

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public Autor() {
		super();
	}
	
	
}
