package br.com.khadijeelzein.base.ui.view;

import br.com.khadijeelzein.base.domain.model.ClientRequest;
import br.com.khadijeelzein.base.domain.service.ClientService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.validator.RegexpValidator;

import java.time.LocalDate;
import java.util.Optional;


public class ClientForm extends Composite<FormLayout> {
    private ClientRequest formDataObject;
    private BeanValidationBinder<ClientRequest> binderValidator = new BeanValidationBinder<>(ClientRequest.class);
    private final ClientService clientService;

    public ClientForm(ClientService clientService) {
        this.clientService = clientService;
        // Create the components
        var cpfField = new TextField("CPF");
        var nameField = new TextField("Nome");
        var birthadyField = new DatePicker("Data de Nascimento");
        var formLayout = getContent();
        formLayout.add(cpfField);
        formLayout.add(nameField);
        formLayout.add(birthadyField);
        formLayout.add(new Button("Salvar", event -> saveClient()));
        binderValidator.forField(cpfField)
                .asRequired("Informe um CPF")
                .withValidator(
                        new RegexpValidator("Informe um CPF válido",
                                "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}")
                )
                .bind(ClientRequest::getCpf, ClientRequest::setCpf);
        binderValidator.forField(nameField)
                .asRequired("Informe um nome")
                .bind(ClientRequest::getName, ClientRequest::setName);
        binderValidator.forField(birthadyField)
                .asRequired("Informe a data de Nascimento")
                .withValidator((bean, valueContext)-> {
            if (bean != null && bean.isAfter(LocalDate.now())) {
                return ValidationResult.error("Data de nascimento não pode ser futura");
            }
            return ValidationResult.ok();
        })
                .bind(ClientRequest::getBirthday, ClientRequest::setBirthday);
    }

    public Optional<ClientRequest> getFormDataObject() {
        if (formDataObject == null) {
            formDataObject = new ClientRequest();
        }
        if (binderValidator.writeBeanIfValid(formDataObject)) {
            return Optional.of(formDataObject);

        } else {
            return Optional.empty();
        }
    }
    private void saveClient() {
        getFormDataObject().ifPresent(client -> {
                    binderValidator.setBean(formDataObject);
                    if (binderValidator.validate().isOk()) {
                        client = binderValidator.getBean();
                        clientService.save(client);
                        if (clientService.isSuccess()) {
                            var notification = new Notification(
                                    "Cliente Cadastrado com Sucesso");
                            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                            notification.setPosition(Notification.Position.TOP_CENTER);
                            notification.setDuration(3000);
                            UI.getCurrent().access(notification::open);
                        }
                    }
            UI.getCurrent().navigate(ClientView.class);
        });
    }

}