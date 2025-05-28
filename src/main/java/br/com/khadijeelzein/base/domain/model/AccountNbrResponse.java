package br.com.khadijeelzein.base.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountNbrResponse {
    @JsonProperty("numero_conta")
    Long accountNbr;
}
