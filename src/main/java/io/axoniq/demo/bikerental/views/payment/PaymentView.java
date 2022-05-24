package io.axoniq.demo.bikerental.views.payment;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;
import io.axoniq.demo.bikerental.RentalClient;
import io.axoniq.demo.bikerental.views.MainLayout;

@PageTitle("Payment")
@Route(value = "payment", layout = MainLayout.class)
public class PaymentView extends HorizontalLayout implements BeforeEnterObserver {
    private RentalClient rentalClient = RentalClient.get(RentalClient.class);

    private Button pay;
    private QueryParameters queryParameters;


    public PaymentView() {
        pay = new Button("Pay");

        pay.addClickListener(e -> {


            String reference = null;
            try {
                reference = queryParameters.getParameters().get("reference").get(0);
                System.out.println("paying: "+ reference);

                String paymentId = rentalClient.findPayment(reference);

                if(paymentId == null) throw new RuntimeException("unable to find payment");

                rentalClient.acceptPayment(paymentId);
                Notification notification = Notification.show("Payment accepted " + reference);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } catch (Exception ex) {
                Notification notification = Notification.show("Payment failed " + reference);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                ex.printStackTrace();
            }
            System.out.println("Payment accepted: "+ reference);
            pay.getUI().ifPresent(ui -> ui.navigate("return",queryParameters));
        });

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, pay);

        add(pay);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Location location = beforeEnterEvent.getLocation();
        queryParameters = location.getQueryParameters();
    }
}
