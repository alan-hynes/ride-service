package ie.atu.rideservice;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RideController.class)
class RideControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RideService rideService;

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testGetAllRides() throws Exception {
        when(rideService.getAllRides()).thenReturn(Arrays.asList(
                new Ride(1L, 100L, "Location A", "Location B", "ONGOING"),
                new Ride(2L, 101L, "Location C", "Location D", "COMPLETED")
        ));

        mockMvc.perform(get("/api/rides"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pickupLocation").value("Location A"))
                .andExpect(jsonPath("$[1].status").value("COMPLETED"));
        verify(rideService, times(1)).getAllRides();
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testCreateRide() throws Exception {
        Ride mockRide = new Ride(1L, 100L, "Location A", "Location B", "ONGOING");
        when(rideService.createRide(Mockito.any(Ride.class))).thenReturn(mockRide);

        mockMvc.perform(post("/api/rides")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()) // Add CSRF token
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":100,\"pickupLocation\":\"Location A\",\"destinationLocation\":\"Location B\",\"status\":\"ONGOING\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pickupLocation").value("Location A"))
                .andExpect(jsonPath("$.status").value("ONGOING"));
        verify(rideService, times(1)).createRide(Mockito.any(Ride.class));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testGetRideById() throws Exception {
        Ride mockRide = new Ride(1L, 100L, "Location A", "Location B", "ONGOING");
        when(rideService.getRideById(1L)).thenReturn(mockRide);

        mockMvc.perform(get("/api/rides/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pickupLocation").value("Location A"))
                .andExpect(jsonPath("$.status").value("ONGOING"));
        verify(rideService, times(1)).getRideById(1L);
    }
}
