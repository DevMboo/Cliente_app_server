package io.github.devMboo.rest.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    /*
    * Retorna uma lista de erros
    * para que seja formatados na
    * classe ApplicationControllerAdvice
    * */
    @Getter
    private List<String>errors;

    public  ApiErrors(List<String> errors){
        this.errors = errors;
    }

    public ApiErrors(String messages){
        this.errors = Arrays.asList(messages);
    }
}
