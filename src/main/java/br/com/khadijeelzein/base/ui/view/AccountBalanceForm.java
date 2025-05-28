package br.com.khadijeelzein.base.ui.view;

import br.com.khadijeelzein.base.domain.model.AccountNbrResponse;
import br.com.khadijeelzein.base.domain.model.AccountRequest;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import jakarta.annotation.Nullable;

import java.util.Optional;

public class AccountBalanceForm extends Composite<FormLayout> {
    private final Binder<AccountNbrResponse> binder;
    private AccountNbrResponse formDataObject;

    public AccountBalanceForm() {
        // Create the components
        var accountNbrField = new TextField("Número da Conta Bancária");
        var formLayout = getContent();
        formLayout.add(accountNbrField);
        binder = new Binder<>();

        binder.forField(accountNbrField)
                .withConverter(new StringToLongConverter("Informe um número"))
                .asRequired("Informe um número de conta")
                .withValidator(number -> number > 0,"Número de conta deve ser maior que 0")
                .bind(AccountNbrResponse::getAccountNbr, AccountNbrResponse::setAccountNbr);
    }
    public void setFormDataObject(@Nullable AccountNbrResponse formDataObject) {
        binder.setBean(formDataObject);
    }
    public Optional<AccountNbrResponse> getFormDataObject() {
        if (formDataObject == null) {
            formDataObject = new AccountNbrResponse();
        }
        if (binder.writeBeanIfValid(formDataObject)) {
            return Optional.of(formDataObject);
        } else {
            return Optional.empty();
        }
    }
}
