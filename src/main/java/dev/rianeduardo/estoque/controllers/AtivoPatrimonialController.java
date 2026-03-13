package dev.rianeduardo.estoque.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.rianeduardo.estoque.models.Ativos;
import dev.rianeduardo.estoque.repositories.AtivoPatrimonialRepository;

@Controller
@RequestMapping("/app/ativos")
public class AtivoPatrimonialController {

    @Autowired
    private AtivoPatrimonialRepository ativoRepository;

    @GetMapping
    public String listarAtivos(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }

        model.addAttribute("ativos", ativoRepository.findAll());
        return "app/ativos/listar";
    }

    @GetMapping("/novo")
    public String novoAtivo(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }

        model.addAttribute("ativo", new Ativos());

        return "app/ativos/novo";
    }

    @PostMapping("/salvar")
    public String salvarAtivo(@ModelAttribute Ativos ativo, HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }

        ativoRepository.save(ativo);
        return "redirect:/app/ativos";
    }

    @GetMapping("/editar/{id}")
    public String editarAtivo(@PathVariable("id") Long id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }

        Ativos ativo = ativoRepository.findById(id).orElse(null);
        if (ativo == null)
            return "redirect:/app/ativos";

        model.addAttribute("ativo", ativo);
        return "app/ativos/editar";
    }

    @GetMapping("/excluir/{id}")
    public String excluirAtivo(@PathVariable("id") Long id, HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }
        ativoRepository.deleteById(id);
        return "redirect:/app/ativos";
    }
}