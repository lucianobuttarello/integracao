package com.example.integracao.repository;

import com.example.integracao.repository.dto.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u "
            + "JOIN u.pedidos p "
            + "WHERE (:dataInicio IS NULL OR :dataFim IS NULL OR p.data BETWEEN :dataInicio AND :dataFim) "
            + "AND (:idPedido IS NULL OR p.idPedido = :idPedido)")
    Page<Usuario> findByDateBetweenAndUserIdAndStatus(LocalDate dataInicio, LocalDate dataFim, Long idPedido, Pageable pageable);}

