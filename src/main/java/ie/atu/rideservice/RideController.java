package ie.atu.rideservice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping
    public ResponseEntity<Ride> createRide(@Valid @RequestBody Ride ride) {
        Ride createdRide = rideService.createRide(ride);
        return ResponseEntity.status(201).body(createdRide);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ride> getRideById(@PathVariable Long id) {
        Ride ride = rideService.getRideById(id);
        return ResponseEntity.ok(ride);
    }

    @GetMapping
    public ResponseEntity<List<Ride>> getAllRides() {
        List<Ride> rides = rideService.getAllRides();
        return ResponseEntity.ok(rides);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ride> updateRide(@PathVariable Long id, @Valid @RequestBody Ride rideDetails) {
        Ride updatedRide = rideService.updateRide(id, rideDetails);
        return ResponseEntity.ok(updatedRide);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable Long id) {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }
}
