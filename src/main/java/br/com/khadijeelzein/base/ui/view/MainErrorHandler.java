package br.com.khadijeelzein.base.ui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpClientErrorException;

@Configuration
class MainErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(MainErrorHandler.class);

    @Bean
    public VaadinServiceInitListener errorHandlerInitializer() {
        return (event) -> event.getSource().addSessionInitListener(
                sessionInitEvent -> sessionInitEvent.getSession().setErrorHandler(errorEvent -> {
                    if(errorEvent.getThrowable() instanceof HttpClientErrorException.Conflict) {
                        log.error("CPF não pode ser duplicado", errorEvent.getThrowable());
                        errorEvent.getComponent().flatMap(Component::getUI).ifPresent(ui -> {
                            var notification = new Notification(
                                    "CPF não pode ser duplicado");
                            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                            notification.setPosition(Notification.Position.TOP_CENTER);
                            notification.setDuration(3000);
                            ui.access(notification::open);
                        });
                    }
                    else if(errorEvent.getThrowable() instanceof HttpClientErrorException.BadRequest) {
                        log.error("Requisição Inválida", errorEvent.getThrowable());
                        errorEvent.getComponent().flatMap(Component::getUI).ifPresent(ui -> {
                            var notification = new Notification(
                                    "Requisição Inválida");
                            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                            notification.setPosition(Notification.Position.TOP_CENTER);
                            notification.setDuration(3000);
                            ui.access(notification::open);
                        });
                    }else if(errorEvent.getThrowable() instanceof HttpClientErrorException.NotFound) {
                        log.error("Não encontrado", errorEvent.getThrowable());
                        errorEvent.getComponent().flatMap(Component::getUI).ifPresent(ui -> {
                            var notification = new Notification(
                                    "Não encontrado");
                            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                            notification.setPosition(Notification.Position.TOP_CENTER);
                            notification.setDuration(3000);
                            ui.access(notification::open);
                        });
                    }else {
                        log.error("Ocorreu um erro inesperado", errorEvent.getThrowable());
                        errorEvent.getComponent().flatMap(Component::getUI).ifPresent(ui -> {
                            var notification = new Notification(
                                    "Ocorreu um erro inesperado");
                            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                            notification.setPosition(Notification.Position.TOP_CENTER);
                            notification.setDuration(3000);
                            ui.access(notification::open);
                        });
                    }
                }));
    }
}
