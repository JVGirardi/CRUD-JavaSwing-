package com.biblioteca.presentation;

import com.biblioteca.domain.Client;
import com.biblioteca.persistence.ClientDAO;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ConverterFactory;
import com.jgoodies.binding.value.ValueModel;

public class ClientPresentationModel extends PresentationModel<Client> {
	
	private static final long serialVersionUID = 1L;
	
	public ClientPresentationModel(Client client) {
		super(client);
	}
	
	public ValueModel getNameModel() {
		return getModel("name");
	}
	
	public ValueModel getEmailModel() {
		return getModel("email");
	}
	
	public ValueModel getPhoneNumber() {
		return getModel("phone");
	}
	
	public ValueModel getIdModel() {
		return getModel("id");
	}
	
	
	public String getErrosValidacao() {
		StringBuilder erros = new StringBuilder();
		Client client = getBean();
		
		if (verificar(client.getName())) {
			erros.append(" - Nome Completo \n");
		}
		if (verificar(client.getEmail())) {
			erros.append(" - E-Mail \n");
		}
		if (verificar(client.getPhone())) {
			erros.append(" - Telefone Celular \n");
		}
		return erros.toString();
	}
	
	public void salvar() {
		ClientDAO dao = new ClientDAO();
		Client savedClient = dao.saveOrUpdate(this.getBean());
		//atualizar o campo ID
		this.setBean(savedClient);
	}
	
	public void limpar() {
		this.setBean(new Client());
	}

	private boolean verificar(String string) {
		return string == null || string.trim().isEmpty();
	}
	
}
