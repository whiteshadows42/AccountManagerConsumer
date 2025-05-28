package br.com.khadijeelzein.base.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountMovementHistoryRequest {
    private Long id;
    @JsonProperty("data_inicial")
    private String startDate;
    @JsonProperty("data_final")
    private String endDate;
}
