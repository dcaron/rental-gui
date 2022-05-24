package io.axoniq.demo.bikerental.views.return_;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;
import io.axoniq.demo.bikerental.RentalClient;
import io.axoniq.demo.bikerental.views.MainLayout;

@PageTitle("Return")
@Route(value = "return", layout = MainLayout.class)
public class ReturnView extends HorizontalLayout implements BeforeEnterObserver {
    private RentalClient rentalClient = RentalClient.get(RentalClient.class);

    private Button returnButton;
    private QueryParameters queryParameters;

    public ReturnView() {
        System.out.println(queryParameters.getParameters());
        String bikeId = queryParameters.getParameters().get("bikeId").get(0);

        returnButton = new Button("Return bike " + bikeId);
        returnButton.addClickListener(e -> {
            try {
                rentalClient.returnBike(bikeId);

                Notification show = Notification.show("Returned " + bikeId);
                show.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } catch (Exception ex) {
                Notification show = Notification.show("Failed to return " + bikeId);
                show.addThemeVariants(NotificationVariant.LUMO_ERROR);
                ex.printStackTrace();
            }
            returnButton.getUI().ifPresent(ui -> ui.navigate("find"));
        });

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END,  returnButton);

        add(returnButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Location location = beforeEnterEvent.getLocation();
        queryParameters = location.getQueryParameters();
    }
}
