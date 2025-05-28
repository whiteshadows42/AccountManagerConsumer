package br.com.khadijeelzein.base.ui.view;

import br.com.khadijeelzein.base.domain.model.AccountMovementResponse;
import br.com.khadijeelzein.base.domain.service.AccountMovementService;
import br.com.khadijeelzein.base.ui.component.ViewToolbar;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Route("/accounts/transactions")
@PageTitle("Histórico de Movimentações Bancárias")
@Menu(title = "Histórico de Movimentações", order = 5, icon = "vaadin:coins")
public class AccountMovementHistoryView extends Main {
    private final AccountMovementHistoryForm accountMovementHistoryForm = new AccountMovementHistoryForm();
    private final AccountMovementService accountMovementService;
    private Grid<AccountMovementResponse> grid = new Grid<>(AccountMovementResponse.class, false);
    AccountMovementHistoryView(AccountMovementService accountMovementService) {
        this.accountMovementService = accountMovementService;
        addClassName(LumoUtility.Padding.MEDIUM);
        add(new ViewToolbar("Histórico de Movimentações"));
        add(accountMovementHistoryForm);
        add(new Button("Pesquisar", event -> searchAccountMovementHistory()));
        grid.addColumn(AccountMovementResponse::getAccountOrigin).setHeader("Conta de Origem")
                .setAutoWidth(true);
        grid.addColumn(AccountMovementResponse::getAccountDestination).setHeader("Conta de Destino")
                .setAutoWidth(true);
        grid.addColumn(AccountMovementResponse::getAmount).setHeader("Valor")
                .setAutoWidth(true);
        grid.addColumn(AccountMovementResponse::getType).setHeader("Tipo")
                .setAutoWidth(true);
        var sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        grid.addColumn(accountMovementResponse ->
                        sdf.format(accountMovementResponse.getDateTime()))
                .setHeader("Data")
                .setAutoWidth(true);
        add(grid);
    }

    public static void showAccountMovementHistoryView() {
        UI.getCurrent().navigate(AccountMovementHistoryView.class);
    }

    private void searchAccountMovementHistory() {
        accountMovementHistoryForm.getFormDataObject().ifPresent(accountMovementHistory -> {
            grid.setItemsPageable(pageable -> accountMovementService.searchAccountMovementHistory(accountMovementHistory,pageable));
            UI.getCurrent().navigate(AccountMovementHistoryView.class);
        });
    }
}