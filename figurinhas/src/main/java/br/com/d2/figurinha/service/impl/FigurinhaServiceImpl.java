package br.com.d2.figurinha.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.d2.figurinha.model.entity.Figurinha;
import br.com.d2.figurinha.model.entity.dto.FigurinhaDTO;
import br.com.d2.figurinha.model.mapper.FigurinhaMapper;
import br.com.d2.figurinha.service.repository.FigurinhaRepository;
import br.com.d2.figurinha.service.FigurinhaService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class FigurinhaServiceImpl implements FigurinhaService {

	private FigurinhaRepository repository;
	private FigurinhaMapper mapper;
	
	public FigurinhaServiceImpl(FigurinhaRepository repository, FigurinhaMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	

	public List<FigurinhaDTO> buscarTodos() {
		return mapper.parseListDTO(repository.findAll());
	}

	public FigurinhaDTO buscarUm(String id) {
		Optional<Figurinha> figurinhaOp = repository.findById(id);
		if (figurinhaOp.isPresent()) {
			Figurinha figurinha = figurinhaOp.get();
			return mapper.parseDTO(figurinha);
		}

		throw new EntityNotFoundException();
	}

	public FigurinhaDTO criar(FigurinhaDTO figurinhaDTO) {
		Figurinha figurinha = mapper.parseEntity(figurinhaDTO);
		figurinha.setId(null);
		repository.save(figurinha);
		return mapper.parseDTO(figurinha);
	}

	public FigurinhaDTO editar(String id, FigurinhaDTO figurinhaDTO) {

		Optional<Figurinha> figurinhaOp = repository.findById(id);

		if (figurinhaOp.isPresent()) {
			Figurinha figurinha = figurinhaOp.get();
			figurinha.setImagem(figurinhaDTO.getImagem());
			figurinha.setIdColecao(figurinhaDTO.getIdColecao());
			figurinha.setDescricao(figurinhaDTO.getDescricao());
			figurinha.setRaridade(figurinhaDTO.getRaridade());
			figurinha.setPreco(figurinhaDTO.getPreco());
			figurinha.setId(id);
			figurinha = repository.save(figurinha);
			return mapper.parseDTO(figurinha);
		}

		throw new EntityNotFoundException();
	}

	public void excluir(String id) {
		if (!repository.existsById(id)) {
			throw new EntityNotFoundException();
		}

		repository.deleteById(id);
	}

}
