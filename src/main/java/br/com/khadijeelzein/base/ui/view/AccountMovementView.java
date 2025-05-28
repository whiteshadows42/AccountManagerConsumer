package br.com.khadijeelzein.base.ui.view;

import br.com.khadijeelzein.base.domain.service.AccountMovementService;
import br.com.khadijeelzein.base.domain.service.AccountService;
import br.com.khadijeelzein.base.ui.component.ViewToolbar;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("/transactions")
@PageTitle("Cadastro de Movimentações Bancárias")
@Menu(title = "Cadastro de Movimentações", order = 3, icon = "vaadin:coins")
public class AccountMovementView extends Main {
    private final AccountMovementForm accountMovementForm = new AccountMovementForm();
    private final AccountMovementService accountMovementService;

    AccountMovementView(AccountMovementService accountMovementService) {
        this.accountMovementService = accountMovementService;
        addClassName(LumoUtility.Padding.MEDIUM);
        add(new ViewToolbar("Cadastro de Movimentações Bancárias"));
        add(accountMovementForm);
        add(new Button("Salvar", event -> saveAccountMovement()));
    }

    public static void showAccountMovementView() {
        UI.getCurrent().navigate(AccountMovementView.class);
    }

    private void saveAccountMovement() {
        accountMovementForm.getFormDataObject().ifPresent(accountMovement -> {
            accountMovementService.save(accountMovement);
            UI.getCurrent().navigate(AccountMovementView.class);
        });
    }
}