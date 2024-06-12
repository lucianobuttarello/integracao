package com.example.integracao.repository.dto;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "pedidos" )
@EqualsAndHashCode(exclude = "pedidos")
public class Usuario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Integer idUsuario;
        private String nome;

        @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Pedido> pedidos = new ArrayList<>();

        public void addPedido(Pedido pedido) {
                pedidos.add(pedido);
                pedido.setUsuario(this);
        }
}
