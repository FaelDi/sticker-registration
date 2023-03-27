package br.com.d2.figurinha.service;

import java.util.List;
import br.com.d2.figurinha.model.entity.dto.ColecaoDTO;
import br.com.d2.figurinha.model.entity.dto.FigurinhaDTO;

public interface ColecaoService {
	
	List<FigurinhaDTO> criarColecao(ColecaoDTO colecao);
	
	List<FigurinhaDTO> getColecao(String idColecao, String qtd);

}
