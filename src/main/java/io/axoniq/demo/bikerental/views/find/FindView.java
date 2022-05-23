package io.axoniq.demo.bikerental.views.find;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import io.axoniq.demo.bikerental.RentalClient;
import io.axoniq.demo.bikerental.coreapi.rental.BikeStatus;
import io.axoniq.demo.bikerental.views.MainLayout;
import io.axoniq.demo.bikerental.views.payment.PaymentView;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PageTitle("Find")
@Route(value = "find", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class FindView extends VerticalLayout {
    private RentalClient rentalClient = RentalClient.get(RentalClient.class);

    private ComboBox<String> location;
    private Button ok;
    private ComboBox<String> bike;

    public FindView() {
        BikeStatus[] bikes = rentalClient.getBikes();

        // get set of unique locations
        Set<String> locationChoices = Stream.of(bikes).map(x -> x.getLocation()).collect(Collectors.toSet());

        System.out.println("Nr of locations: " + locationChoices.size());
        location = new ComboBox<>("Pickup location", locationChoices);

        bike = new ComboBox<String>("Bike", Collections.<String>emptyList());
        // bike.setVisible(false);

        ok = new Button("Select bike");
        ok.setEnabled(false);

        location.addValueChangeListener(e -> {
            List<String> bikeChoices = Stream.of(bikes).filter(c -> c.getLocation().equals(location.getValue())).map(x -> x.getBikeId()).collect(Collectors.toList());
            System.out.println("Nr of bikes: " + bikeChoices.size());

            bike.setItems(bikeChoices);
            // bike.setVisible(true);
           // ok.setEnabled(true);
        });
        bike.addValueChangeListener(e -> {
            ok.setEnabled(true);
            System.out.println("Bike selected: " + bike.getValue());
        });
        ok.addClickListener(e -> {

            BikeStatus bikeStatus = rentalClient.getBike( bike.getValue());
            Notification.show("Bike selected: " + bikeStatus);
            String reference = rentalClient.requestBike(bikeStatus.getBikeId());
            Notification.show("Response: " + reference);

//            ok.getUI().ifPresent(ui -> ui.navigate("payment"));
            ok.getUI().ifPresent(ui -> ui.navigate("payment", QueryParameters.simple(Map.of("reference", reference))));
        });

        setMargin(true);
        //setVerticalComponentAlignment(Alignment.END, location, ok);

        add(location, bike, ok);
    }

}
