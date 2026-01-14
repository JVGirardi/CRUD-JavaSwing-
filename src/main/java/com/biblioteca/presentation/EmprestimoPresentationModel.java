package com.biblioteca.presentation;

import java.util.List;

import com.biblioteca.domain.Client;
import com.biblioteca.domain.Emprestimo;
import com.biblioteca.domain.Livro;
import com.biblioteca.persistence.EmprestimoService;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;

public class EmprestimoPresentationModel extends PresentationModel<Emprestimo> {
	
	private EmprestimoService service;
	
	private SelectionInList<Client> clientes;
	private SelectionInList<Livro> livrosDisponiveis;
	
	
	public EmprestimoPresentationModel(Emprestimo emprestimo) {
		super(emprestimo);
		this.service = new EmprestimoService();
	}
	
	public void initModels() {
		List<Client> listaClientes = service.listarClientes();
		List<Livro> listarLivros = service.listarLivrosDisponiveisParaEmprestimo();
		
		clientes = new SelectionInList<Client>(listaClientes);
		livrosDisponiveis = new SelectionInList<Livro>(listarLivros);
		
	}
	
	public ValueModel getIdModel() {
		return getModel("id");
	}
	
	public ValueModel getClientModel() {
		return getModel("client");
	}
	
	public ValueModel getLivroModel() {
		return getModel("livro");
	}
	
	public ValueModel getDataEmprestimo() {
		return getModel("dataEmprestimo");
	}
	
	public ValueModel getDataDevolucaoPrevista() {
		return getModel("dataDevolucaoPrevista");
	}
	
	public ValueModel getDataDevolucaoEfetiva() {
		return getModel("dataDevolucaoEfetiva");
	}
}
