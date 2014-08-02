package io.mesosphere.interview;

import io.mesosphere.interview.Elevator.Direction;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class PassengerTest {

    Passenger passenger1;
    Elevator elevator1;
    Elevator elevator2;

    @Before
    public void setUp() {
        passenger1 = new Passenger(0, 1);
        elevator1 = new Elevator(0, 2);
        elevator2 = new Elevator(1, 0);
    }

    @Test
    public void getterMethodTest() {
        Assert.assertEquals(null, passenger1.getCurrentElevator());
        Assert.assertFalse(passenger1.isInsideElevator());
        Assert.assertEquals(0, passenger1.getCurrentFloor());
        Assert.assertEquals(1, passenger1.getGoalFloor());
        Assert.assertEquals(Direction.UP, passenger1.getDirection());
    }

    @Test
    public void getOnTestDiffFloor() {

        Assert.assertEquals(Direction.IDLE, elevator1.getDirection());

        passenger1.getOn(elevator1);

        Assert.assertEquals(elevator1, passenger1.getCurrentElevator());
        Assert.assertFalse(passenger1.isInsideElevator());
        Assert.assertEquals(0, elevator1.getPassengers().size());
        Assert.assertEquals(1, elevator1.getScheduledPassengers().size());
        Assert.assertEquals(Direction.DOWN, elevator1.getDirection());

    }

    @Test
    public void getOnTestSameFloor() {

        Assert.assertEquals(Direction.IDLE, elevator2.getDirection());

        passenger1.getOn(elevator2);

        Assert.assertEquals(elevator2, passenger1.getCurrentElevator());
        Assert.assertTrue(passenger1.isInsideElevator());
        Assert.assertEquals(1, elevator2.getPassengers().size());
        Assert.assertEquals(0, elevator2.getScheduledPassengers().size());
        Assert.assertEquals(Direction.UP, elevator2.getDirection());

    }

}
