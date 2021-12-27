package io.github.devMboo.rest;

import io.github.devMboo.rest.exception.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    /*
    * Formata a saída de erros no console,
    * e garante que nenhum campo fique vázio
    * trazendo uma validação direto do servidor,
    * isso ocorre no momento de insert no banco
    * de dados.
    * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationErros(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = bindingResult.getAllErrors()
                .stream()
                .map( objectError -> objectError.getDefaultMessage() )
                .collect( Collectors.toList());
        return new ApiErrors(messages);
    }
    /*
     * Formata a saída de erros no console,
     * e garante que nenhum campo fique vázio
     * trazendo uma validação direto do servidor,
     *  isso ocorre no momento de update no banco
     * de dados.
     * */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleResponseStatusException(ResponseStatusException ex){
        String mensagemeErro = ex.getMessage();
        HttpStatus codigoStatus = ex.getStatus();
        ApiErrors apiErrors = new ApiErrors(mensagemeErro);
        return new ResponseEntity(apiErrors, codigoStatus);
    }
}
