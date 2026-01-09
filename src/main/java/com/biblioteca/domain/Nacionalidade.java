package com.biblioteca.domain;

public enum Nacionalidade {
	BRASILEIRA("Brasileira"),
	ARGENTINA("Argentina"),
	ESTADOUNIDENSE("Estadunidense"),
    PORTUGUESA("Portuguesa"),
    OUTRA("Outra");
	
	private final String descricao;
	
	Nacionalidade(String descricao) {
		this.descricao = descricao;
	}
	
	@Override 
	public String toString() {
		return descricao;
	}

}
