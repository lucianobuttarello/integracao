package com.example.integracao.dominio;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class PedidoResponse {

    private Long idPedido;
    private double total;
    private LocalDate data;
    private List<ProdutoResponse> produtos;

    public PedidoResponse() {
    }

    public PedidoResponse(Long idPedido, double total, LocalDate data, List<ProdutoResponse> produtos) {
        this.idPedido = idPedido;
        this.total = total;
        this.data = data;
        this.produtos = produtos;
    }
}
