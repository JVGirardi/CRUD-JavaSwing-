package com.biblioteca.presentation;

import com.biblioteca.domain.Client;
import com.biblioteca.domain.Livro;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueModel;

public class LivroPresentationModel extends PresentationModel<Livro> {
	
	public LivroPresentationModel(Livro livro) {
		super(livro);
	}
	
	public ValueModel getId() {
		return getModel("id");
	}
	
	public ValueModel getTituloModel() {
		return getModel("titulo");
	}
	
	public ValueModel getAutorModel() {
		return getModel("autor");
	}
	
	public ValueModel getIsbnModel() {
		return getModel("isbn");
	}
	
	public ValueModel getYearModel() {
		return getModel("publicationYear");
	}
	
	public ValueModel getImageModel() {
		return getModel("capaImagem");
	}
	
	public ValueModel getGeneroModel() {
		return getModel("genero");
	}
	
	public String getErrosValidacao() {
		StringBuilder erros = new StringBuilder();
		Livro livro = this.getBean();
		if (validar(livro.getTitulo())) {
			erros.append(" - Titulo \n");
		}
		if (livro.getAutor() == null) {
			erros.append(" - Autor \n");
		}
		if (validar(livro.getIsbn())) {
			erros.append(" - ISBN \n");
		}
		if (livro.getPublicationYear() == null) {
			erros.append(" - Ano \n");
		}
		if (livro.getCapaImagem() == null) {
			erros.append(" - Imagem de capa \n");
		}
		if (livro.getGenero() == null) {
			erros.append(" - Genero \n");
		}
		return erros.toString();
	}
	
	private boolean validar(String string) {
		return string == null || string.trim().isEmpty();
	}
	
	public void limpar() {
		this.setBean(new Livro());
	}
	
}
