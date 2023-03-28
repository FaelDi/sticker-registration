package br.com.d2.figurinha.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.d2.figurinha.model.entity.Figurinha;
import br.com.d2.figurinha.model.entity.dto.FigurinhaDTO;
import br.com.d2.figurinha.model.enumeration.Raridade;
import br.com.d2.figurinha.model.mapper.FigurinhaMapper;
import br.com.d2.figurinha.repository.FigurinhaRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class FigurinhaServiceImplTest {

    @Mock
    private FigurinhaRepository repository;
    
    @Mock
    private FigurinhaMapper mapper;
    
    @InjectMocks
    private FigurinhaServiceImpl service;
    
    private FigurinhaDTO figurinhaDTO;
    
    private Figurinha figurinha = new Figurinha();

    
    @BeforeEach
    public void setUp() throws Exception {
        figurinhaDTO = new FigurinhaDTO();
        figurinhaDTO.setId("1");
        figurinhaDTO.setDescricao("Figurinha teste");
        figurinhaDTO.setIdColecao("colecao1");
        figurinhaDTO.setImagem("imagem_teste");
        figurinhaDTO.setPreco("2.5");
        figurinhaDTO.setRaridade(Raridade.GRAU_4.getDescicao());
        
        figurinha = new Figurinha();
		figurinha.setId("1");
		figurinha.setIdColecao("123");
		figurinha.setDescricao("Figurinha do jogador Neymar");
		figurinha.setImagem("https://images.com/123.jpg");
		figurinha.setPreco("1.50");
		figurinha.setRaridade(Raridade.GRAU_4.getDescicao());
    }

    @Test
    @DisplayName("Buscar todos figurinhas - Deve retornar uma lista com todas as figurinhas cadastradas")
    public void buscarTodosTest() {
        List<Figurinha> listEntity = Arrays.asList(new Figurinha(), new Figurinha());
        when(repository.findAll()).thenReturn(listEntity);
        List<FigurinhaDTO> listDTOExpected = Arrays.asList(new FigurinhaDTO(), new FigurinhaDTO());
        when(mapper.parseListDTO(listEntity)).thenReturn(listDTOExpected);
        
        List<FigurinhaDTO> listDTOActual = service.buscarTodos();
        
        assertAll(() -> assertEquals(listDTOExpected, listDTOActual),
                () -> verify(repository).findAll(),
                () -> verify(mapper).parseListDTO(listEntity));
    }
    
    @Test
    @DisplayName("Buscar figurinha por id existente - Deve retornar a figurinha com o id informado")
    public void buscarUmTest() {
        Figurinha entity = new Figurinha();
        when(repository.findById(anyString())).thenReturn(Optional.of(entity));
        FigurinhaDTO dtoExpected = new FigurinhaDTO();
        when(mapper.parseDTO(entity)).thenReturn(dtoExpected);
        
        FigurinhaDTO dtoActual = service.buscarUm("1");
        
        assertAll(() -> assertEquals(dtoExpected, dtoActual),
                () -> verify(repository).findById("1"),
                () -> verify(mapper).parseDTO(entity));
    }
    
    @Test
    @DisplayName("Buscar figurinha por id não existente - Deve lançar EntityNotFoundException")
    public void buscarUmTestEntityNotFound() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> service.buscarUm("1"),
                "Deveria ter lançado EntityNotFoundException");
    }
    
	@Test
	public void testCriar() {
		when(mapper.parseEntity(figurinhaDTO)).thenReturn(figurinha);
		when(repository.save(figurinha)).thenReturn(figurinha);
		when(mapper.parseDTO(figurinha)).thenReturn(figurinhaDTO);

		FigurinhaDTO result = service.criar(figurinhaDTO);

		assertNotNull(result);
		assertEquals(result.getIdColecao(), figurinhaDTO.getIdColecao());
		assertEquals(result.getDescricao(), figurinhaDTO.getDescricao());
		assertEquals(result.getImagem(), figurinhaDTO.getImagem());
		assertEquals(result.getPreco(), figurinhaDTO.getPreco());
		assertEquals(result.getRaridade(), figurinhaDTO.getRaridade());
	}
	
    @Test
    public void testEditarFigurinhaExistente() {
        // Arrange
        String id = "1";

        when(repository.findById(id)).thenReturn(Optional.of(figurinha));
        when(repository.save(figurinha)).thenReturn(figurinha);
        when(mapper.parseDTO(figurinha)).thenReturn(figurinhaDTO);

        // Act
        FigurinhaDTO resultado = service.editar(id, figurinhaDTO);

        // Assert
        verify(repository).findById(id);
        verify(repository).save(figurinha);
        verify(mapper).parseDTO(figurinha);
        assertEquals(figurinhaDTO, resultado);
        assertEquals("colecao1", figurinha.getIdColecao());
        assertEquals(Raridade.GRAU_4.getDescicao(), figurinha.getRaridade());
        assertEquals("2.5", figurinha.getPreco());
    }

    @Test
    public void testEditarFigurinhaInexistente() {
        // Arrange
        String id = "1";
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act/Assert
        assertThrows(EntityNotFoundException.class, () -> service.editar(id, figurinhaDTO));
        verify(repository).findById(id);
        verify(repository, never()).save(any());
        verify(mapper, never()).parseDTO(any());
    }

    @Test
    public  void testExcluirFigurinhaExistente() {
        // Arrange
        String id = "1";
        when(repository.existsById(id)).thenReturn(true);

        // Act
        service.excluir(id);

        // Assert
        verify(repository).existsById(id);
        verify(repository).deleteById(id);
    }

    @Test
    public void testExcluirFigurinhaInexistente() {
        // Arrange
        String id = "1";
        when(repository.existsById(id)).thenReturn(false);

        // Act/Assert
        assertThrows(EntityNotFoundException.class, () -> service.excluir(id));
        verify(repository).existsById(id);
        verify(repository, never()).deleteById(any());
    }


    
}