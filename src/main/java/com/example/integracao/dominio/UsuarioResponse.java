package com.example.integracao.dominio;

import com.example.integracao.repository.dto.PaginacaoDTO;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Data
public class UsuarioResponse {
    private Integer idUsuario;
    private String nome;
    private List<PedidoResponse> pedidos;
    private PaginacaoDTO paginacao;

    public UsuarioResponse() {

    }
    public UsuarioResponse(Integer userId, String name, List<PedidoResponse> pedidos,PaginacaoDTO paginacao) {
        this.idUsuario = userId;
        this.nome = name;
        this.pedidos = pedidos;
        this.paginacao = paginacao;
    }

    public UsuarioResponse(Integer idUsuario, List<PedidoResponse> pedidosCombinados) {
    }
}
