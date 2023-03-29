package br.com.d2.figurinha.album;

import br.com.d2.figurinha.model.entity.dto.SlotAlbumDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("album-api")
public interface AlbumClient {
    @GetMapping("/slots/album/{id}")
    SlotAlbumDTO[] findSlots(@PathVariable UUID id);
}
