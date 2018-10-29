package com.nearsoft.training.spring.flightsapi;

import com.nearsoft.training.spring.flightsapi.controller.ScheduledFlightController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightsApiApplicationTest {
    @Autowired
    private ScheduledFlightController scheduledFlightController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(scheduledFlightController).isNotNull();
    }
}
