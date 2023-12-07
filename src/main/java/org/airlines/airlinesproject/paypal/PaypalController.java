package org.airlines.airlinesproject.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.airlines.airlinesproject.client.ClientService;
import org.airlines.airlinesproject.client.dto.ClientPlaceOrderRequest;
import org.airlines.airlinesproject.cruises.dto.CruiseRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/pay")
public class PaypalController {

    private final PaypalService paypalService;
    private final ClientService clientService;

    public static final String SUCCESS_URL = "api/pay/success";

//    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/home")
    public String home(){
        return "home";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public String payment(@RequestBody Order order) {

        try {
            ClientPlaceOrderRequest orderRequest = new ClientPlaceOrderRequest(
                    order.getCruiseId(),
                    order.getFirstName(),
                    order.getLastName(),
                    order.getEmail());



            final Payment payment = paypalService.createPayment(
                    order.getPrice(),
                    order.getCurrency(),
                    order.getMethod(),
                    order.getIntent(),
                    order.getDescription(),
                    "http://localhost:8040/api/pay/cancel",
                    "http://localhost:8040/api/pay/success" +
                            "?cruiseId=" + orderRequest.getCruiseId() +
                            "&firstName=" + orderRequest.getFirstName() +
                            "&lastName=" + orderRequest.getLastName() +
                            "&email=" + orderRequest.getEmail()

            );
            for(Links link: payment.getLinks()){
                if(link.getRel().equals("approval_url")){
                    return "redirect:" + link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/";
    }


    @GetMapping(path = "/cancel")
    public String cancelPay() {
        return "cancel";
    }

//    TODO: METHOD SHOULD BE POST
    @GetMapping(path = "/success")
    public String successPay(
            @RequestParam("cruiseId") String cruiseId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            //Executing payment
            Payment payment = paypalService.executePayment(paymentId, payerId);

            //Sing a cruise to client
            clientService.saveCruiseToClient(
                    ClientPlaceOrderRequest
                    .builder()
                            .cruiseId(UUID.fromString(cruiseId))
                            .firstName(firstName)
                            .lastName(lastName)
                            .email(email)
                    .build());

            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }

        return "redirect:/";
    }





}
