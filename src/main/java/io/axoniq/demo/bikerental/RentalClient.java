package io.axoniq.demo.bikerental;

import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import io.axoniq.demo.bikerental.coreapi.rental.BikeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.HashMap;
import java.util.Map;

//@SpringComponent
//@UIScope
//@VaadinSessionScope
@Service
public class RentalClient {
    private final RestTemplate restTemplate;

    @Autowired
    public RentalClient(RestTemplateBuilder restTemplateBuilder, @Value("${rentalservice.baseurl}") String baseUrl) {
        System.out.println("creating client for" + baseUrl);
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(baseUrl);
        this.restTemplate = restTemplateBuilder.build();
        restTemplate.setUriTemplateHandler(uriBuilderFactory);
    }

    public BikeStatus[] getBikes() {
        ResponseEntity<BikeStatus[]> response = restTemplate.getForEntity("/bikes", BikeStatus[].class);
        return response.getBody();
    }

    // GET {{hostname}}/bikes/bc3a2bf1-120a-4b3f-84a9-9a8b67a73624
    public BikeStatus getBike(String bikeId) {
        return restTemplate.getForObject("/bikes/{bikeId}", BikeStatus.class, bikeId);
    }

    //POST {{hostname}}/requestBike?bikeId=1972f57a-babe-44a7-b5dd-1a1333da521b
    public String requestBike(String bikeId) {
        Map<String, String> params = new HashMap<>();
        params.put("bikeId",bikeId);

        return restTemplate.postForObject("requestBike?bikeId={bikeId}",params,String.class,params);
    }

    public static <T> T get(Class<T> serviceType)
    {
        return WebApplicationContextUtils
                .getWebApplicationContext(VaadinServlet.getCurrent().getServletContext())
                .getBean(serviceType);
    }
}