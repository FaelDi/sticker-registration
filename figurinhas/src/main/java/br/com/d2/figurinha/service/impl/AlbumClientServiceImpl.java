package br.com.d2.figurinha.service.impl;

import java.util.List;
import java.util.UUID;

import br.com.d2.figurinha.album.AlbumClient;
import br.com.d2.figurinha.album.AlbumGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import br.com.d2.figurinha.model.entity.dto.SlotAlbumDTO;
import br.com.d2.figurinha.service.AlbumClientService;

@Service
public class AlbumClientServiceImpl implements AlbumClientService {

	private RestTemplateBuilder builder;


	private AlbumGateway albumGateway;

	public AlbumClientServiceImpl(RestTemplateBuilder builder, AlbumGateway albumGateway) {
		this.builder = builder;
		this.albumGateway = albumGateway;
	}

	@Override
	public List<SlotAlbumDTO> getSlots(UUID idAlbum) {
		SlotAlbumDTO[] list = albumGateway.findSlots(idAlbum);

		return List.of(list);
	}

}
