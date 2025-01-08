package ie.atu.rideservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RideServiceTest {

    @InjectMocks
    private RideService rideService;

    @Mock
    private RideRepository rideRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRide() {
        Ride ride = new Ride(1L, 100L, "Location A", "Location B", "ONGOING");
        when(rideRepository.save(any(Ride.class))).thenReturn(ride);

        Ride createdRide = rideService.createRide(ride);

        assertNotNull(createdRide);
        assertEquals("Location A", createdRide.getPickupLocation());
        assertEquals("Location B", createdRide.getDestinationLocation());
        assertEquals("ONGOING", createdRide.getStatus());
        verify(rideRepository, times(1)).save(any(Ride.class));
    }

    @Test
    void testGetRideById() {
        Ride ride = new Ride(1L, 100L, "Location A", "Location B", "ONGOING");
        when(rideRepository.findById(1L)).thenReturn(Optional.of(ride));

        Ride fetchedRide = rideService.getRideById(1L);

        assertNotNull(fetchedRide);
        assertEquals("Location A", fetchedRide.getPickupLocation());
        assertEquals("Location B", fetchedRide.getDestinationLocation());
        assertEquals("ONGOING", fetchedRide.getStatus());
        verify(rideRepository, times(1)).findById(1L);
    }

    @Test
    void testGetRideById_NotFound() {
        when(rideRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> rideService.getRideById(1L));
        assertEquals("Ride not found with ID: 1", exception.getMessage());
        verify(rideRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllRides() {
        List<Ride> rides = Arrays.asList(
                new Ride(1L, 100L, "Location A", "Location B", "ONGOING"),
                new Ride(2L, 101L, "Location C", "Location D", "COMPLETED")
        );
        when(rideRepository.findAll()).thenReturn(rides);

        List<Ride> fetchedRides = rideService.getAllRides();

        assertNotNull(fetchedRides);
        assertEquals(2, fetchedRides.size());
        verify(rideRepository, times(1)).findAll();
    }

    @Test
    void testUpdateRide() {
        Ride existingRide = new Ride(1L, 100L, "Location A", "Location B", "ONGOING");
        Ride updatedDetails = new Ride(1L, 100L, "Location X", "Location Y", "COMPLETED");

        when(rideRepository.findById(1L)).thenReturn(Optional.of(existingRide));
        when(rideRepository.save(existingRide)).thenReturn(existingRide);

        Ride updatedRide = rideService.updateRide(1L, updatedDetails);

        assertNotNull(updatedRide);
        assertEquals("Location X", updatedRide.getPickupLocation());
        assertEquals("Location Y", updatedRide.getDestinationLocation());
        assertEquals("COMPLETED", updatedRide.getStatus());
        verify(rideRepository, times(1)).findById(1L);
        verify(rideRepository, times(1)).save(existingRide);
    }

    @Test
    void testDeleteRide() {
        Ride ride = new Ride(1L, 100L, "Location A", "Location B", "ONGOING");
        when(rideRepository.findById(1L)).thenReturn(Optional.of(ride));

        rideService.deleteRide(1L);

        verify(rideRepository, times(1)).findById(1L);
        verify(rideRepository, times(1)).delete(ride);
    }

    @Test
    void testDeleteRide_NotFound() {
        when(rideRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> rideService.deleteRide(1L));
        assertEquals("Ride not found with ID: 1", exception.getMessage());
        verify(rideRepository, times(1)).findById(1L);
    }
}
