package com.alugai.alugaai.service;

import com.alugai.alugaai.dto.RentRegistrationDto;
import com.alugai.alugaai.model.Product;
import com.alugai.alugaai.model.Rent;
import com.alugai.alugaai.model.User;
import com.alugai.alugaai.repository.RentRepository;
import com.alugai.alugaai.security.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentService {

    private final RentRepository rentRepository;
    private final CurrentUserProvider currentUserProvider;
    private final ProductService productService;

    public Optional<Rent> create(
            RentRegistrationDto rentDto
    ) {

        var loggedUser = currentUserProvider.getSessionUser();

        if (rentDto.getEndDate().isBefore(rentDto.getStartDate())) {
            return Optional.empty();
        }

        var optionalProduct = productService.findById(rentDto.getProductId());

        if (optionalProduct.isEmpty()) {
            return Optional.empty();
        }

        Product product = optionalProduct.get();

        if (Objects.equals(product.getOwner(), loggedUser)) {
            return Optional.empty();
        }

        Rent rent = Rent
                .builder()
                .renter(loggedUser)
                .product(product)
                .startDate(rentDto.getStartDate())
                .endDate(rentDto.getEndDate())
                .build();

        return Optional.of(rentRepository.save(rent));

    }

    public void acceptRent(long id) {


        var optionalRent = rentRepository.findById(id);

        if (optionalRent.isEmpty()) {
            return;
        }

        optionalRent.get().setApproved(true);
        rentRepository.save(optionalRent.get());

    }

    public Optional<Rent> findById(Long id) {
        return rentRepository.findById(id);
    }

    public List<Rent> findByRenter(User user) {
        return rentRepository.findByRenter(user);
    }

    public List<Rent> findByProductOwner(User user) {
        return rentRepository.findByProductOwner(user);
    }

}
