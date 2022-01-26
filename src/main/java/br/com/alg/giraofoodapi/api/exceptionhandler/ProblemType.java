package br.com.alg.giraofoodapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ERRO_SISTEMA("erro-sistema", "Erro Sistema"),
    ENTIDADE_EM_USO("entidade-em-uso", "Entidade em Uso"),
    MENSAGEM_INCOMPREENSIVEL(",mensagem-incompreensivel", "Mensagem Incompreensivel"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parametro inválido"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");


    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "http://www.gifood.com.br/" + path ;
        this.title = title;
    }
}
