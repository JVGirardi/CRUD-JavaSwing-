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
	
	public ValueModel getId() {
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
	
	public void limpar() {
		this.setBean(new Client());
	}

	private boolean verificar(String string) {
		return string == null || string.trim().isEmpty();
	}
	
}
