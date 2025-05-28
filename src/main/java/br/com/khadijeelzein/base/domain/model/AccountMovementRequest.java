package br.com.khadijeelzein.base.domain.model;

import br.com.khadijeelzein.base.domain.enums.MovementTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountMovementRequest {
    @JsonProperty("conta_origem")
    private Long accountNbrOrigin;
    @JsonProperty("conta_destino")
    private Long accountNbrDestination;
    @JsonProperty("valor")
    private Double amount;
    @JsonProperty("tipo")
    private MovementTypeEnum type;
}