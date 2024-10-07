package com.alejo.rentadevehiculos.infrastructure.services;

import com.alejo.rentadevehiculos.api.models.request.RentRequest;
import com.alejo.rentadevehiculos.api.models.response.SuccesResponse;
import com.alejo.rentadevehiculos.domain.entities.PaymentMethodEntity;
import com.alejo.rentadevehiculos.domain.entities.RentEntity;
import com.alejo.rentadevehiculos.domain.entities.UserEntity;
import com.alejo.rentadevehiculos.domain.entities.VehicleEntity;
import com.alejo.rentadevehiculos.domain.repositories.PaymentMethodRepository;
import com.alejo.rentadevehiculos.domain.repositories.RentRepository;
import com.alejo.rentadevehiculos.domain.repositories.UserRepository;
import com.alejo.rentadevehiculos.domain.repositories.VehicleRepository;
import com.alejo.rentadevehiculos.infrastructure.abstractServices.IRentService;
import com.alejo.rentadevehiculos.util.Method;
import com.alejo.rentadevehiculos.util.Status;
import com.alejo.rentadevehiculos.util.encrypt.EncryptionUtil;
import com.alejo.rentadevehiculos.util.exceptions.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
public class RentService implements IRentService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final RentRepository rentRepository;

    @Value("${encryption.secret.key}")
    private String secretKeyString;

    private SecretKey secretKey;

    // Convierte el String a SecretKey después de inyectar la clave
    @PostConstruct
    public void init() {
        this.secretKey = EncryptionUtil.getSecretKeyFromBase64(secretKeyString);
    }

    @Override
    public ResponseEntity<SuccesResponse> createRent(RentRequest request) {

        UserEntity user = userRepository.findUserById(request.getUserId())
                .orElseThrow(()-> new UserNotFoundExeption("The id is not valid"));
        PaymentMethodEntity paymentMethod = paymentMethodRepository.
                findPaymentByPaymentId(request.getPaymentMethodId())
                .orElseThrow(()->new PaymentNotFoundException("the Id on Method is not valid"));
        VehicleEntity vehicle = vehicleRepository.getVehicle(request.getVehicleId())
                .orElseThrow(()-> new VehicleNotFoundException("The license plate on Vehicle is not valid"));

        if (!user.getId().equals(paymentMethod.getUser().getId())){
            throw new BadRequestRentalVehiclesException("The payment method does not belong to the user.");
        }else if(!vehicle.getIsAvailable()){
            throw new BadRequestRentalVehiclesException("Vehicle is not available.");
        }

        vehicle.setIsAvailable(false);
        vehicleRepository.save(vehicle);

        RentEntity rentEntity = new RentEntity();
        rentEntity.setStatus(Status.OPEN);

        rentEntity.setVehicle(vehicle);
        rentEntity.setPaymentMethod(paymentMethod);
        rentEntity.setStartDate(LocalDateTime.now());
        rentEntity.setValue(BigDecimal.valueOf(0));
        rentRepository.save(rentEntity);

        return ResponseEntity.ok(new SuccesResponse("Rent created in the correct manner"));
    }

    @Override
    public Set<RentEntity> getAllRents() {
        return new HashSet<>(rentRepository.findAll());
    }

    @Override
    public RentEntity getRentById(Long id) {
        return rentRepository.findById(id).orElseThrow(()->new RentNotFoundException("The id is no valid"));
    }

    @Override
    public ResponseEntity<SuccesResponse> updateStatus(Long id) throws Exception {
        RentEntity rent = rentRepository.findById(id).
                orElseThrow(()-> new RentNotFoundException("RenId is not valid"));
        rent.setValue(calcualatePrice(rent.getStartDate(), LocalDateTime.now(), rent.getVehicle().getRate()));
        if(simulatePayment(rent)){
            rent.setStatus(Status.UNDER_REVIEW);
            rentRepository.save(rent);
            return ResponseEntity.accepted().body(new SuccesResponse("The rent is under review (UNDER_REVIEW). No further actions can be performed at this time."));
        }
        rent.setStatus(Status.CLOSED);
        rent.getVehicle().setIsAvailable(true);
        vehicleRepository.save(rent.getVehicle());
        rent.setEndDate(LocalDateTime.now());
        rentRepository.save(rent);
        return ResponseEntity.ok(new SuccesResponse("Rent closed in the correct manner"));
    }

    @Override
    public Set<RentEntity> getRentsByUserId(Long id) {
        userRepository.findUserById(id).orElseThrow(()->new UserNotFoundExeption("Id is not valid"));
        return rentRepository.findRentsByUserId(id);
    }

    public BigDecimal calcualatePrice(LocalDateTime start, LocalDateTime end, BigDecimal rate){
        if(!start.isBefore(end)){
            throw new BadRequestRentalVehiclesException("Verify the dates");
        }
        Long hours = Duration.between(start, end).toHours();

        if(hours == 0){
            hours=1L;
        }
        return rate.multiply(BigDecimal.valueOf(hours));

    }

    public boolean simulatePayment(RentEntity rent) throws Exception {

        if(rent.getStatus().equals(Status.CLOSED)){
           throw new BadRequestRentalVehiclesException("This rent cannot be used");
        }
        if(rent.getPaymentMethod().getMethod().equals(Method.CASH)){
            return false;
        }
        PaymentMethodEntity paymentMethod = rent.getPaymentMethod();
        String decrypt = EncryptionUtil.decrypt(paymentMethod.getCardNumber(), secretKey);
        return decrypt.startsWith("1");

    }
}
