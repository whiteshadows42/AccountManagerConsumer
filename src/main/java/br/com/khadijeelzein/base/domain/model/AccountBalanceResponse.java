package br.com.khadijeelzein.base.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountBalanceResponse {
    @JsonProperty("saldo_atual")
    Double currentBalance;
}
