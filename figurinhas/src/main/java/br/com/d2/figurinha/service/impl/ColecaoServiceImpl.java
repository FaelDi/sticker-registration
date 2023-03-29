package br.com.d2.figurinha.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.d2.figurinha.model.entity.Figurinha;
import br.com.d2.figurinha.model.entity.dto.ColecaoDTO;
import br.com.d2.figurinha.model.entity.dto.FigurinhaDTO;
import br.com.d2.figurinha.model.entity.dto.FigurinhaFactory;
import br.com.d2.figurinha.model.entity.dto.SlotAlbumDTO;
import br.com.d2.figurinha.model.enumeration.Raridade;
import br.com.d2.figurinha.model.mapper.FigurinhaMapper;
import br.com.d2.figurinha.repository.FigurinhaRepository;
import br.com.d2.figurinha.service.AlbumClientService;
import br.com.d2.figurinha.service.ColecaoService;

@Service
public class ColecaoServiceImpl implements ColecaoService {
	
	private FigurinhaRepository repository;

	private FigurinhaMapper mapper;
	
	private AlbumClientService albumClient;
	
	
	public ColecaoServiceImpl(FigurinhaRepository repository, FigurinhaMapper mapper, AlbumClientService albumClient) {
		this.repository = repository;
		this.mapper = mapper;
		this.albumClient = albumClient;
	}
	
	
	@Override
	public List<FigurinhaDTO> getColecao(String idColecao, String qtd) {
		return mapper.parseListDTO(repository.getPacoteFigurinha(idColecao, qtd));
	}
	

	@Override
	public List<FigurinhaDTO> criarColecao(ColecaoDTO colecao) {
		
		String idColecao = colecao.getIdColecao();
		
		List<Figurinha> listEntity = null;
		if(repository.getVerificaExistenciaColecao(idColecao) > 0) {
			listEntity = repository.findAllByIdColecao(idColecao);
		}else {
			listEntity = criarNovaColecao(idColecao);
		}
		
		return mapper.parseListDTO(listEntity);
		
	}
	
	private List<Figurinha> criarNovaColecao(String idColecao) {
		List<FigurinhaDTO> lista = new ArrayList<>(20);

		lista.addAll(criarFigurinhas(idColecao, Raridade.GRAU_4, 10));
		lista.addAll(criarFigurinhas(idColecao, Raridade.GRAU_3, 6));
		lista.addAll(criarFigurinhas(idColecao, Raridade.GRAU_2, 3));
		lista.addAll(criarFigurinhas(idColecao, Raridade.GRAU_1, 1));
		
		List<SlotAlbumDTO> slots = albumClient.getSlots(idColecao);
		
		for (int i = 0; i < slots.size(); i++) {
			lista.get(i).setId(slots.get(i).getIdentificador().toString());
		}

		List<Figurinha> entityList = mapper.parseListEntity(lista);

		return repository.saveAll(entityList);
	}
	
	private List<FigurinhaDTO> criarFigurinhas(String idColecao, Raridade raridade, int quantidade){
		
		List<FigurinhaDTO> lista = new ArrayList<>(quantidade);
		for (int i = 0; i < quantidade; i++) {
			lista.add(FigurinhaFactory.getFigurinha(raridade, "descrição "+raridade.getDescicao() , "imagem", idColecao));
		}
		
		return lista;
	}


}
