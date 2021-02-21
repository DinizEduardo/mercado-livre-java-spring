package br.com.zup.mercadolivre.mercadoLivre.shared;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorsOutputDto {

    private List<String> globalErrorMessages = new ArrayList<>();
    private List<br.com.zup.mercadolivre.mercadoLivre.shared.FieldErrorOutputDto> fieldErrors = new ArrayList<>();

    public void addError(String message) {
        globalErrorMessages.add(message);
    }

    public void addFieldError(String field, String message) {
        br.com.zup.mercadolivre.mercadoLivre.shared.FieldErrorOutputDto fieldError = new br.com.zup.mercadolivre.mercadoLivre.shared.FieldErrorOutputDto(field, message);
        fieldErrors.add(fieldError);
    }

    public List<String> getGlobalErrorMessages() {
        return globalErrorMessages;
    }

    public List<br.com.zup.mercadolivre.mercadoLivre.shared.FieldErrorOutputDto> getErrors() {
        return fieldErrors;
    }

    public int getNumberOfErrors() {
        return this.globalErrorMessages.size() + this.fieldErrors.size();
    }
}