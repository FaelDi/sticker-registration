package br.com.d2.figurinha.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.d2.figurinha.model.entity.dto.ColecaoDTO;
import br.com.d2.figurinha.model.entity.dto.FigurinhaDTO;
import br.com.d2.figurinha.service.ColecaoService;
import br.com.d2.figurinha.service.FigurinhaService;

public class FigurinhaControllerTest {

    @Mock
    private FigurinhaService figurinhaService;

    @Mock
    private ColecaoService colecaoService;

    @InjectMocks
    private FigurinhaController figurinhaController;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCriarColecao() {
        ColecaoDTO colecao = new ColecaoDTO();
        FigurinhaDTO figurinha1 = new FigurinhaDTO();
        FigurinhaDTO figurinha2 = new FigurinhaDTO();
        List<FigurinhaDTO> figurinhas = new ArrayList<>();
        figurinhas.add(figurinha1);
        figurinhas.add(figurinha2);
        when(colecaoService.criarColecao(any())).thenReturn(figurinhas);
        ResponseEntity<List<FigurinhaDTO>> response = figurinhaController.criarColecao(colecao);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(figurinhas, response.getBody());
    }

    @Test
    public void testGetColecao() {
        String idColecao = "1";
        String qtd = "10";
        FigurinhaDTO figurinha1 = new FigurinhaDTO();
        FigurinhaDTO figurinha2 = new FigurinhaDTO();
        List<FigurinhaDTO> figurinhas = new ArrayList<>();
        figurinhas.add(figurinha1);
        figurinhas.add(figurinha2);
        when(colecaoService.getColecao(anyString(), anyString())).thenReturn(figurinhas);
        ResponseEntity<List<FigurinhaDTO>> response = figurinhaController.getColecao(idColecao, qtd);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(figurinhas, response.getBody());
    }
}
