package com.uol.mscheckout.repository;

import com.uol.mscheckout.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento , Long> {
}
