package br.com.oak.aworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.oak.aworks.cobranca.model.StatusTitulo;
import br.com.oak.aworks.cobranca.model.Titulo;
import br.com.oak.aworks.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	@Autowired
	private Titulos titulos;

	@RequestMapping
	public ModelAndView pesquisar() {

		ModelAndView mav = new ModelAndView("PesquisaTitulos");

		mav.addObject("titulos", titulos.findAll());

		return mav;
	}

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mav = new ModelAndView("CadastroTitulo");
		mav.addObject(new Titulo());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(@Validated Titulo titulo, Errors errors) {

		ModelAndView mav = new ModelAndView("CadastroTitulo");
		
		if (errors.hasErrors()) {
			return mav;
		}

		titulos.save(titulo);

		mav.addObject("mensagem", "Titulo salvo com sucesso");

		return mav;
	}

	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}
}