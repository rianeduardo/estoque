package dev.rianeduardo.estoque.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.rianeduardo.estoque.models.*;
import dev.rianeduardo.estoque.repositories.MovimentacaoRepository;
import dev.rianeduardo.estoque.repositories.MaterialRepository;

@Controller
@RequestMapping("/app/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @GetMapping
    public String listarMovimentacoes(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }

        model.addAttribute("movimentacoes", movimentacaoRepository.findAll());
        return "app/movimentacoes/listar";
    }

    @GetMapping("/nova")
    public String novaMovimentacao(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }

        model.addAttribute("movimentacao", new Movimentacoes());

        model.addAttribute("materiais", materialRepository.findAll());
        return "app/movimentacoes/nova";
    }

    @PostMapping("/salvar")
    public String salvarMovimentacao(@ModelAttribute Movimentacoes movimentacao, HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }

        movimentacaoRepository.save(movimentacao);

        Materiais material = materialRepository.findById(movimentacao.getMaterial().getId()).orElse(null);

        if (material != null) {
            if (movimentacao.getTipo() == Movimentacoes.TipoMovimentacao.ENTRADA) {
                material.setEstoque(material.getEstoque() + movimentacao.getQuantidade());

            } else if (movimentacao.getTipo() == Movimentacoes.TipoMovimentacao.SAIDA) {
                material.setEstoque(material.getEstoque() - movimentacao.getQuantidade());
            }

            materialRepository.save(material);
        }

        return "redirect:/app/movimentacoes";
    }

    @GetMapping("/excluir/{id}")
    public String excluirMovimentacao(@PathVariable("id") Long id, HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }

        movimentacaoRepository.deleteById(id);

        return "redirect:/app/movimentacoes";
    }
}