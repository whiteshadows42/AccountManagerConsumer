package br.com.khadijeelzein.base.ui.view;

import br.com.khadijeelzein.base.domain.model.AccountMovementHistoryRequest;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.converter.StringToLongConverter;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AccountMovementHistoryForm extends Composite<FormLayout> {
    private final Binder<AccountMovementHistoryRequest> binder;
    private AccountMovementHistoryRequest formDataObject;

    public AccountMovementHistoryForm() {
        // Create the components
        var accountnNbrField = new TextField("Conta");
        var startDateField = new TextField("Data de Início");
        startDateField.setPattern("\\d{2}/\\d{2}/\\d{4}");
        var endDateField = new TextField("Data de Fim");
        endDateField.setPattern("\\d{2}/\\d{2}/\\d{4}");
        var formLayout = getContent();
        formLayout.add(accountnNbrField, startDateField, endDateField);
        binder = new Binder<>();
        binder.forField(accountnNbrField)
                .withConverter( new StringToLongConverter("Informe um número"))
                .asRequired("Informe a conta")
                .withValidator(number -> number!=null && number > 0,"Número de conta deve ser maior que 0")
                .bind(AccountMovementHistoryRequest::getId, AccountMovementHistoryRequest::setId);
        binder.forField(startDateField)
                .withValidator((bean, valueContext)-> {
                    var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    if (!bean.isBlank() && LocalDate.parse(bean,formatter).isAfter(LocalDate.now())) {
                        return ValidationResult.error("Data inicial não pode ser futura");
                    }
                    return ValidationResult.ok();
                })
                .bind(AccountMovementHistoryRequest::getStartDate, AccountMovementHistoryRequest::setStartDate);
        binder.forField(endDateField)
                .withValidator((bean, valueContext)-> {
                    var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    if (!bean.isBlank() && !startDateField.getValue().isBlank() && LocalDate.parse(bean,formatter).isBefore(LocalDate.parse(startDateField.getValue(),formatter))) {
                        return ValidationResult.error("Data final não pode ser antes da inicial");
                    }
                    return ValidationResult.ok();
                })
                .bind(AccountMovementHistoryRequest::getEndDate, AccountMovementHistoryRequest::setEndDate);
    }
    public void setFormDataObject(@Nullable AccountMovementHistoryRequest formDataObject) {
        binder.setBean(formDataObject);
    }
    public Optional<AccountMovementHistoryRequest> getFormDataObject() {
        if (formDataObject == null) {
            formDataObject = new AccountMovementHistoryRequest();
        }
        if (binder.writeBeanIfValid(formDataObject)) {
            return Optional.of(formDataObject);
        } else {
            return Optional.empty();
        }
    }
}
