package dev.rianeduardo.estoque.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.rianeduardo.estoque.models.*;
import dev.rianeduardo.estoque.repositories.*;

@Controller
public class AuthController {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioAutenticadoRepository funcionarioAutenticadoRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "app/auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String nif,
            @RequestParam String senha,
            HttpSession session,
            Model model) {

        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findByNif(nif);

        if (funcionarioOpt.isPresent()) {
            Funcionario funcionario = funcionarioOpt.get();

            if (funcionario.isAtivo() && funcionario.getSenha().equals(senha)) {

                funcionario.setUltimoLogin(LocalDateTime.now());
                funcionarioRepository.save(funcionario);

                session.setAttribute("usuarioLogado", true);
                session.setAttribute("nif", funcionario.getNif());
                session.setAttribute("nome", funcionario.getNome());
                session.setAttribute("ultimoLogin", funcionario.getUltimoLogin());

                return "redirect:/dashboard";
            }
        }

        model.addAttribute("erro", "NIF ou senha inválidos, ou usuário inativo.");
        return "app/auth/login";
    }

    @GetMapping("/cadastro")
    public String cadastroPage() {
        return "app/auth/cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastro(@RequestParam String nome,
            @RequestParam String nif,
            @RequestParam String senha,
            Model model) {

        if (funcionarioRepository.existsByNif(nif)) {
            model.addAttribute("erro", "Este NIF já possui uma conta cadastrada.");
            return "app/auth/cadastro";
        }

        boolean autorizado = funcionarioAutenticadoRepository.existsByNifAndNomeAndAtivoTrue(nif, nome);

        if (!autorizado) {
            model.addAttribute("erro",
                    "Cadastro negado. NIF e Nome não constam na lista de autorizados do RH ou estão inativos.");
            return "app/auth/cadastro";
        }

        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setNome(nome);
        novoFuncionario.setNif(nif);
        novoFuncionario.setSenha(senha);
        novoFuncionario.setAtivo(true);

        funcionarioRepository.save(novoFuncionario);

        model.addAttribute("sucesso", "Conta criada com sucesso! Você já pode fazer login.");
        return "app/auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String abrirDashboard(HttpSession session, Model model, RedirectAttributes redirectAttributes) {

        Object usuarioLogado = session.getAttribute("usuarioLogado");

        if (usuarioLogado != null && (Boolean) usuarioLogado) {

            String nif = (String) session.getAttribute("nif");
            String nome = (String) session.getAttribute("nome");

            model.addAttribute("nif", nif);
            model.addAttribute("nome", nome);

            return "app/index";
        } else {
            redirectAttributes.addFlashAttribute("erro",
                    "Sua sessão não é válida ou expirou. Faça login novamente para acessar o sistema.");
            return "redirect:/login";
        }
    }

    @GetMapping("/app/minha-conta")
    public String abrirConta(HttpSession session, Model model) {

        Object usuarioLogado = session.getAttribute("usuarioLogado");

        if (usuarioLogado != null && (Boolean) usuarioLogado) {
            String nif = (String) session.getAttribute("nif");
            String nome = (String) session.getAttribute("nome");
            LocalDateTime ultimoLogin = (LocalDateTime) session.getAttribute("ultimoLogin");

            model.addAttribute("nif", nif);
            model.addAttribute("nome", nome);
            model.addAttribute("ultimoLogin", ultimoLogin);

            return "app/auth/conta";
        } else {
            return "redirect:/login";
        }
    }

}