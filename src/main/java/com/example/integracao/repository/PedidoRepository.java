package com.example.integracao.repository;

import com.example.integracao.repository.dto.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
