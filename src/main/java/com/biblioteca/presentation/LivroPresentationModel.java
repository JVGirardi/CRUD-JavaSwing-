package com.biblioteca.presentation;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import com.biblioteca.domain.Autor;
import com.biblioteca.domain.EnumGenero;
import com.biblioteca.domain.Livro;
import com.biblioteca.persistence.LivroDAO;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;

public class LivroPresentationModel extends PresentationModel<Livro> {
	
	private SelectionInList<Autor> autoresSelection;
	private LivroDAO dao;
	
	
	public LivroPresentationModel(Livro livro) {
		super(livro);
		this.autoresSelection = new SelectionInList<>();
		this.autoresSelection.setSelectionHolder(getAutorModel());
		this.dao = new LivroDAO();
		
	}
	
	public SelectionInList<EnumGenero> getGeneroSelection() {
	    return new SelectionInList<>(EnumGenero.values(), getGeneroModel());
	}
	
	public ValueModel getIdModel() {
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
	
	public SelectionInList<Autor> getAutoresSelection() {
		return autoresSelection;
	}

	public void setListaAutores(List<Autor> autores) {
		this.autoresSelection.setList(autores);
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
	
	public void salvarLivro() throws Exception {
		Livro salvo = dao.saveOrUpdate(this.getBean());
		this.setBean(salvo);
		} 
	
	private boolean validar(String string) {
		return string == null || string.trim().isEmpty();
	}
	
	public void limpar() {
		this.setBean(new Livro());
	}
	
	public void converterImagemParaBytes(File arquivo) {
		try {
			byte[] bytes = Files.readAllBytes(arquivo.toPath());
			getImageModel().setValue(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean validarImagem(File arquivo) {
		if (arquivo == null) {
			return false;
		}
		String tipoDoArquivo = arquivo.getName();
		boolean eJPG = tipoDoArquivo.toLowerCase().endsWith(".jpg");
		boolean png = tipoDoArquivo.toLowerCase().endsWith(".png");
		boolean jpeg = tipoDoArquivo.toLowerCase().endsWith(".jpeg");
		
		if (eJPG || png || jpeg) {
			return true; 			
		}
		return false;
		
	}
	
	public String gerarHashCurto(Object entrada) {
		if (entrada == null) {
			return "000";
		}
		
		int hash = Math.abs(entrada.hashCode() % 1000);
		return String.format("%03d", hash);
	}
	
	
	
	
	
	
	
}
