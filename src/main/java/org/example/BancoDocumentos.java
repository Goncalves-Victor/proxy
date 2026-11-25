package org.example;

import java.util.HashMap;
import java.util.Map;

public class BancoDocumentos {
    private static final Map<Integer, Documento> documentos = new HashMap<>();

    public static void addDocumento(Documento documento) {
        documentos.put(documento.getCodigo(), documento);
    }

    public static Documento getDocumento(int codigo) {
        Documento documento = documentos.get(codigo);
        if (documento == null) {
            throw new IllegalArgumentException("Documento não encontrado");
        }
        return documento;
    }
}
