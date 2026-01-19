package com.biblioteca.presentation;

import java.util.List;

import com.biblioteca.domain.Autor;
import com.biblioteca.persistence.AutorDAO;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.common.bean.Bean;

public class AutorListModel extends Bean {
	
	private SelectionInList<Autor> selection;
	private AutorDAO dao;
	
	public AutorListModel() {
		this.selection = new SelectionInList<>();
		this.dao = new AutorDAO();
	}
	
	
	public void loadAutors() {
		List<Autor> autors = dao.findAll();
		selection.setList(autors);
	}
	
	public void deleteSelection() throws Exception {
		Autor autorDeletar = selection.getSelection();
		dao.delete(autorDeletar);
		loadAutors();
		selection.setSelection(null);
	}
	
	public SelectionInList<Autor> getSelection() {
		return selection;
	}
	

}
