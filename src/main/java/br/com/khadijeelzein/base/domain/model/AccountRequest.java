package br.com.khadijeelzein.base.domain.model;

import br.com.khadijeelzein.base.domain.enums.TypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class AccountRequest {
    @JsonProperty("cliente")
    @CPF
    private String clientCpf;

    @JsonProperty("tipo_conta")
    private TypeEnum type;
}
