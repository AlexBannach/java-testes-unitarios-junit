package br.com.twbiblioteca.builders;

import br.com.twbiblioteca.models.Autor;

import java.time.LocalDate;

public class AutorBuilder {

    private Autor autor;

    public static AutorBuilder builder() {
        AutorBuilder builder = new AutorBuilder();

        var autor = new Autor(1L, "Ciclano", LocalDate.now(), null);
        builder.autor = autor;

        return builder;
    }

    public Autor build() {
        return autor;
    }

}
