package br.com.twbiblioteca.services;

import br.com.twbiblioteca.models.Emprestimo;

public interface NotificacaoService {

    void notificar(Emprestimo emprestimo);
}
