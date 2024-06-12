package com.example.integracao;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.integracao.dominio.PedidoFiltro;
import com.example.integracao.dominio.UsuarioResponse;
import com.example.integracao.repository.UsuarioRepository;
import com.example.integracao.repository.dto.Usuario;
import com.example.integracao.service.IntegracaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

@ExtendWith(MockitoExtension.class)

class IntegracaoServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private IntegracaoService integracaoService;

    @Mock
    private MultipartFile file;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCarregaArquivo() throws Exception {
        String content = "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        when(file.getInputStream()).thenReturn(inputStream);
        when(file.getOriginalFilename()).thenReturn("testFile.txt");

        (usuarioRepository).save(Mockito.any());

        String result = integracaoService.carregaArquivo(file);

        assertEquals("Foram carregados 1 pedidos do Arquivo: testFile.txt", result);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testObtemRelatorioVendas() {
        PedidoFiltro filtro = new PedidoFiltro(null, LocalDate.now().minusDays(1), LocalDate.now(), 0, 10);
        filtro.setPagina(0);
        filtro.setTamanho(10);
        filtro.setDataInicio(LocalDate.now().minusDays(1));
        filtro.setDataFim(LocalDate.now());

        List<Usuario> usuarios = new ArrayList<>();
        Page<Usuario> page = new PageImpl<>(usuarios);

        when(usuarioRepository.findByDateBetweenAndUserIdAndStatus(
                any(LocalDate.class), any(LocalDate.class), any(Long.class), any(Pageable.class)))
                .thenReturn(page);

        assertThrows(RuntimeException.class, () -> {
            integracaoService.obtemRelatorioVendas(filtro);
        });
    }

    @Test
    void testObtemRelatorioVendas_Valid() {
        PedidoFiltro filtro = new PedidoFiltro(null, LocalDate.now().minusDays(1), LocalDate.now(), 0, 10);
        filtro.setPagina(0);
        filtro.setTamanho(10);
        filtro.setDataInicio(LocalDate.now().minusDays(1));
        filtro.setDataFim(LocalDate.now());

        Usuario usuario = new Usuario();
        List<Usuario> usuarios = Collections.singletonList(usuario);
        Page<Usuario> page = new PageImpl<>(usuarios);

        when(usuarioRepository.findByDateBetweenAndUserIdAndStatus(
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(page);

        Page<UsuarioResponse> result = integracaoService.obtemRelatorioVendas(filtro);

        assertNotNull(result);
    }
}
