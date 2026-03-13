package dev.rianeduardo.estoque.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.rianeduardo.estoque.models.Categorias;
import dev.rianeduardo.estoque.repositories.CategoriaRepository;

@Controller
@RequestMapping("/app/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String listarCategorias(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "app/categorias/listar";
    }

    @GetMapping("/nova")
    public String novaCategoria(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }
        model.addAttribute("categoria", new Categorias());
        return "app/categorias/form";
    }

    @PostMapping("/salvar")
    public String salvarCategoria(@ModelAttribute Categorias categoria, HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }
        categoriaRepository.save(categoria);
        return "redirect:/app/categorias";
    }

    @GetMapping("/editar/{id}")
    public String editarCategoria(@PathVariable("id") Long id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }

        Categorias categoria = categoriaRepository.findById(id).orElse(null);
        if (categoria == null)
            return "redirect:/app/categorias";

        model.addAttribute("categoria", categoria);
        return "app/categorias/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCategoria(@PathVariable("id") Long id, HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }
        categoriaRepository.deleteById(id);
        return "redirect:/app/categorias";
    }
}