package br.com.khadijeelzein.base.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ClientRequest {
    private String cpf;

    @JsonProperty("nome_completo")
    private String name;
    @JsonProperty("data_nascimento")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;
}