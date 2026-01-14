package com.biblioteca.persistence;

import java.time.LocalDate;
import java.util.List;

import com.biblioteca.domain.Client;
import com.biblioteca.domain.Emprestimo;
import com.biblioteca.domain.Livro;

public class EmprestimoService {
	private LivroDAO livroDao;
	private EmprestimoDAO emprestimoDao;
	private ClientDAO clientDao;
	
	public EmprestimoService() {
		this.livroDao = new LivroDAO();
		this.emprestimoDao = new EmprestimoDAO();
		this.clientDao = new ClientDAO();
	}
	
	
	public void realizarEmprestimo(Long livroId, Client client) throws Exception {
		Livro livro = livroDao.findbyId(livroId);
		if (livro == null) {
			throw new Exception("Livro não encontrado");
		}
		boolean isLivroDisponivel = emprestimoDao.isLivroDisponivel(livro);
		
		if (!isLivroDisponivel) {
			throw new Exception("Livro indisponível para empréstimo.");
		}
		
		Emprestimo novoEmprestimo = new Emprestimo();
		novoEmprestimo.setLivro(livro);
		novoEmprestimo.setClient(client);
		novoEmprestimo.setDataEmprestimo(LocalDate.now());
		novoEmprestimo.setDataDevolucaoPrevista(LocalDate.now().plusDays(7));
		emprestimoDao.registrarEmprestimo(novoEmprestimo);
	}
	
	public void realizarDevolucao(Emprestimo emprestimo) {
		emprestimo.setDataDevolucaoEfetiva(LocalDate.now());
		emprestimoDao.registrarDevolucao(emprestimo);
	}
	
	public List<Client> listarClientes() {
		return clientDao.findAll();
	}
	
	public List<Livro> listarLivrosDisponiveisParaEmprestimo() {
		return emprestimoDao.findAllLivrosDisponiveis();
	}
	

}
