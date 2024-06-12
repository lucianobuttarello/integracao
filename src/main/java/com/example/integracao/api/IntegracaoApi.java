package com.example.integracao.api;

import com.example.integracao.dominio.PedidoFiltro;
import com.example.integracao.dominio.UsuarioResponse;
import com.example.integracao.exception.ErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IntegracaoApi {
    @Operation(summary = "Carregamento do Arquivo ", tags = "Pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorDTO.class))) })
    ResponseEntity<String> carregaArquivo(@RequestParam("file") MultipartFile file) throws IOException;

    @Operation(summary = "Consulta dos Pedidos por filtro", tags = "Pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorDTO.class))) })
    ResponseEntity<Page<UsuarioResponse>> obtemRelatorio(@RequestBody PedidoFiltro filtro);
}
