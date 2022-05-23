package io.axoniq.demo.bikerental.views.payment;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import io.axoniq.demo.bikerental.RentalClient;
import io.axoniq.demo.bikerental.views.MainLayout;

import java.util.List;
import java.util.Map;

@PageTitle("Payment")
@Route(value = "payment", layout = MainLayout.class)
public class PaymentView extends HorizontalLayout implements BeforeEnterObserver{
    private RentalClient rentalClient = RentalClient.get(RentalClient.class);

    //private TextField name;
    private Button pay;
    private Map<String, List<String>> parametersMap;

    public PaymentView() {
        //name = new TextField("Your name");
        // GET {{hostname}}/findPayment?reference=95496b07-1e9f-4639-8ff3-29b1c58cd89a
        pay = new Button("Pay");
        pay.addClickListener(e -> {
            List<String> reference = parametersMap.getOrDefault("reference", List.of("bleble"));
            System.out.println(""+ reference);
            Notification.show("Hello " + reference);
        });

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, pay);

        add(pay);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Location location = beforeEnterEvent.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();

        parametersMap = queryParameters
                .getParameters();
    }
}
