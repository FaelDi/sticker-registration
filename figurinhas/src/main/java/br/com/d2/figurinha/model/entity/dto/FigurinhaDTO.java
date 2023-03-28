package br.com.d2.figurinha.model.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FigurinhaDTO {

	private String id;
	private String idColecao;
	private String imagem;
	private String descricao;
	private String raridade;
	private String preco;
}
