package br.com.d2.figurinha.service.impl;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import br.com.d2.figurinha.model.entity.dto.SlotAlbumDTO;
import br.com.d2.figurinha.service.AlbumClientService;

@Service
public class AlbumClientServiceImpl implements AlbumClientService {

	private RestTemplateBuilder builder;

	public AlbumClientServiceImpl(RestTemplateBuilder builder) {
		this.builder = builder;
	}

	@Override
	public List<SlotAlbumDTO> getSlots(String idAlbum) {
		SlotAlbumDTO[] list = builder.build().getForObject("http://localhost:8087/slots/album/" + idAlbum,
				SlotAlbumDTO[].class);

		return List.of(list);
	}

}
