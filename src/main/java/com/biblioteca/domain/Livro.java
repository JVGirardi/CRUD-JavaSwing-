package com.biblioteca.domain;

import java.sql.Types;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "livro")
public class Livro extends AbstractBean{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name= "titulo", nullable = false)
	private String titulo;
	
	@ManyToOne
	@JoinColumn(name= "autor_id") //coluna FK na tabela
	private Autor autor;
	
	//pergunta
	@Column(name = "isbn", nullable = false, unique = true)
	private String isbn;
	
	@Column(name = "year", nullable = false)
	private Integer publicationYear;
	
	@Lob
	@JdbcTypeCode(Types.VARBINARY)
	@Column(name = "image")
	private byte[] capaImagem;
	
	@Enumerated(EnumType.STRING)
	@JoinColumn(name = "genero", nullable = false) 
	private EnumGenero genero;
	
	@OneToMany(mappedBy = "livro")
	private List<Emprestimo> emprestimos;

	
	public Livro() {
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		String oldTitulo = this.titulo;
		this.titulo = titulo;
		changeSupport.firePropertyChange("titulo", oldTitulo, titulo);
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		Autor oldAutor = this.autor;
		this.autor = autor;
		changeSupport.firePropertyChange("autor", oldAutor, autor);
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		String oldIsbn = this.isbn;
		this.isbn = isbn;
		changeSupport.firePropertyChange("isbn", oldIsbn, isbn);
	}

	public Integer getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Integer publicationYear) {
		Integer oldPublicationYear = this.publicationYear;
		this.publicationYear = publicationYear;
		changeSupport.firePropertyChange("year", oldPublicationYear, publicationYear);
	}

	public byte[] getCapaImagem() {
		return capaImagem;
	}

	public void setCapaImagem(byte[] capaImagem) {
		byte[] oldCapaImagem = this.capaImagem;
		this.capaImagem = capaImagem;
		changeSupport.firePropertyChange("image", oldCapaImagem, capaImagem);
	}

	public EnumGenero getGenero() {
		return genero;
	}

	public void setGenero(EnumGenero genero) {
		EnumGenero oldGenero = this.genero;
		this.genero = genero;
		changeSupport.firePropertyChange("genero", oldGenero, genero);
	}
	
}
