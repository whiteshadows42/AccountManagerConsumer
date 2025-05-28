package br.com.khadijeelzein.base.ui.view;

import br.com.khadijeelzein.base.domain.model.AccountBalanceResponse;
import br.com.khadijeelzein.base.domain.service.AccountService;
import br.com.khadijeelzein.base.ui.component.ViewToolbar;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;

@Route("/accounts/balance")
@PageTitle("Consulta de saldo da Conta Bancária")
@Menu(title = "Consulta de saldo da Conta", order = 4, icon = "vaadin:coins")
public class AccountBalanceView extends Main {
    private final AccountBalanceForm accountBalanceForm = new AccountBalanceForm();
    private final AccountService accountService;
    private final Grid<AccountBalanceResponse> grid = new Grid<>(AccountBalanceResponse.class,false);
    AccountBalanceView(AccountService accountService) {
        this.accountService = accountService;
        addClassName(LumoUtility.Padding.MEDIUM);
        add(new ViewToolbar("Consulta de Saldo da Conta Bancária"));
        add(accountBalanceForm);
        add(new Button("Pesquisar", event->searchAccountBalance()));
        grid.addColumn(AccountBalanceResponse::getCurrentBalance).setHeader("Saldo atual")
                .setAutoWidth(true).setFlexGrow(0);
        add(grid);
    }

    public static void showAccountBalanceView() {
        UI.getCurrent().navigate(AccountBalanceView.class);
    }

    private void searchAccountBalance() {
        accountBalanceForm.getFormDataObject().ifPresent(accountBalance -> {
            try {
                accountService.searchAccountBalance(accountBalance.getAccountNbr());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            var accountResponse = this.accountService.getAccountBalanceResponse();
            System.out.println(accountResponse);
            AccountBalanceResponse response = accountResponse!= null ?accountResponse: new AccountBalanceResponse();
            grid.setItems(response);
            UI.getCurrent().navigate(AccountBalanceView.class);
        });
    }
    private boolean responseIsBadRequestException() {
        var errorResponse = this.accountService.getCustomErrorResponse();
        return errorResponse != null && errorResponse.getStatus().equals(HttpStatus.BAD_REQUEST);
    }
    private boolean responseIsNotFoundException() {
        var errorResponse = this.accountService.getCustomErrorResponse();
        return errorResponse != null && errorResponse.getStatus().equals(HttpStatus.NOT_FOUND);
    }
}
