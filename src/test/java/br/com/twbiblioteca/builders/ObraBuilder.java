package br.com.twbiblioteca.builders;

import br.com.twbiblioteca.enums.Tipo;
import br.com.twbiblioteca.models.Obra;

public class ObraBuilder {

    private Obra obra;

    public static ObraBuilder builder() {
        ObraBuilder builder = new ObraBuilder();

        var autor = AutorBuilder.builder().build();

        var obra = new Obra(1L, "Livro", 100, Tipo.LIVRO, autor);
        builder.obra = obra;

        return builder;
    }

    public Obra build() {
        return obra;
    }
}
