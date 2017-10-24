package br.com.oak.aworks.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.oak.aworks.cobranca.model.StatusTitulo;
import br.com.oak.aworks.cobranca.model.Titulo;
import br.com.oak.aworks.cobranca.repository.Titulos;

@Service
public class CadastroTituloService {

	@Autowired
	private Titulos titulos;

	public List<Titulo> findAll() {
		return titulos.findAll();
	}

	public void salvar(Titulo titulo) {

		try {

			titulos.save(titulo);

		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}

	public void excluir(Long codigo) {
		titulos.delete(codigo);
	}
	
	public String receber(Long codigo) {

		Titulo titulo = titulos.findOne(codigo);

		titulo.setStatus(StatusTitulo.RECEBIDO);
		
		titulos.save(titulo);

		return StatusTitulo.RECEBIDO.getDescricao();
	}
}