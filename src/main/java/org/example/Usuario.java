package org.example;

public class Usuario {
    private final String nome;
    private final boolean autorizado;

    public Usuario(String nome, boolean autorizado) {
        this.nome = nome;
        this.autorizado = autorizado;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAutorizado() {
        return autorizado;
    }
}
