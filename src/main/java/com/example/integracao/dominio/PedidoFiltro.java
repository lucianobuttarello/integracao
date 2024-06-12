package com.example.integracao.dominio;

import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Builder
@Data
public class PedidoFiltro {

    private Long idPedido;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Integer pagina;
    private Integer tamanho;

    public PedidoFiltro(Long idPedido, LocalDate dataInicio, LocalDate dataFim, Integer pagina, Integer tamanho) {
        this.idPedido = idPedido;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.pagina = pagina;
        this.tamanho = tamanho;
    }
}
