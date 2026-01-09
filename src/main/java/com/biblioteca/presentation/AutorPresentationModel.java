package com.biblioteca.presentation;

import com.biblioteca.domain.Autor;
import com.biblioteca.domain.Nacionalidade;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;

public class AutorPresentationModel extends PresentationModel<Autor> {
	
	private static final long serialVersionUID = 1L; 
	
	public AutorPresentationModel(Autor autor) {
		super(autor);
	}
	
	public ValueModel getNameModel() {
		return getModel("name");
	}
	
	public ValueModel getNationalityModel() {
		return getModel("nationality");
	}
	
	public SelectionInList<Nacionalidade> getNacionalidadeSelection() {
	    // Cria uma lista de seleção com todas as opções do Enum e vincula ao modelo da nacionalidade
	    return new SelectionInList<>(Nacionalidade.values(), getNationalityModel());
	}
	
	public ValueModel getIdModel() {
		return getModel("id");
	}
	
	public void limpar() {
		this.setBean(new Autor());
	}
	
	public String getErrosValidacao() {
		StringBuilder erros = new StringBuilder();
		Autor selecionado = this.getBean();
		
		if (validar(selecionado.getName())) {
			erros.append(" - Nome \n");
		}
		if  (selecionado.getNationality() == null) {
			erros.append(" - Nacionalidade \n");
		}
		return erros.toString();
	}
	
	public boolean validar(String campo) {
		return campo == null || campo.trim().isEmpty();
	}
	
}
