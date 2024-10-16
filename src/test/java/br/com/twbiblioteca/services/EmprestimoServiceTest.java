package br.com.twbiblioteca.services;

import br.com.twbiblioteca.builders.ClienteBuilder;
import br.com.twbiblioteca.builders.EmprestimoBuilder;
import br.com.twbiblioteca.builders.ObraBuilder;
import br.com.twbiblioteca.dao.EmprestimoDAO;
import br.com.twbiblioteca.enums.Reputacao;
import br.com.twbiblioteca.models.Cliente;
import br.com.twbiblioteca.models.Emprestimo;
import br.com.twbiblioteca.models.Obra;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmprestimoServiceTest {

    @Mock
    private EmprestimoDAO emprestimoDAO;

    @Mock
    private NotificacaoService notificacaoService;

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Description("Testa o método novo da classe EmprestimoService")
    @Test
    void quandoMetodoNovoForChamadoDeveRetornarUmEmprestimo() {

        Cliente cliente = ClienteBuilder.builder().build();
        Obra obra = ObraBuilder.builder().build();

        //Execucao
        Emprestimo emprestimo = emprestimoService.novo(cliente, List.of(obra));

        //Verificacao
        assertEquals(cliente, emprestimo.getCliente());
        assertEquals(List.of(obra), emprestimo.getLivros());
        assertEquals(LocalDate.now(), emprestimo.getDataEmprestimo());
        assertEquals(LocalDate.now().plusDays(3), emprestimo.getDataDevolucao() );
    }

    @Description("Testa o método novo da classe EmprestimoService quando o cliente tem reputação ruim")
    @Test
    void quandoMetodoNovoForChamadoComCLienteDeReputacaoRuimDeveRetornarUmEmprestimoComDataDeDevolucaoDe1Dia() {

        Cliente cliente = ClienteBuilder.builder().reputacao(Reputacao.RUIM).build();
        Obra obra = ObraBuilder.builder().build();

        //Execucao
        Emprestimo emprestimo = emprestimoService.novo(cliente, List.of(obra));

        //Verificacao
        assertEquals(LocalDate.now().plusDays(1), emprestimo.getDataDevolucao());
    }

    @Description("Testa o método novo da classe EmprestimoService quando o cliente tem reputação regular")
    @Test
    void quandoMetodoNovoForChamadoComCLienteDeReputacaoRgularDeveRetornarUmEmprestimoComDataDeDevolucaoDe3Dias() {

        Cliente cliente = ClienteBuilder.builder().build();
        Obra obra = ObraBuilder.builder().build();

        //Execucao
        Emprestimo emprestimo = emprestimoService.novo(cliente, List.of(obra));

        //Verificacao
        assertEquals(LocalDate.now().plusDays(3), emprestimo.getDataDevolucao());
    }

    @Description("Testa o método novo da classe EmprestimoService quando o cliente tem reputação boa")
    @Test
    void quandoMetodoNovoForChamadoComCLienteDeReputacaoBoaDeveRetornarUmEmprestimoComDataDeDevolucaoDe5Dia() {

        Cliente cliente = ClienteBuilder.builder().reputacao(Reputacao.BOA).build();
        Obra obra = ObraBuilder.builder().build();

        //Execucao
        Emprestimo emprestimo = emprestimoService.novo(cliente, List.of(obra));

        //Verificacao
        assertEquals(LocalDate.now().plusDays(5), emprestimo.getDataDevolucao());
    }

    @Description("Testa o método novo da classe EmprestimoService quando a obra é nula")
    @Test
    void quandoMetodoNovoForChamadoComObraNulaDeveLancarUmaExcecaoDoTipoIllegalArgumentException() {

        Cliente cliente = ClienteBuilder.builder().build();
        var mensagemEsperada = "Obras não pode ser nulo e nem vazio";

        //Execucao e Verificacao
        var exception = assertThrows(IllegalArgumentException.class, () -> emprestimoService.novo(cliente, null));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Description("Testa o método novo da classe EmprestimoService quando a obra é vazia")
    @Test
    void quandoMetodoNovoForChamadoComObraVaziaDeveLancarUmaExcecaoDoTipoIllegalArgumentException() {

        Cliente cliente = ClienteBuilder.builder().build();
        var obras = new ArrayList<Obra>();
        var mensagemEsperada = "Obras não pode ser nulo e nem vazio";

        //Execucao e Verificacao
        var exception = assertThrows(IllegalArgumentException.class, () -> emprestimoService.novo(cliente, obras));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Description("Testa o método novo da classe EmprestimoService quando o cliente é nulo")
    @Test
    void quandoMetodoNovoForChamadoComClienteNuloDeveLancarUmaExcecaoDoTipoIllegalArgumentException() {

        var obra = ObraBuilder.builder().build();
        var mensagemEsperada = "Cliente não pode ser nulo";

        //Execucao e Verificacao
        var exception = assertThrows(IllegalArgumentException.class, () -> emprestimoService.novo(null, List.of(obra)));
        assertEquals(mensagemEsperada, exception.getMessage());
    }

    @Description("Testa o método notificar Atraso da classe EmprestimoService")
    @Test
    void quandoMetodoNotificarAtrasoForChamadoDeveRetornarONumeroDeNotificacoes() {
        //Cenario
       var emprestimos = List.of(
                EmprestimoBuilder.builder().build(),
               EmprestimoBuilder.builder().dataDevolucao(LocalDate.now().minusDays(1)).build(),
               EmprestimoBuilder.builder().dataDevolucao(LocalDate.now().minusDays(2)).build(),
               EmprestimoBuilder.builder().dataDevolucao(LocalDate.now().plusDays(1)).build()
       );

       when(emprestimoDAO.buscarTodos()).thenReturn(emprestimos);

       emprestimoService.notificarAtraso();

        verify(notificacaoService).notificar(emprestimos.get(2));
    }
}
