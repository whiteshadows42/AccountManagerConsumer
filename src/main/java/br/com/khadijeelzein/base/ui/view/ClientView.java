package br.com.khadijeelzein.base.ui.view;

import br.com.khadijeelzein.base.domain.service.ClientService;
import br.com.khadijeelzein.base.ui.component.ViewToolbar;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("clients")
@PageTitle("Cadastro de Cliente")
@Menu(title = "Cadastro de Cliente", order = 1, icon = "vaadin:user")
public class ClientView extends Main {
    private final ClientForm clientForm;
    ClientView(ClientService clientService) {
        clientForm = new ClientForm(clientService);
        addClassName(LumoUtility.Padding.MEDIUM);
        add(new ViewToolbar("Cadastro de Cliente"));
        add(clientForm);
    }
    public static void showClientView() {
        UI.getCurrent().navigate(ClientView.class);
    }
}