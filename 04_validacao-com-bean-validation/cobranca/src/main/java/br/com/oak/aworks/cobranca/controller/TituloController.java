package br.com.oak.aworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.oak.aworks.cobranca.model.StatusTitulo;
import br.com.oak.aworks.cobranca.model.Titulo;
import br.com.oak.aworks.cobranca.service.CadastroTituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	private static final String CADASTRO_VIEW = "CadastroTitulo";

	@Autowired
	private CadastroTituloService cadastroTituloService;

	@RequestMapping
	public ModelAndView pesquisar() {

		final ModelAndView mav = new ModelAndView("PesquisaTitulos");

		mav.addObject("titulos", cadastroTituloService.findAll());

		return mav;
	}

	@RequestMapping("/novo")
	public ModelAndView novo() {

		final ModelAndView mav = new ModelAndView(CADASTRO_VIEW);

		mav.addObject(new Titulo());

		return mav;
	}

	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {

		final ModelAndView mv = new ModelAndView(CADASTRO_VIEW);

		mv.addObject(titulo);

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {

		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}

		try {
			cadastroTituloService.salvar(titulo);

			attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");
			return "redirect:/titulos/novo";

		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}

	@RequestMapping(value = "{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {

		cadastroTituloService.excluir(codigo);

		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		return "redirect:/titulos";
	}
	
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		return cadastroTituloService.receber(codigo);
	}

	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}
}