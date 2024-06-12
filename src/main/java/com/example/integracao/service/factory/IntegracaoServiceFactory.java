package com.example.integracao.service.factory;

import com.example.integracao.dominio.PedidoPayload;
import com.example.integracao.dominio.PedidoResponse;
import com.example.integracao.dominio.ProdutoResponse;
import com.example.integracao.dominio.UsuarioResponse;
import com.example.integracao.repository.dto.Pedido;
import com.example.integracao.repository.dto.Produto;
import com.example.integracao.repository.dto.Usuario;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IntegracaoServiceFactory {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static Usuario constroiPedidosBase(PedidoPayload pedidoPayload){
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(pedidoPayload.getIdUsuario());
        usuario.setNome(pedidoPayload.getNome());

        Pedido pedido = new Pedido();
        pedido.setIdPedido(pedidoPayload.getIdPedido());
        pedido.setData(pedidoPayload.getData());
        pedido.setUsuario(usuario);

        Produto produto = new Produto();
        produto.setIdProduto(pedidoPayload.getIdProduto());
        produto.setValor(pedidoPayload.getValor());
        produto.setPedido(pedido);
        usuario.setPedidos(List.of(pedido));
        pedido.setProdutos(List.of(produto));
        return usuario;
    }
    public static PedidoPayload constroiPedidosArquivo(String linha) {
        return PedidoPayload.builder()
                .idProduto(Long.valueOf(linha.substring(66, 76).trim()))
                .nome(linha.substring(10, 55).trim())
                .data(LocalDate.parse(linha.substring(87, 95).trim(),formatter))
                .idPedido(Long.valueOf(linha.substring(56, 65).trim()))
                .idUsuario(Integer.valueOf(linha.substring(0, 10).trim()))
                .valor(Double.parseDouble(linha.substring(77, 87).trim()))
                .build();
    }

    public static List<UsuarioResponse> constroiUsuarios(List<Usuario> usuario) {
        List<UsuarioResponse> listaUsuarioResponse = new ArrayList<>();
        usuario.stream().forEach(item -> {
            listaUsuarioResponse.add(UsuarioResponse.builder().idUsuario(item.getIdUsuario()).nome(item.getNome()).pedidos(constroiPedidos(item.getPedidos())).build());
        });
        return listaUsuarioResponse;
    }

    public static List<PedidoResponse> constroiPedidos(List<Pedido> pedidos) {
        List<PedidoResponse> listaPedidos = new ArrayList<>();
        pedidos.stream().forEach(item -> {
            listaPedidos.add(PedidoResponse.builder().data(item.getData()).idPedido(item.getIdPedido()).produtos(constroiProdutos(item.getProdutos())).build());
        });
        return listaPedidos;
    }

    public static List<ProdutoResponse> constroiProdutos(List<Produto> produtos) {
        List<ProdutoResponse> listaProdutos = new ArrayList<>();
        produtos.forEach(item -> {
            listaProdutos.add(ProdutoResponse.builder().idProduto(item.getIdProduto()).valor(item.getValor()).build());
        });
        return listaProdutos;
    }
}
