package br.com.alg.giraofoodapi.api.exceptionhandler;

import br.com.alg.giraofoodapi.core.validation.ValidacaoException;
import br.com.alg.giraofoodapi.domain.exception.EntidadeEmUsoException;
import br.com.alg.giraofoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.alg.giraofoodapi.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static String ERRO_MSG_USER = "Ocorreu um erro interno inesperado no sistema." +
            " Tente novamente e se o problema persistir, entre em contato com o administrador do sistema";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bidingResult = ex.getBindingResult();

        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if(rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status,request) ;
        } else if(rootCause instanceof IgnoredPropertyException) {
            return handleIgnoredPropertyException((IgnoredPropertyException) rootCause, headers, status, request);
        } else if(rootCause instanceof UnrecognizedPropertyException) {
            return handleUnrecognizedPropertyException((UnrecognizedPropertyException) rootCause, headers, status, request);
        }

        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe";
        Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail).build();
        return super.handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = this.joinPath(ex.getPath());
        String detail = String.format("A propriedade '%s' recebeu o valor '%s',"
                + " que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.", path, ex.getValue(), ex.getTargetType());
        Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail)
                .userMessage(ERRO_MSG_USER)
                .build();
        return super.handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = e.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        String detail = e.getMessage();
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String detail = e.getMessage();

        Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, detail).build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleRuntimeException(Exception e, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.ERRO_SISTEMA;
        log.error(e.getMessage(), e);

        Problem problem = createProblemBuilder(status, problemType, ERRO_MSG_USER).build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    public ResponseEntity<Object> handleIgnoredPropertyException(IgnoredPropertyException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = this.joinPath(e.getPath());
        String detail = String.format("A propriedade '%s' não é válida", path);
        Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, detail)
                .userMessage(ERRO_MSG_USER)
                .build();
        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    public ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = this.joinPath(e.getPath());
        String detail = String.format("A propriedade '%s' não é reconhecida como campos válido", path);
        Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, detail).build();
        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler({ValidacaoException.class })
    public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResul(), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if(ex instanceof  MethodArgumentTypeMismatchException) {
            return  handleMethodArgumentoTypeMismatchException((MethodArgumentTypeMismatchException)ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("O recurso %s , que você tentou acessar, é inexistente.", ex.getRequestURL());
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(ERRO_MSG_USER)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentoTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s' que é de um tipo inválido." +
                "Corrrija e informe um valor compatível com o tipo %s.", ex.getName(), ex.getValue(), ex.getRequiredType());
        Problem problem = createProblemBuilder(status, ProblemType.PARAMETRO_INVALIDO, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<Problem.Object> problemObjects = bindingResult.getAllErrors()
                .stream()
                .map(objectError -> {
                            String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                            String name = objectError.getObjectName();

                            if(objectError instanceof FieldError) {
                                name  = ((FieldError)objectError).getField();
                            }
                            return Problem.Object.builder()
                                    .name(name)
                                    .userMessage(message).build();
                        }
                ).collect(Collectors.toList());

        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        Problem problem = createProblemBuilder(status, problemType, detail)
                .objects(problemObjects)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(reference -> reference.getFieldName())
                .collect(Collectors.joining("."));
    }
    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType type, String detail) {
        return Problem.builder()
                .status(status.value())
                .type(type.getUri())
                .title(type.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }
}
