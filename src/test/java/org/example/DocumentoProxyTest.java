package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DocumentoProxyTest {
    @BeforeEach
    void cadastrarDocumento() {
        BancoDocumentos.addDocumento(new Documento(1, "Plano anual", "Ana", "Metas do próximo ano"));
    }

    @Test
    void deveConsultarDadosPeloContratoComum() {
        IDocumento documento = new DocumentoProxy(1);
        assertEquals(List.of("Plano anual", "Ana"), documento.obterDados());
    }

    @Test
    void deveLiberarConteudoParaUsuarioAutorizado() {
        IDocumento documento = new DocumentoProxy(1);
        assertEquals("Metas do próximo ano", documento.obterConteudo(new Usuario("Bruno", true)));
    }

    @Test
    void deveNegarConteudoParaUsuarioSemPermissao() {
        IDocumento documento = new DocumentoProxy(1);
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> documento.obterConteudo(new Usuario("Carla", false)));
        assertEquals("Usuário não autorizado", erro.getMessage());
    }

    @Test
    void deveVerificarPermissaoMesmoDepoisDeCarregarDocumento() {
        IDocumento documento = new DocumentoProxy(1);
        documento.obterConteudo(new Usuario("Bruno", true));

        assertThrows(IllegalArgumentException.class,
                () -> documento.obterConteudo(new Usuario("Carla", false)));
        assertThrows(IllegalArgumentException.class, () -> documento.obterConteudo(null));
    }

    @Test
    void deveCarregarSomenteNaPrimeiraConsulta() {
        IDocumento documento = new DocumentoProxy(1);
        BancoDocumentos.addDocumento(new Documento(1, "Plano revisado", "Ana", "Metas revisadas"));

        assertEquals(List.of("Plano revisado", "Ana"), documento.obterDados());
        assertEquals("Metas revisadas", documento.obterConteudo(new Usuario("Bruno", true)));
    }

    @Test
    void deveReutilizarDocumentoCarregado() {
        IDocumento documento = new DocumentoProxy(1);
        documento.obterDados();
        BancoDocumentos.addDocumento(new Documento(1, "Nova versão", "Bruno", "Novo conteúdo"));

        assertEquals(List.of("Plano anual", "Ana"), documento.obterDados());
        assertEquals("Metas do próximo ano", documento.obterConteudo(new Usuario("Ana", true)));
        assertEquals(List.of("Nova versão", "Bruno"), new DocumentoProxy(1).obterDados());
    }

    @Test
    void deveInformarDocumentoInexistenteNaConsulta() {
        IDocumento documento = new DocumentoProxy(-1);
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class, documento::obterDados);
        assertEquals("Documento não encontrado", erro.getMessage());
    }

    @Test
    void deveVerificarPermissaoAntesDeConsultarBanco() {
        IDocumento documento = new DocumentoProxy(-1);
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> documento.obterConteudo(new Usuario("Carla", false)));
        assertEquals("Usuário não autorizado", erro.getMessage());
    }
}
