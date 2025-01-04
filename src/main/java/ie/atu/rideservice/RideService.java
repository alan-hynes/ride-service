package ie.atu.rideservice;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    public Ride createRide(Ride ride) {
        return rideRepository.save(ride);
    }

    public Ride getRideById(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: " + id));
    }

    public Ride updateRide(Long id, Ride rideDetails) {
        return rideRepository.findById(id).map(ride -> {
            ride.setPickupLocation(rideDetails.getPickupLocation());
            ride.setDestinationLocation(rideDetails.getDestinationLocation());
            ride.setStatus(rideDetails.getStatus());
            ride.setUserId(rideDetails.getUserId());
            return rideRepository.save(ride);
        }).orElseThrow(() -> new ResourceNotFoundException("Ride not found with id: " + id));
    }

    public void deleteRide(Long id) {
        rideRepository.deleteById(id);
    }
}
