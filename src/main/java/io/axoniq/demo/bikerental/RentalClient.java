package io.axoniq.demo.bikerental;

import com.vaadin.flow.server.VaadinServlet;
import io.axoniq.demo.bikerental.coreapi.rental.BikeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

    private String baseUrl;
    @Autowired
    public RentalClient(RestTemplateBuilder restTemplateBuilder, @Value("${rentalservice.baseurl}") String baseUrl) {
        this.baseUrl=baseUrl;
        System.out.println("creating client for" + baseUrl);
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(baseUrl);
        this.restTemplate = restTemplateBuilder.build();
        restTemplate.setUriTemplateHandler(uriBuilderFactory);
    }

    public String getBaseUrl() {
        return baseUrl;
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

    //GET {{hostname}}/findPayment?reference=95496b07-1e9f-4639-8ff3-29b1c58cd89a
    public String findPayment(String reference) {
        return restTemplate.getForObject("/findPayment?reference={reference}", String.class,Map.of("reference",reference));
    }

    // POST {{hostname}}/acceptPayment?id=7c38518b-3c4e-4b4b-9172-c4bef65ff3b0
    public String acceptPayment(String id) {

        Map<String, String> params = Map.of("id",id);
        return restTemplate.postForObject("acceptPayment?id={id}",params,String.class,params);
    }

    // POST {{hostname}}/returnBike?bikeId=8e648607-6294-44aa-a71a-b7a2b0dae6e2
    public String returnBike(String bikeId) {

        Map<String, String> params = Map.of("bikeId",bikeId);
        return restTemplate.postForObject("returnBike?bikeId={bikeId}",params,String.class,params);
    }

    public static <T> T get(Class<T> serviceType)
    {
        return WebApplicationContextUtils
                .getWebApplicationContext(VaadinServlet.getCurrent().getServletContext())
                .getBean(serviceType);
    }
}