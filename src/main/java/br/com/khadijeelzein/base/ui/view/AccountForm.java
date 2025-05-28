package br.com.khadijeelzein.base.ui.view;

import br.com.khadijeelzein.base.domain.enums.TypeEnum;
import br.com.khadijeelzein.base.domain.model.AccountRequest;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.RegexpValidator;
import jakarta.annotation.Nullable;

import java.util.Optional;


public class AccountForm extends Composite<FormLayout> {
    private final Binder<AccountRequest> binder;
    private AccountRequest formDataObject;

    public AccountForm() {
        // Create the components
        var clientCpfField = new TextField("CPF do Cliente");
        var typeField = new Select<TypeEnum>();
        typeField.setLabel("Tipo de Conta");
        typeField.setItems(TypeEnum.CORRENTE,TypeEnum.POUPANCA);
        var formLayout = getContent();
        formLayout.add(clientCpfField);
        formLayout.add(typeField);
        binder = new Binder<>();

        binder.forField(clientCpfField)
                .asRequired("Informe um CPF")
                .withValidator(
                        new RegexpValidator("Informe um CPF válido",
                                "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}")
                )
                .bind(AccountRequest::getClientCpf, AccountRequest::setClientCpf);
        binder.forField(typeField)
                .asRequired("Informe um Tipo de Conta")
                .bind(AccountRequest::getType, AccountRequest::setType);
    }
    public void setFormDataObject(@Nullable AccountRequest formDataObject) {
        binder.setBean(formDataObject);
    }
    public Optional<AccountRequest> getFormDataObject() {
        if (formDataObject == null) {
            formDataObject = new AccountRequest();
        }
        if (binder.writeBeanIfValid(formDataObject)) {
            return Optional.of(formDataObject);
        } else {
            return Optional.empty();
        }
    }
}
