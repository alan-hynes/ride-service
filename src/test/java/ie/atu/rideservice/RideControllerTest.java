package ie.atu.rideservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RideControllerTest {

    @Mock
    private RideService rideService;

    @InjectMocks
    private RideController rideController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRides() {
        // Mocking the RideService
        List<Ride> mockRides = Arrays.asList(
                new Ride(1L, 1L, "Pickup1", "Destination1", "PENDING"),
                new Ride(2L, 2L, "Pickup2", "Destination2", "COMPLETED")
        );
        when(rideService.getAllRides()).thenReturn(mockRides);

        // Calling the method
        ResponseEntity<List<Ride>> response = rideController.getAllRides();

        // Verifications
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        assertThat(response.getBody().get(0).getStatus()).isEqualTo("PENDING");
    }

    @Test
    public void testGetRideById() {
        // Mocking the RideService
        Ride mockRide = new Ride(1L, 1L, "Pickup1", "Destination1", "PENDING");
        when(rideService.getRideById(1L)).thenReturn(mockRide);

        // Calling the method
        ResponseEntity<Ride> response = rideController.getRideById(1L);

        // Verifications
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPickupLocation()).isEqualTo("Pickup1");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }


    @Test
    public void testCreateRide() {
        // Mocking the RideService
        Ride mockRide = new Ride(1L, 1L, "Pickup1", "Destination1", "PENDING");
        when(rideService.createRide(any(Ride.class))).thenReturn(mockRide);

        // Calling the method
        ResponseEntity<Ride> response = rideController.createRide(mockRide);

        // Verifications
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPickupLocation()).isEqualTo("Pickup1");
    }

    @Test
    public void testUpdateRide() {
        // Mocking the RideService
        Ride mockRide = new Ride(1L, 1L, "UpdatedPickup", "UpdatedDestination", "COMPLETED");
        when(rideService.updateRide(eq(1L), any(Ride.class))).thenReturn(mockRide);

        // Calling the method
        ResponseEntity<Ride> response = rideController.updateRide(1L, mockRide);

        // Verifications
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPickupLocation()).isEqualTo("UpdatedPickup");
    }

    @Test
    public void testDeleteRide() {
        // Mocking the RideService
        doNothing().when(rideService).deleteRide(1L);

        // Calling the method
        ResponseEntity<Void> response = rideController.deleteRide(1L);

        // Verifications
        verify(rideService, times(1)).deleteRide(1L);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
