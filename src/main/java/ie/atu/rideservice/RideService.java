package ie.atu.rideservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;

    @Transactional
    public Ride createRide(Ride ride) {
        return rideRepository.save(ride);
    }

    public Ride getRideById(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with ID: " + id));
    }

    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    public List<Ride> getRidesByUserId(Long userId) {
        return rideRepository.findByUserId(userId);
    }

    @Transactional
    public Ride updateRide(Long id, Ride rideDetails) {
        Ride ride = getRideById(id);
        ride.setPickupLocation(rideDetails.getPickupLocation());
        ride.setDestinationLocation(rideDetails.getDestinationLocation());
        ride.setStatus(rideDetails.getStatus());
        return rideRepository.save(ride);
    }

    @Transactional
    public void deleteRide(Long id) {
        Ride ride = getRideById(id);
        rideRepository.delete(ride);
    }

    @RabbitListener(queues = "ride_service_queue")
    public void handleUserMessage(String message) {
        System.out.println("Received message: " + message);

        String[] messageParts = message.split(",");

        String action = messageParts[0].split(":")[1].trim().replaceAll("\"", "");
        Long userId = Long.parseLong(messageParts[1].split(":")[1].trim());
        String pickupLocation = messageParts[2].split(":")[1].trim().replaceAll("\"", "");
        String destinationLocation = messageParts[3].split(":")[1].trim().replaceAll("\"", "");
        String status = messageParts[4].split(":")[1].trim().replaceAll("\"", "");

        if ("create".equals(action)) {
            Ride newRide = new Ride();
            newRide.setUserId(userId);
            newRide.setPickupLocation(pickupLocation);
            newRide.setDestinationLocation(destinationLocation);
            newRide.setStatus(status);

            createRide(newRide);
            System.out.println("Created a new ride: " + newRide);
        } else if ("update".equals(action)) {
            Long rideId = Long.parseLong(messageParts[5].split(":")[1].trim());

            Ride rideToUpdate = getRideById(rideId);
            rideToUpdate.setPickupLocation(pickupLocation);
            rideToUpdate.setDestinationLocation(destinationLocation);
            rideToUpdate.setStatus(status);

            updateRide(rideId, rideToUpdate);
            System.out.println("Updated ride: " + rideToUpdate);
        }
    }
}
