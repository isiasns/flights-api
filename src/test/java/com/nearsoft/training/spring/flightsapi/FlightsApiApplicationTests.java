package com.nearsoft.training.spring.flightsapi;

import com.nearsoft.training.spring.flightsapi.seed.DatabaseSeederTest;
import com.nearsoft.training.spring.flightsapi.service.AirlineServiceTest;
import com.nearsoft.training.spring.flightsapi.service.AirportServiceTest;
import com.nearsoft.training.spring.flightsapi.service.ScheduledFlightServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@SpringBootTest
@Suite.SuiteClasses({
        DatabaseSeederTest.class,
        AirlineServiceTest.class,
        AirportServiceTest.class,
        ScheduledFlightServiceTest.class
})
public class FlightsApiApplicationTests {

}
