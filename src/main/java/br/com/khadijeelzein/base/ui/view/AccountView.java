package br.com.khadijeelzein.base.ui.view;

import br.com.khadijeelzein.base.domain.model.AccountBalanceResponse;
import br.com.khadijeelzein.base.domain.model.AccountNbrResponse;
import br.com.khadijeelzein.base.domain.service.AccountService;
import br.com.khadijeelzein.base.ui.component.ViewToolbar;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;

@Route("accounts")
@PageTitle("Cadastro de Contas Bancárias")
@Menu(title = "Cadastro de Contas", order = 2, icon = "vaadin:coins")
public class AccountView extends Main implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(responseIsBadRequestException()) {
            event.rerouteToError(
                    BadRequestException.class);
        }
    }

    private final AccountForm accountForm = new AccountForm();
    private final AccountService accountService;
    private final Grid<AccountNbrResponse> grid = new Grid<>(AccountNbrResponse.class, false);
    AccountView(AccountService accountService) {
        this.accountService = accountService;
        addClassName(LumoUtility.Padding.MEDIUM);
        add(new ViewToolbar("Cadastro de Contas Bancárias"));
        add(accountForm);
        add(new Button("Salvar", event->saveAccount()));
        grid.addColumn(AccountNbrResponse::getAccountNbr).setHeader("Número da conta cadastrada")
                .setAutoWidth(true);
        add(grid);
    }

    public static void showAccountView() {
        UI.getCurrent().navigate(AccountView.class);
    }

    private void saveAccount() {
        accountForm.getFormDataObject().ifPresent(account -> {
            accountService.save(account);
            var accountResponse = this.accountService.getAccountNbrResponse();
            if (accountService.isSuccess()) {
                var notification = new Notification(
                        "Conta Cadastrada com Sucesso");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setPosition(Notification.Position.TOP_CENTER);
                notification.setDuration(3000);
                UI.getCurrent().access(notification::open);
            }
            grid.setItems(accountResponse);
            UI.getCurrent().navigate(AccountView.class);
        });
    }
    private boolean responseIsBadRequestException() {
        var errorResponse = this.accountService.getCustomErrorResponse();
        return errorResponse != null && errorResponse.getStatus().equals(HttpStatus.BAD_REQUEST);
    }
}