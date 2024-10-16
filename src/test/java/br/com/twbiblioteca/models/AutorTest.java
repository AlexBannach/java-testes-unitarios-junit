package br.com.twbiblioteca.models;

import jdk.jfr.Description;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class AutorTest {

    @Description("Testa o método estaVivo da classe Autor quando a data de falecimento é nula")
    @Test
    public void quandoMetodoEstaVivoForChamadoComDataFalecimentoNulaDeveRetornarTrue() {
        Autor autor = new Autor();

        assertEquals(true, autor.estaVivo());
    }

    @Description("Testa o método estaVivo da classe Autor quando a data de falecimento é preenchida")
    @Test
    public void quandoMetodoEstaVivoForChamadoComDataFalecimentoPreenchidaDeveRetornarFalse() {
        Autor autor = new Autor();
        autor.setDataFalecimento(LocalDate.now());

        assertEquals(false, autor.estaVivo());
    }
}
