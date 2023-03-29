package br.com.d2.figurinha.album;

import br.com.d2.figurinha.model.entity.dto.SlotAlbumDTO;
import feign.Feign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
public class AlbumGateway {

    @Autowired
    AlbumClient albumClient;

    @CircuitBreaker(name = "findSlots", fallbackMethod = "findAlbumFallback")
    public SlotAlbumDTO[] findSlots(UUID id){
        return albumClient.findSlots(id);
    }

    private  SlotAlbumDTO[] findAlbumFallback(UUID id, Throwable e){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album não disponivel, tente mais tarde");
    }

}
