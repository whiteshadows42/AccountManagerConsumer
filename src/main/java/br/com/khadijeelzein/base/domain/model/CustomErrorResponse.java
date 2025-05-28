package br.com.khadijeelzein.base.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomErrorResponse {
    private HttpStatus status;
    @JsonProperty("mensagem")
    private String message;
    @JsonProperty("erro")
    private String error;

    public CustomErrorResponse(HttpStatus status, String message, String error) {
            super();
            this.status = status;
            this.message = message;
            this.error = error;
    }
    public CustomErrorResponse(HttpStatus status) {
        super();
        this.status = status;
    }
    public CustomErrorResponse(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }
}
