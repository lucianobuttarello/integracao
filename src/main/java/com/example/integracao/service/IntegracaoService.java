package com.example.integracao.service;

import com.example.integracao.dominio.PedidoFiltro;
import com.example.integracao.dominio.PedidoPayload;
import com.example.integracao.dominio.PedidoResponse;
import com.example.integracao.dominio.ProdutoResponse;
import com.example.integracao.dominio.UsuarioResponse;
import com.example.integracao.repository.ProdutoRepository;
import com.example.integracao.repository.UsuarioRepository;
import com.example.integracao.repository.dto.Usuario;
import com.example.integracao.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import static com.example.integracao.service.factory.IntegracaoServiceFactory.constroiPedidosBase;
import static com.example.integracao.service.factory.IntegracaoServiceFactory.constroiPedidosArquivo;
import static com.example.integracao.service.factory.IntegracaoServiceFactory.constroiUsuarios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class IntegracaoService {

    public final PedidoRepository pedidoRepository;
    public final UsuarioRepository usuarioRepository;
    public final ProdutoRepository produtoRepository;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public String carregaArquivo(MultipartFile file) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            Long cont = 0L;
            String line;
            List<PedidoPayload> pedidoPayload = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                pedidoPayload.add(constroiPedidosArquivo(line));
                cont ++;
            }
            List<PedidoPayload> pedidosOrdenados = ordenaPedidos(pedidoPayload);
            salvaPedidos(pedidosOrdenados);
            return "Foram carregados " + cont + " pedidos do Arquivo: " + file.getOriginalFilename();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao Ler o Arquivo");
        }
    }

    private void salvaPedidos (List<PedidoPayload> pedidosOrdenados){
        pedidosOrdenados.forEach(item -> {
            if (!item.getIdProduto().equals(0L)) {
                Usuario usuario = constroiPedidosBase(item);
                usuarioRepository.save(usuario);
            }
        });
    }

    private List<PedidoPayload> ordenaPedidos(List<PedidoPayload> pedidoPayload) {
        return pedidoPayload.stream()
                .sorted((p1, p2) -> p1.getIdPedido().compareTo(p2.getIdPedido()))
                .collect(Collectors.toList());
    }

    public Page<UsuarioResponse> obtemRelatorioVendas(PedidoFiltro filtro) {

        validaPaginacao(filtro);

        Pageable pageable = PageRequest.of(filtro.getPagina(), filtro.getTamanho());
        Page<Usuario> resultado = usuarioRepository.findByDateBetweenAndUserIdAndStatus(
                filtro.getDataInicio(), filtro.getDataFim(), filtro.getIdPedido(), pageable);

        if (resultado.isEmpty()) {
            throw new RuntimeException("Nenhum Pedido Encontrado");
        }

        List<UsuarioResponse> listaUsuarioResponse = agrupaRetorno(constroiUsuarios(resultado.getContent()));

        return new PageImpl<>(listaUsuarioResponse, pageable, resultado.getTotalElements());
    }

    private void validaPaginacao(PedidoFiltro filtro) {
        if (filtro.getTamanho() <= 0) {
            throw new RuntimeException("Tamanho da Pagina tem que ser maior que Zero");
        }
    }

    public List<UsuarioResponse> agrupaRetorno(List<UsuarioResponse> listaUsuarioResponse ) {
        Map<Integer, UsuarioResponse> usuarioResponseMap = new HashMap<>();

        for (UsuarioResponse usuarioResponse : listaUsuarioResponse) {
            if (!usuarioResponseMap.containsKey(usuarioResponse.getIdUsuario())) {
                usuarioResponseMap.put(usuarioResponse.getIdUsuario(), usuarioResponse);
            } else {
                UsuarioResponse existingUsuarioResponse = usuarioResponseMap.get(usuarioResponse.getIdUsuario());
                for (PedidoResponse pedidoResponse : usuarioResponse.getPedidos()) {
                    boolean pedidoJaExiste = false;
                    for (PedidoResponse existingPedidoResponse : existingUsuarioResponse.getPedidos()) {
                        if (existingPedidoResponse.getIdPedido().equals(pedidoResponse.getIdPedido())) {
                            existingPedidoResponse.getProdutos().addAll(pedidoResponse.getProdutos());
                            pedidoJaExiste = true;
                            break;
                        }
                    }
                    if (!pedidoJaExiste) {
                        existingUsuarioResponse.getPedidos().add(pedidoResponse);
                    }
                }
                usuarioResponseMap.put(usuarioResponse.getIdUsuario(), existingUsuarioResponse);
            }
        }
        List<UsuarioResponse> groupedUsuarioResponses = new ArrayList<>(usuarioResponseMap.values());

        return calculaTotal(groupedUsuarioResponses);
    }
    private List<UsuarioResponse> calculaTotal(List<UsuarioResponse> groupedUsuarioResponses){
        groupedUsuarioResponses.forEach(usuarioResponse ->
                usuarioResponse.getPedidos().forEach(pedidoResponse -> {
                    double totalPedido = pedidoResponse.getProdutos().stream()
                            .mapToDouble(ProdutoResponse::getValor)
                            .sum();
                    pedidoResponse.setTotal(Double.parseDouble(df.format(totalPedido)));
                })
        );
        return groupedUsuarioResponses;
    }
}