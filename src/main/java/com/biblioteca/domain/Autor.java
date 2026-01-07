package com.biblioteca.domain;

import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "autor")
public class Autor extends AbstractBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "nationality", nullable = false)
	private Nacionalidade nationality;
	
	@OneToMany(mappedBy = "autor")
	private List<Livro> livros;
	
	public Autor(String name, Nacionalidade nationality) {
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

	public Nacionalidade getNationality() {
		return nationality;
	}

	public void setNationality(Nacionalidade nationality) {
		Nacionalidade oldNationality = this.nationality;
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
