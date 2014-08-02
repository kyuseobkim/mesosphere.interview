package io.mesosphere.interview;

import io.mesosphere.interview.Elevator.Direction;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ElevatorTest {

    Passenger passenger1;
    Passenger passenger2;
    Elevator elevator1;

    @Before
    public void setUp() {

        passenger1 = new Passenger(1, 2);
        passenger2 = new Passenger(3, 1);
        elevator1 = new Elevator(0, 0);

    }

    @Test
    public void gettetMethodTest() {
        Assert.assertEquals(0, elevator1.getElevatorID());
        Assert.assertEquals(0, elevator1.getCurrentFloor());
        Assert.assertEquals(0, elevator1.getPassengers().size());
        Assert.assertEquals(Direction.IDLE, elevator1.getDirection());
        elevator1.loadPassenger(passenger1);
        Assert.assertEquals(Direction.UP, elevator1.getDirection());
        elevator1.loadPassenger(passenger2);
        Assert.assertEquals(Direction.UP, elevator1.getDirection());
        Assert.assertEquals(0, elevator1.getPassengers().size());
        Assert.assertEquals(2, elevator1.getScheduledPassengers().size());
    }

    @Test
    public void decrementIncrementFloorTest() {
        elevator1.decrementFloor(2);
        Assert.assertEquals(-2, elevator1.getCurrentFloor());
        elevator1.incrementFloor(2);
        Assert.assertEquals(0, elevator1.getCurrentFloor());
    }

}
