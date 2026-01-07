package com.biblioteca.domain;

public enum EnumGeneros {
	ROMANCE("Romance"),
	FICCAO("Ficcao"),
	SUSPENSE("Suspense"),
	TERROR("Terror"),
	AVENTURA("Aventura"),
	HUMOR("Humor"),
	OUTRO("Outro");
	
	private final String descricao;
	
	EnumGeneros(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return descricao;
	}
	
	
}
