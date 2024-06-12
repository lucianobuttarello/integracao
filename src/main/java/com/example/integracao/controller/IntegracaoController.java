package com.example.integracao.controller;

import com.example.integracao.api.IntegracaoApi;
import com.example.integracao.dominio.PedidoFiltro;

import com.example.integracao.dominio.UsuarioResponse;
import com.example.integracao.service.IntegracaoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class IntegracaoController implements IntegracaoApi {

    private final IntegracaoService integracaoService;

    public IntegracaoController(IntegracaoService integracaoService) {
        this.integracaoService = integracaoService;
    }

    @Override
    @PostMapping("/upload")
    public ResponseEntity<String> carregaArquivo(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(integracaoService.carregaArquivo(file));
    }
    @Override
    @PostMapping
    public ResponseEntity<Page<UsuarioResponse>> obtemRelatorio(@RequestBody PedidoFiltro filtro) {
        return ResponseEntity.ok(integracaoService.obtemRelatorioVendas(filtro));
    }
}
