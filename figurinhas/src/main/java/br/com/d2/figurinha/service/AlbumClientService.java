package br.com.d2.figurinha.service;

import java.util.List;

import br.com.d2.figurinha.model.entity.dto.SlotAlbumDTO;

public interface AlbumClientService {

	List<SlotAlbumDTO> getSlots(String idAlbum);
}
