package br.com.oak.aworks.cobranca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.oak.aworks.cobranca.model.Titulo;

public interface Titulos extends JpaRepository<Titulo, Long> {

	List<Titulo> findByDescricaoContaining(String descricao);

}
