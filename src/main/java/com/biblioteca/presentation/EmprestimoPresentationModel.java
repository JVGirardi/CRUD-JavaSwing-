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
	private SelectionInList<Livro> livros;
	
	
	public EmprestimoPresentationModel(Emprestimo emprestimo) {
		super(emprestimo);
		this.service = new EmprestimoService();
		
		this.clientes = new SelectionInList<>();
		this.livros = new SelectionInList<>();
		
		this.clientes.setSelectionHolder(getClientModel());
		this.livros.setSelectionHolder(getLivroModel());
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
	
	public void listarClientes() {
		clientes.setList(service.listarClientes());
	}
	
	public void listarLivros() {
		livros.setList(service.listarLivrosDisponiveisParaEmprestimo());
	}

	public SelectionInList<Client> getClientes() {
		return clientes;
	}

	public void setClientes(SelectionInList<Client> clientes) {
		this.clientes = clientes;
	}

	public SelectionInList<Livro> getLivrosDisponiveis() {
		return livros;
	}

	public void setLivrosDisponiveis(SelectionInList<Livro> livrosDisponiveis) {
		this.livros = livrosDisponiveis;
	}

	public void realizarEmprestimo(Client client, Livro livro) throws Exception {
		if (client != null && livro != null) {
			try {
				service.realizarEmprestimo(livro.getId(), client);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new Exception("Por favor, selecione (1) cliente e (1) livro para que seja possivel realizar um emprestimo");
		}
	}
	
	public List<Emprestimo> buscarEmprestimosEmAberto() {
		return service.listarEmprestimosEmAberto();
	}
	
	public void efetivarDevolucao(Emprestimo emprestimo) {
		service.realizarDevolucao(emprestimo);
	}
	
	
	
}


