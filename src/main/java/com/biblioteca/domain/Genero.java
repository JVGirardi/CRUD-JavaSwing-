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
@Table(name = "genero")
public class Genero extends AbstractBean{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;//PK
	
	@Enumerated(EnumType.STRING)
	@Column(name = "nome", nullable = false, unique= true)
	private EnumGeneros nome;
	
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

	public EnumGeneros getNome() {
		return nome;
	}

	public void setNome(EnumGeneros nome) {
		EnumGeneros oldNome = nome;
		this.nome = nome;
		changeSupport.firePropertyChange("nome", oldNome, nome);
	}

	public Genero(EnumGeneros nome) {
		super();
		this.nome = nome;
	}
	
	

}
