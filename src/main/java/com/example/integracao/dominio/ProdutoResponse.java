package com.example.integracao.dominio;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProdutoResponse {
    private Long idProduto;
    private double valor;

    public  ProdutoResponse() {
    }


    public  ProdutoResponse(Long idProduto, double valor) {
        this.idProduto = idProduto;
        this.valor = valor;
    }



}
