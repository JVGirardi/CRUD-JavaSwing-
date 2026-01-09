package com.biblioteca.presentation;

import java.util.List;

import com.biblioteca.domain.Client;
import com.biblioteca.domain.Livro;
import com.biblioteca.persistence.ClientDAO;
import com.biblioteca.persistence.LivroDAO;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.common.bean.Bean;

public class LivroListModel extends Bean {
	
	private SelectionInList<Livro> selection;
	private LivroDAO dao;
	
	public LivroListModel() {
		this.selection = new SelectionInList<Livro>();
		this.dao = new LivroDAO();
	}
	
	public void loadLivros() {
		List<Livro> livros = dao.findAll();
		selection.setList(livros);
	}
	
	public void deleteSelection() throws Exception {
		if (selection.hasSelection()) {
			Livro livroDeletar = selection.getValue();
			if (livroDeletar == null) {
				throw new Exception("Selecione um livro na tabela para excluir.");
			} else {
				dao.delete(livroDeletar);
				loadLivros();
				selection.setSelection(null);
			}
			
		}
	}
	
	public SelectionInList<Livro> getSelection() {
		return selection;
	}
	

}
