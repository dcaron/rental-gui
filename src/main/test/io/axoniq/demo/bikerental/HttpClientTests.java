//package io.axoniq.demo.bikerental;
//
//import org.springframework.boot.web.client.RestTemplateBuilder;
//
//import java.util.Arrays;
//import org.junit.Test;
//
//public class HttpClientTests {
//    private RentalClient rentalClient = new RentalClient(new RestTemplateBuilder(),"http://bike-rental-default.cnr.axon.devops.cloud-native.biz/");
//    @Test
//    public void test() {
//        Arrays.stream(rentalClient.getBikes()).forEach(bikeStatus -> System.out.println(bikeStatus));
//    }
//
//}
