package io.axoniq.demo.bikerental.views.payment;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.axoniq.demo.bikerental.views.MainLayout;

@PageTitle("Payment")
@Route(value = "payment", layout = MainLayout.class)
public class PaymentView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public PaymentView() {
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);
    }

}
