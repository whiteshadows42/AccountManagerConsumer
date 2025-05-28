package br.com.khadijeelzein.base.domain.enums;

import lombok.Getter;

@Getter
public enum TypeEnum {
    CORRENTE(1,"CORRENTE"),
    POUPANCA(2,"POUPANÇA");

    private final String type;
    private final Integer id;

    TypeEnum(int id, String type) {
        this.type = type;
        this.id = id;
    }
}