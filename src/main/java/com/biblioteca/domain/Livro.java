package com.biblioteca.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "livro")
public class Livro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name= "titulo", nullable = false)
	private String titulo;
	
	@ManyToOne
	@JoinColumn(name= "autor_id") //coluna FK na tabela
	private Autor autor;
	
	@Column(name = "isbn", nullable = false, unique = true)
	private String isbn;
	
	@Column(name = "year", nullable = false)
	private Integer publicationYear;
	
	@Lob
	@Column(name = "image")
	private byte[] capaImagem;
	
	@ManyToOne
	@JoinColumn(name = "genero_id") //coluna FK na tabela
	private Genero genero;

	public Livro() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}

	public byte[] getCapaImagem() {
		return capaImagem;
	}

	public void setCapaImagem(byte[] capaImagem) {
		this.capaImagem = capaImagem;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	
	
	
	

}
