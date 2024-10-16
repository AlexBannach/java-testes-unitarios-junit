package br.com.twbiblioteca.builders;

import br.com.twbiblioteca.enums.Reputacao;
import br.com.twbiblioteca.models.Cliente;

import java.time.LocalDate;

public class ClienteBuilder {

    private Cliente cliente;

    public static ClienteBuilder builder() {
        ClienteBuilder builder = new ClienteBuilder();

        var cliente = new Cliente(1L, "Fulano", LocalDate.now(), "123.123.123-11", Reputacao.REGULAR);
        builder.cliente = cliente;

        return builder;
    }

    public ClienteBuilder reputacao(Reputacao reputacao) {
        cliente.setReputacao(reputacao);
        return this;
    }

    public Cliente build() {
        return cliente;
    }
}
