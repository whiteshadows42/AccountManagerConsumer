package br.com.khadijeelzein.base.domain.enums;

public enum MovementTypeEnum {
    TRANSFERENCIA(1, "TRANSFERÊNCIA");

    private final String type;
    private final Integer id;

    MovementTypeEnum(Integer id, String type) {
        this.id = id;
        this.type = type;
    }
}