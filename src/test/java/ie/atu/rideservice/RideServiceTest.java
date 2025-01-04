package ie.atu.rideservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RideServiceTest {

    @Mock
    private RideRepository rideRepository;

    @InjectMocks
    private RideService rideService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRide() {
        // Create a new ride
        Ride ride = new Ride();
        ride.setPickupLocation("Location A");
        ride.setDestinationLocation("Location B");
        ride.setUserId(1L);
        ride.setStatus("PENDING");

        // Mock the save method
        when(rideRepository.save(any(Ride.class))).thenReturn(ride);

        // Call the createRide method
        Ride createdRide = rideService.createRide(ride);

        // Verify the ride was saved
        assertThat(createdRide).isNotNull();
        assertThat(createdRide.getPickupLocation()).isEqualTo("Location A");
        assertThat(createdRide.getDestinationLocation()).isEqualTo("Location B");
    }

    @Test
    public void testGetRideById() {
        // Create a new ride
        Ride ride = new Ride();
        ride.setId(1L);
        ride.setPickupLocation("Location A");
        ride.setDestinationLocation("Location B");
        ride.setUserId(1L);

        // Mock the findById method to return the ride wrapped in an Optional
        when(rideRepository.findById(1L)).thenReturn(Optional.of(ride));

        // Call the getRideById method
        Ride foundRide = rideService.getRideById(1L);

        // Verify the ride was found
        assertThat(foundRide).isNotNull();
        assertThat(foundRide.getId()).isEqualTo(1L);
        assertThat(foundRide.getPickupLocation()).isEqualTo("Location A");
        assertThat(foundRide.getDestinationLocation()).isEqualTo("Location B");
    }


    @Test
    public void testGetRideByIdNotFound() {
        // Mock the findById method to return an empty Optional
        when(rideRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the getRideById method and expect a ResourceNotFoundException
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            rideService.getRideById(1L);
        });

        // Verify the exception message
        assertThat(exception.getMessage()).isEqualTo("Ride not found with id: 1");
    }
}
