package br.com.d2.figurinha.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.d2.figurinha.model.entity.Figurinha;
import br.com.d2.figurinha.model.entity.dto.ColecaoDTO;
import br.com.d2.figurinha.model.entity.dto.FigurinhaDTO;
import br.com.d2.figurinha.model.entity.dto.SlotAlbumDTO;
import br.com.d2.figurinha.model.enumeration.Raridade;
import br.com.d2.figurinha.model.mapper.FigurinhaMapper;
import br.com.d2.figurinha.service.repository.FigurinhaRepository;
import br.com.d2.figurinha.service.impl.ColecaoServiceImpl;

public class ColecaoServiceImplTest {

    @InjectMocks
    private ColecaoServiceImpl colecaoService;

    @Mock
    private FigurinhaRepository repository;

    @Mock
    private FigurinhaMapper mapper;
    
    @Mock
    private AlbumClientService albumClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public  void testGetColecao() {
        // Dado que
        String idColecao = "idColecao";
        String qtd = "qtd";
        List<Figurinha> figurinhas = new ArrayList<>();
        Figurinha figurinha = new Figurinha();
        figurinha.setRaridade(Raridade.GRAU_4.getDescicao());
        figurinha.setDescricao("descricao");
        figurinha.setImagem("imagem");
        figurinha.setIdColecao("idColecao");
        figurinhas.add(figurinha);
        when(repository.getPacoteFigurinha(idColecao, qtd)).thenReturn(figurinhas);
        FigurinhaDTO figurinhaDTO = new FigurinhaDTO();
        figurinhaDTO.setRaridade(Raridade.GRAU_4.getDescicao());
        figurinhaDTO.setDescricao("descricao");
        figurinhaDTO.setImagem("imagem");
        figurinhaDTO.setIdColecao("idColecao");
        List<FigurinhaDTO> expected = new ArrayList<>();
        expected.add(figurinhaDTO);
        when(mapper.parseListDTO(figurinhas)).thenReturn(expected);

        // Quando
        List<FigurinhaDTO> actual = colecaoService.getColecao(idColecao, qtd);

        // Então
        assertEquals(expected, actual);
    }
    
    @Test
    public void criarColecao_deveRetornarListaDeFigurinhasDTO() {
        ColecaoDTO colecao = new ColecaoDTO();
        colecao.setIdColecao("1");
        List<Figurinha> listaFigurinhas = new ArrayList<>();
        listaFigurinhas.add(new Figurinha());
        List<SlotAlbumDTO> slots = new ArrayList<>();
        
        for (int i = 0; i < 20; i++) {
        	SlotAlbumDTO slot = new SlotAlbumDTO();
        	slot.setIdentificador(UUID.randomUUID());
        	slots.add(slot);
		}
       
        
        when(repository.getVerificaExistenciaColecao(anyString())).thenReturn(0);
        when(repository.saveAll(listaFigurinhas)).thenReturn(listaFigurinhas);
        when(mapper.parseListDTO(listaFigurinhas)).thenReturn(new ArrayList<>());
        when(albumClient.getSlots(UUID.fromString(colecao.getIdColecao()))).thenReturn(slots);
        

        List<FigurinhaDTO> result = colecaoService.criarColecao(colecao);
        assertEquals(0, result.size());
    }

}