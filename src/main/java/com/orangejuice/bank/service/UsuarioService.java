package com.orangejuice.bank.service;

import com.orangejuice.bank.model.Usuario;
import com.orangejuice.bank.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario criar(Usuario usuario) {
        return repository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario buscarPorId(long id) {
        return repository.findById(id).orElse(null);
    }

    public Usuario depositar(Long id, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser positivo.");
        }

        Usuario usuario = buscarPorId(id);
        if (usuario == null) return null;

        usuario.setSaldo(usuario.getSaldo().add(valor));
        return repository.save(usuario);
    }

    public Usuario sacar(Long id, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo.");
        }

        Usuario usuario = buscarPorId(id);
        if (usuario == null || usuario.getSaldo().compareTo(valor) < 0) return null;

        usuario.setSaldo(usuario.getSaldo().subtract(valor));
        return repository.save(usuario);
    }
}
