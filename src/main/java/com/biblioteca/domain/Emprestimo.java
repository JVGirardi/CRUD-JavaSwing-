package com.biblioteca.domain;



import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "emprestimo")
public class Emprestimo extends AbstractBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)//FK
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;
	
	@ManyToOne(fetch = FetchType.LAZY)//FK
	@JoinColumn(name = "livro_id", nullable = false)
	private Livro livro;
	
	@Column(name = "data_emprestimo", nullable = false)
	private LocalDate dataEmprestimo;

	@Column(name = "data_dev_prevista", nullable = false)
	private LocalDate dataDevolucaoPrevista;
	
	@Column(name = "data_dev_efetiva")
	private LocalDate dataDevolucaoEfetiva;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		Long oldId = this.id;
		this.id = id;
		changeSupport.firePropertyChange("id", oldId, id);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		Client oldClient = this.client;
		this.client = client;
		changeSupport.firePropertyChange("client", oldClient, client);
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		Livro oldLivro = this.livro;
		this.livro = livro;
		changeSupport.firePropertyChange("livro", oldLivro, livro);
	}

	public LocalDate getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(LocalDate dataEmprestimo) {
		LocalDate oldDataEmprestimo = this.dataEmprestimo;
		this.dataEmprestimo = dataEmprestimo;
		changeSupport.firePropertyChange("dataEmprestimo", oldDataEmprestimo, dataEmprestimo);
	}

	public LocalDate getDataDevolucaoPrevista() {
		return dataDevolucaoPrevista;
	}

	public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
		LocalDate oldDataDevolucaoPrevista = this.dataDevolucaoPrevista;
		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
		changeSupport.firePropertyChange("dataDevolucaoPrevista", oldDataDevolucaoPrevista, dataDevolucaoPrevista);
	}

	public LocalDate getDataDevolucaoEfetiva() {
		return dataDevolucaoEfetiva;
	}

	public void setDataDevolucaoEfetiva(LocalDate dataDevolucaoEfetiva) {
		LocalDate oldDataDevolucaoEfetiva = this.dataDevolucaoEfetiva;
		this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
		changeSupport.firePropertyChange("dataDevolucaoEfetiva", oldDataDevolucaoEfetiva, dataDevolucaoEfetiva);
	}
	
	
	
	

}
