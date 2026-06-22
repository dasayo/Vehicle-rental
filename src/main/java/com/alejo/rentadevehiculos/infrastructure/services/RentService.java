package com.alejo.rentadevehiculos.infrastructure.services;

import com.alejo.rentadevehiculos.api.models.request.RentRequest;
import com.alejo.rentadevehiculos.api.models.response.RentResponse;
import com.alejo.rentadevehiculos.domain.entities.PaymentMethodEntity;
import com.alejo.rentadevehiculos.domain.entities.RentEntity;
import com.alejo.rentadevehiculos.domain.entities.UserEntity;
import com.alejo.rentadevehiculos.domain.entities.VehicleEntity;
import com.alejo.rentadevehiculos.domain.repositories.PaymentMethodRepository;
import com.alejo.rentadevehiculos.domain.repositories.RentRepository;
import com.alejo.rentadevehiculos.domain.repositories.UserRepository;
import com.alejo.rentadevehiculos.domain.repositories.VehicleRepository;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IRentService;
import com.alejo.rentadevehiculos.infrastructure.mappers.RentMapper;
import com.alejo.rentadevehiculos.util.Status;
import com.alejo.rentadevehiculos.util.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RentService implements IRentService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final RentRepository rentRepository;
    private final PaymentValidationService paymentValidationService;
    private final RentPricingService rentPricingService;
    private final RentMapper rentMapper;

    @Override
    public void createRent(RentRequest request) {
        UserEntity user = userRepository.findUserById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundExeption("The id is not valid"));
        PaymentMethodEntity paymentMethod = paymentMethodRepository
                .findPaymentByPaymentId(request.getPaymentMethodId())
                .orElseThrow(() -> new PaymentNotFoundException("the Id on Method is not valid"));
        VehicleEntity vehicle = vehicleRepository.getVehicle(request.getVehicleId())
                .orElseThrow(() -> new VehicleNotFoundException("The license plate on Vehicle is not valid"));

        if (!user.getId().equals(paymentMethod.getUser().getId())) {
            throw new BadRequestRentalVehiclesException("The payment method does not belong to the user.");
        } else if (!vehicle.getIsAvailable()) {
            throw new BadRequestRentalVehiclesException("Vehicle is not available.");
        }

        vehicle.setIsAvailable(false);
        vehicleRepository.save(vehicle);

        RentEntity rentEntity = RentEntity.builder()
                .status(Status.OPEN)
                .vehicle(vehicle)
                .paymentMethod(paymentMethod)
                .startDate(LocalDateTime.now())
                .value(BigDecimal.valueOf(0))
                .build();
        rentRepository.save(rentEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<RentResponse> getAllRents() {
        return rentRepository.findAll()
                .stream().map(rentMapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public RentResponse getRentById(Long id) {
        return rentMapper
                .toResponse(
                        rentRepository
                                .findById(id)
                                .orElseThrow(()->new RentNotFoundException("The id is no valid")
                                )
                );

    }

    @Override
    public RentResponse updateStatus(Long id) throws Exception {
        RentEntity rent = rentRepository.findById(id)
                .orElseThrow(() -> new RentNotFoundException("RenId is not valid"));
        rent.setValue(rentPricingService.calculatePrice(rent.getStartDate(), LocalDateTime.now(), rent.getVehicle().getRate()));
        boolean paymentResult = paymentValidationService.simulatePayment(rent);
        if (paymentResult) {
            rent.setStatus(Status.UNDER_REVIEW);
            rentRepository.save(rent);

            return rentMapper.toResponse(rent);
        }
        rent.setStatus(Status.CLOSED);
        rent.getVehicle().setIsAvailable(true);
        vehicleRepository.save(rent.getVehicle());
        rent.setEndDate(LocalDateTime.now());
        rentRepository.save(rent);
        return rentMapper.toResponse(rent);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<RentResponse> getRentsByUserId(Long id) {
        userRepository.findUserById(id).orElseThrow(()->new UserNotFoundExeption("Id is not valid"));
        return rentRepository.findRentsByUserId(id)
                .stream().map(rentMapper::toResponse)
                .collect(Collectors.toSet());
    }

}