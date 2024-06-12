package com.example.integracao.repository.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "pedido")
@EqualsAndHashCode(exclude = "pedido")
public class Produto {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private double valor;
        private Long idProduto;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_Pedido")
        private Pedido pedido;


    }
