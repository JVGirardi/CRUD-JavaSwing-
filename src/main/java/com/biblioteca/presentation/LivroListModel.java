package com.biblioteca.presentation;

import java.util.List;

import com.biblioteca.domain.Autor;
import com.biblioteca.domain.Emprestimo;
import com.biblioteca.domain.Livro;
import com.biblioteca.persistence.AutorDAO;
import com.biblioteca.persistence.LivroDAO;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.common.bean.Bean;


public class LivroListModel extends Bean {
	
	private static final long serialVersionUID = 1L;
	
	
	private SelectionInList<Livro> selection;
	private final LivroDAO dao;
	
	private SelectionInList<Autor> autoresList;
	private final AutorDAO autorDAO;
	
	
	public LivroListModel() {
		this.selection = new SelectionInList<Livro>();
		this.dao = new LivroDAO();
		this.autoresList = new SelectionInList<Autor>();
		this.autorDAO = new AutorDAO();
		
	}
	
	public void loadAutores() {
		List<Autor> autores = autorDAO.findAll();
		autoresList.setList(autores);
	}
	
	public void loadLivros() {
		List<Livro> livros = dao.findAll();
		selection.setList(livros);
	}
	
	public void deleteSelection() throws Exception {
		dao.delete(selection.getSelection());
		loadLivros();
		selection.setSelection(null);
	}
	
	public boolean isLivroDisponivel(Livro livro) {
		return dao.isLivroDisponivel(livro);
	}
	
	public SelectionInList<Livro> getSelection() {
		return selection;
	}

	public SelectionInList<Autor> getAutoresList() {
		return autoresList;
	}

	public void setAutoresList(SelectionInList<Autor> autoresList) {
		this.autoresList = autoresList;
	}
	

}
