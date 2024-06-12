package com.example.integracao.repository;

import com.example.integracao.repository.dto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
