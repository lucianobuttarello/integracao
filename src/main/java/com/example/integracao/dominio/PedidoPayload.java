package com.example.integracao.dominio;

import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Builder
@Data
public class PedidoPayload {

    private Integer idUsuario;
    private String nome;
    private Long idPedido;
    private Long idProduto;
    private double valor;
    private LocalDate data;
}
