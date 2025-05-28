package br.com.khadijeelzein.base.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountMovementResponse {
    @JsonProperty("conta_origem")
    private Long accountOrigin;
    @JsonProperty("conta_destino")
    private Long accountDestination;
    @JsonProperty("valor")
    private Double amount;
    @JsonProperty("tipo")
    private String type;
    @JsonProperty("data_hora")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateTime;
}
