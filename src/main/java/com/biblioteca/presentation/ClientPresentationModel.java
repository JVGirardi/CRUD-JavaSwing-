package com.biblioteca.presentation;

import com.biblioteca.domain.Client;
import com.jgoodies.binding.PresentationModel;
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
	
	public String getErrosValidacao() {
		StringBuilder erros = new StringBuilder();
		Client client = getBean();
		
		if (client.getName() == null || client.getName().trim().isEmpty()) {
			erros.append(" - Nome Completo \n");
		}
		if (client.getEmail() == null || client.getEmail().trim().isEmpty()) {
			erros.append(" - E-Mail \n");
		}
		if (client.getPhone() == null || client.getPhone().trim().isEmpty()) {
			erros.append(" - Telefone Celular \n");
		}
		return erros.toString();
	}
	
}
