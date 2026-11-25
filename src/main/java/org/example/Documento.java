package org.example;

import java.util.List;

public class Documento implements IDocumento {
    private final int codigo;
    private final String titulo;
    private final String autor;
    private final String conteudo;

    public Documento(int codigo, String titulo, String autor, String conteudo) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.conteudo = conteudo;
    }

    public Documento(int codigo) {
        Documento salvo = BancoDocumentos.getDocumento(codigo);
        this.codigo = salvo.codigo;
        this.titulo = salvo.titulo;
        this.autor = salvo.autor;
        this.conteudo = salvo.conteudo;
    }

    public int getCodigo() {
        return codigo;
    }

    @Override
    public List<String> obterDados() {
        return List.of(titulo, autor);
    }

    @Override
    public String obterConteudo(Usuario usuario) {
        return conteudo;
    }
}
