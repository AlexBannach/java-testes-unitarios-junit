package br.com.twbiblioteca.services;

import br.com.twbiblioteca.dao.EmprestimoDAO;
import br.com.twbiblioteca.models.Cliente;
import br.com.twbiblioteca.models.Emprestimo;
import br.com.twbiblioteca.models.Obra;

import java.time.LocalDate;
import java.util.List;

public class EmprestimoService {

    public EmprestimoDAO emprestimoDAO;

    private NotificacaoService notificacaoService;

    public EmprestimoService(EmprestimoDAO emprestimoDAO, NotificacaoService notificacaoService) {
        this.emprestimoDAO = emprestimoDAO;
        this.notificacaoService = notificacaoService;
    }

    public Emprestimo novo(Cliente cliente, List<Obra> obras) {

        if(cliente == null ) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }

        if(obras == null || obras.isEmpty()) {
            throw new IllegalArgumentException("Obras não pode ser nulo e nem vazio");
        }

        Emprestimo emprestimo = new Emprestimo();

        var dataEmprestimo = LocalDate.now();
        var diasParaDevolucao = cliente.getReputacao().obterDiasParaDevolucao();


        var dataDevolucao = dataEmprestimo.plusDays(diasParaDevolucao);

        emprestimo.setCliente(cliente);
        emprestimo.setLivros(obras);
        emprestimo.setDataEmprestimo(dataEmprestimo);
        emprestimo.setDataDevolucao(dataDevolucao);

        return emprestimo;
    }

    public void notificarAtraso() {
        var hoje = LocalDate.now();

        var emprestimos = emprestimoDAO.buscarTodos();

        for (Emprestimo emprestimo : emprestimos) {
            var estarAtrasado = emprestimo.getDataDevolucao().isBefore(hoje);
            if (estarAtrasado) {
                notificacaoService.notificar(emprestimo);
            }
        }
    }
}
