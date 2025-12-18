package com.biblioteca.presentation;

import java.util.List;

import com.biblioteca.domain.Client;
import com.biblioteca.persistence.ClientDAO;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.common.bean.Bean;

public class ClientListModel extends Bean {
	
	private SelectionInList<Client> selection;
	private ClientDAO dao;
	
	public ClientListModel() {
		this.selection = new SelectionInList<>();
		this.dao = new ClientDAO();
	}
	
	public void loadClients() {
		List<Client> clients = dao.findAll();
		selection.setList(clients);
		
	}
	
	public void deleteSelection() {
		if (selection.hasSelection()) {
			try {
				dao.delete(selection.getSelection());
				loadClients();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public SelectionInList<Client> getSelection() {
		return selection;
	}

}
