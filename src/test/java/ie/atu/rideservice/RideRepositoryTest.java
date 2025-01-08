package ie.atu.rideservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RideRepositoryTest {

    @Autowired
    private RideRepository rideRepository;

    @Test
    void testFindByUserId() {
        Ride ride1 = new Ride(null, 100L, "Location A", "Location B", "ONGOING");
        Ride ride2 = new Ride(null, 100L, "Location C", "Location D", "COMPLETED");
        rideRepository.save(ride1);
        rideRepository.save(ride2);

        List<Ride> rides = rideRepository.findByUserId(100L);
        assertEquals(2, rides.size());
    }
}
