package org.example;

import java.util.List;

public interface IDocumento {
    List<String> obterDados();
    String obterConteudo(Usuario usuario);
}
