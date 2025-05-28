package br.com.khadijeelzein.base.ui.view;

import br.com.khadijeelzein.base.domain.enums.MovementTypeEnum;
import br.com.khadijeelzein.base.domain.model.AccountMovementRequest;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import jakarta.annotation.Nullable;

import java.util.Optional;

public class AccountMovementForm extends Composite<FormLayout> {
    private final Binder<AccountMovementRequest> binder;
    private AccountMovementRequest formDataObject;

    public AccountMovementForm() {
        // Create the components
        var accountOriginField = new TextField("Conta Origem");
        var accountDestinationField = new TextField("Conta Destino");
        var amountField = new NumberField("Valor a ser transferido");
        var typeField = new Select<MovementTypeEnum>();
        typeField.setLabel("Tipo de Movimentação");
        typeField.setItems(MovementTypeEnum.TRANSFERENCIA);
        var formLayout = getContent();
        formLayout.add(accountOriginField, accountDestinationField, amountField, typeField);
        binder = new Binder<>();
        binder.forField(accountOriginField)
                .withConverter( new StringToLongConverter("Informe um número"))
                .asRequired("Informe a conta de Origem")
                .withValidator(number -> number > 0,"Número de conta deve ser maior que 0")
                .bind(AccountMovementRequest::getAccountNbrOrigin, AccountMovementRequest::setAccountNbrOrigin);
        binder.forField(accountDestinationField)
                .withConverter( new StringToLongConverter("Informe um número"))
                .asRequired("Informe a conta de Destino")
                .withValidator(number -> number > 0,"Número de conta deve ser maior que 0")
                .bind(AccountMovementRequest::getAccountNbrDestination, AccountMovementRequest::setAccountNbrDestination);
        binder.forField(amountField)
                .asRequired("Informe o valor")
                .withValidator(number -> number > 0,"Valor a ser transferido deve ser maior que 0")
                .bind(AccountMovementRequest::getAmount, AccountMovementRequest::setAmount);
        binder.forField(typeField)
                .asRequired("Informe o tipo de movimentação")
                .bind(AccountMovementRequest::getType, AccountMovementRequest::setType);
    }
    public void setFormDataObject(@Nullable AccountMovementRequest formDataObject) {
        binder.setBean(formDataObject);
    }
    public Optional<AccountMovementRequest> getFormDataObject() {
        if (formDataObject == null) {
            formDataObject = new AccountMovementRequest();
        }
        if (binder.writeBeanIfValid(formDataObject)) {
            return Optional.of(formDataObject);
        } else {
            return Optional.empty();
        }
    }
}
