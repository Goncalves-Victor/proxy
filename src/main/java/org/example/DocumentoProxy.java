package org.example;

import java.util.List;


public class DocumentoProxy implements IDocumento {
    private final int codigo;
    private Documento documento;

    public DocumentoProxy(int codigo) {
        this.codigo = codigo;
    }

    private Documento carregarDocumento() {
        if (documento == null) {
            documento = new Documento(codigo);
        }
        return documento;
    }

    @Override
    public List<String> obterDados() {
        return carregarDocumento().obterDados();
    }

    @Override
    public String obterConteudo(Usuario usuario) {
        if (usuario == null || !usuario.isAutorizado()) {
            throw new IllegalArgumentException("Usuário não autorizado");
        }
        return carregarDocumento().obterConteudo(usuario);
    }
}
