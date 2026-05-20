package br.edu.senai.fatesg.ads3.car_repair.core.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String title;
    private String message;

    private ErrorResponse(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public static ErrorResponse error(String message) {
        return new ErrorResponse("Erro", message);
    }

    public static ErrorResponse error(String title, String message) {
        return new ErrorResponse(title, message);
    }

    public static ErrorResponse error(BaseException ex) {
        return new ErrorResponse(ex.getTitle(), ex.getMessage() + " -> " + ex.getMotive());
    }

    public static ErrorResponse error(BusinessException ex) {
        return new ErrorResponse(ex.getTitle(), ex.getMessage() + " -> " + ex.getMotive());
    }

    public static ErrorResponse error(FieldValidationException ex) {
        return new ErrorResponse(ex.getTitle(), ex.getMessage() + " -> " + ex.getMotive());
    }

    public static ErrorResponse error(RuleValidationException ex) {
        return new ErrorResponse(ex.getTitle(), ex.getMessage() + " -> " + ex.getMotive());
    }
}
