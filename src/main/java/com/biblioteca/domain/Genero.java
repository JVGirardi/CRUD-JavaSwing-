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
@Table(name = "genero")
public class Genero extends AbstractBean{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;//PK
	
	@Column(name = "nome", nullable = false, unique= true)
	private String nome;
	
	@OneToMany(mappedBy = "genero")
	private List<Livro> livros;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		Long oldId = id;
		this.id = id;
		changeSupport.firePropertyChange("id", oldId, id);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		String oldNome = nome;
		this.nome = nome;
		changeSupport.firePropertyChange("nome", oldNome, nome);
	}

	public Genero(String nome) {
		super();
		this.nome = nome;
	}
	
	

}
