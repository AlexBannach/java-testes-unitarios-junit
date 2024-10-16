package br.com.twbiblioteca.dao;

import br.com.twbiblioteca.models.Emprestimo;

import java.util.List;

public interface EmprestimoDAO {

    List<Emprestimo> buscarTodos();
}
