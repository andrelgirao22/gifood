package br.com.alg.giraofoodapi.infrastructure.service.report;

public class ReportException extends RuntimeException {

    public ReportException(String mensagem) {
        super(mensagem);
    }

    public ReportException(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }

}
