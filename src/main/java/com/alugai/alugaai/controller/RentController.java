package com.alugai.alugaai.controller;

import com.alugai.alugaai.dto.RentRegistrationDto;
import com.alugai.alugaai.security.CurrentUserProvider;
import com.alugai.alugaai.service.RentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequiredArgsConstructor
public class RentController {

    private final CurrentUserProvider currentUserProvider;
    private final RentService rentService;

    @Value("pk_test_51Ong2YCoptbkQfYfcQHOQ9VAz7BQfp0s7dFfNaBBhKh34TZNFTWGywrYDaweGOmtgzXx9YO0BeuzdL9J9r3thJwk00heME0y1r")
    private String stripePublicKey;

    @Value("sk_test_51Ong2YCoptbkQfYfY7sTd262UbXnbdShXXx0kjNBlKDWx006K0SjjFlAwsEg4B0mfaEVd3Tr6HrXNt7Z9CYskWEc00Q2HMFSv3")
    private String stripePrivateKey;

    @GetMapping("/rents")
    public String getRentPage(Model model) {

        model.addAttribute("stripePublicKey", stripePublicKey);

        var loggedUser = currentUserProvider.getSessionUser();
        var rents = rentService.findByRenter(loggedUser);

        model.addAttribute("rents", rents);

        return "rents";

    }

    @GetMapping("/rents/pending")
    public String getPendingRentPage(Model model) {

        var loggedUser = currentUserProvider.getSessionUser();
        var rents = rentService.findByProductOwner(loggedUser);

        model.addAttribute("rents", rents);

        return "pending-rents";

    }

    @PostMapping("/rent/{id}")
    public String addRent(
            @ModelAttribute("rent") RentRegistrationDto rent,
            @PathVariable Long id
    ) {

        rent.setProductId(id);

        var savedRent = rentService.create(rent);

        if (savedRent.isEmpty()) {
            return "redirect:/";
        }

        return "redirect:/rents";

    }

    @GetMapping("/rent/payment/{id}")
    public String getRentPaymentForm(
            @PathVariable Long id,
            HttpServletRequest request,
            Model model
    ) throws StripeException {

        Stripe.apiKey = stripePrivateKey;

        var baseURL = ServletUriComponentsBuilder
                .fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();

        var optionalRent = rentService.findById(id);

        if (optionalRent.isEmpty()) {
            return "redirect:/";
        }

        var product = SessionCreateParams.LineItem.builder()
                .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                                .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData
                                                .builder()
                                                .setName(optionalRent.get().getProduct().getName())
                                                .setDescription(optionalRent.get().getProduct().getDescription())
                                                .build())
                                .setUnitAmount(
                                        optionalRent.get().getProduct().getPrice() * optionalRent.get().getNumberOfDays() * 100)
                                .setCurrency("brl")
                                .build())
                .setQuantity(1L)
                .build();

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                        .addLineItem(product)
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(baseURL + "/rents/pending/accept/" + optionalRent.get().getId())
                        .setCancelUrl(baseURL + "/rents")
                        .build();

        Session session = Session.create(params);

        model.addAttribute("sessionId", session.getId());

        return "payment";

    }

    @GetMapping("/rents/pending/accept/{id}")
    public String acceptRent(
            @PathVariable Long id
    ) {

        rentService.acceptRent(id);
        return "redirect:/rents";

    }

}
