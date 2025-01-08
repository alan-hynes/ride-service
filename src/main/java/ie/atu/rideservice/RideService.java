package ie.atu.rideservice;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
