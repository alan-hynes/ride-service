package ie.atu.rideservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RideRepositoryTest {

    @Autowired
    private RideRepository rideRepository;

    @Test
    public void testSaveRide() {
        Ride ride = new Ride(null, 1L, "PickupLocation", "DestinationLocation", "PENDING");
        Ride savedRide = rideRepository.save(ride);

        assertThat(savedRide.getId()).isNotNull();
        assertThat(savedRide.getPickupLocation()).isEqualTo("PickupLocation");
    }

    @Test
    public void testFindRideById() {
        Ride ride = new Ride(null, 1L, "PickupLocation", "DestinationLocation", "PENDING");
        Ride savedRide = rideRepository.save(ride);

        Optional<Ride> foundRide = rideRepository.findById(savedRide.getId());

        assertThat(foundRide).isPresent();
        assertThat(foundRide.get().getPickupLocation()).isEqualTo("PickupLocation");
    }

    @Test
    public void testDeleteRide() {
        Ride ride = new Ride(null, 1L, "PickupLocation", "DestinationLocation", "PENDING");
        Ride savedRide = rideRepository.save(ride);

        rideRepository.deleteById(savedRide.getId());

        Optional<Ride> foundRide = rideRepository.findById(savedRide.getId());
        assertThat(foundRide).isEmpty();
    }
}
