package com.biblioteca.presentation;

import java.util.ArrayList;
import java.util.List;

import com.biblioteca.domain.Client;
import com.biblioteca.domain.Emprestimo;
import com.biblioteca.domain.Livro;
import com.biblioteca.persistence.EmprestimoDAO;
import com.biblioteca.persistence.EmprestimoService;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.common.bean.Bean;

public class EmprestimoListModel extends Bean {
	
	private final EmprestimoService service;
	private SelectionInList<Emprestimo> selection;
	
	
	public EmprestimoListModel() {
		this.service = new EmprestimoService();
		this.dao = new EmprestimoDAO();
		
		this.clientes = new SelectionInList<Client>(service.listarClientes());
		this.livrosDisponiveis = new SelectionInList<Livro>(service.listarLivrosDisponiveisParaEmprestimo());
	}
	
	public void loadEmprestimosEmAberto() {
		selection.setList(dao.findEmprestimoEmAberto());
	}
	
	public void loadListaLivros() {
		List<Livro> novaLista = service.listarLivrosDisponiveisParaEmprestimo();
		livrosDisponiveis.setList(new ArrayList<>(novaLista));    
	}
	
	public void loadClientes() {
		List<Client> ListaClientes = service.listarClientes();
		clientes.setList(new ArrayList<>(ListaClientes));
	}



	
	
	
	
	
	
	
	
	
	

}
