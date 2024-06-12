package com.example.integracao.repository.dto;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"usuario", "produtos"})
@EqualsAndHashCode(exclude = {"usuario", "produtos"})
public class Pedido {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private LocalDate data;
        private Long idPedido;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_usuario")
        private Usuario usuario;

        @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Produto> produtos = new ArrayList<>();

        public void addProduto(Produto produto) {
                produtos.add(produto);
                produto.setPedido(this);
        }

}
