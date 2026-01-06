package com.biblioteca.presentation;

import com.biblioteca.domain.Autor;
import com.jgoodies.binding.PresentationModel;
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
	
	public void limpar() {
		this.setBean(new Autor());
	}
	
	public String getErrosValidacao() {
		StringBuilder erros = new StringBuilder();
		Autor selecionado = this.getBean();
		
		if (validar(selecionado.getName())) {
			erros.append(" - Nome \n");
		}
		if  (validar(selecionado.getNationality())) {
			erros.append(" - Nacionalidade \n");
		}
		return erros.toString();
	}
	
	public boolean validar(String campo) {
		return campo == null || campo.trim().isEmpty();
	}
	
}
