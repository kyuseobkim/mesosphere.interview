package io.mesosphere.interview;

import io.mesosphere.interview.Elevator.Direction;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ElevatorControlSystemTest {

    Passenger passenger1;
    Passenger passenger2;
    Elevator elevator1;
    Elevator elevator2;
    ElevatorControlSystem elevatorControlSystem;

    @Before
    public void setUp() {
        passenger1 = new Passenger(1, 5);
        passenger2 = new Passenger(4, 1);

        elevator1 = new Elevator(0, 2);
        elevator2 = new Elevator(1, 1);

        elevatorControlSystem = new ElevatorControlSystem();
    }

    @Test
    public void addElevatorTest() {
        elevatorControlSystem.addElevator(elevator1);
        elevatorControlSystem.addElevator(elevator2);
        Assert.assertEquals(2, elevatorControlSystem.getElevators().size());
    }

    @Test
    public void pickUpTest() {
        elevatorControlSystem.pickUp(passenger1);
        elevatorControlSystem.pickUp(passenger2);
        Assert.assertEquals(2, elevatorControlSystem.getPassengers().size());
    }

    @Test
    public void stepTest() {

        elevatorControlSystem.addElevator(elevator1); //idle at 2
        elevatorControlSystem.addElevator(elevator2); //idle at 1

        elevatorControlSystem.pickUp(passenger1); // at floor 1 want to go to floor 5
        elevatorControlSystem.pickUp(passenger2); // at floor 4 want to go to floor 1

        elevatorControlSystem.step();

        Assert.assertTrue(passenger1.isInsideElevator());
        Assert.assertEquals(Direction.UP, elevator2.getDirection());
        Assert.assertEquals(2, elevator2.getCurrentFloor());
        Assert.assertEquals(0, elevator2.getScheduledPassengers().size());
        Assert.assertEquals(passenger1, elevator2.getPassengers().get(0));

        Assert.assertFalse(passenger2.isInsideElevator());
        Assert.assertEquals(Direction.UP, elevator2.getDirection());
        Assert.assertTrue(passenger2.isScheduled());
        Assert.assertEquals(3, elevator1.getCurrentFloor());
        Assert.assertEquals(1, elevator1.getScheduledPassengers().size());

        elevatorControlSystem.step();
        Assert.assertEquals(4, elevator1.getCurrentFloor());
        Assert.assertEquals(3, elevator2.getCurrentFloor());

        elevatorControlSystem.step();
        Assert.assertEquals(3, elevator1.getCurrentFloor());
        Assert.assertEquals(1, elevator1.getPassengers().size());
        Assert.assertEquals(0, elevator1.getScheduledPassengers().size());
        Assert.assertEquals(passenger2, elevator1.getPassengers().get(0));
        Assert.assertEquals(4, elevator2.getCurrentFloor());
        Assert.assertEquals(1, elevator2.getPassengers().size());
        Assert.assertEquals(2, elevatorControlSystem.getPassengers().size());

        elevatorControlSystem.step();
        Assert.assertEquals(5, elevator2.getCurrentFloor());
        Assert.assertEquals(1, elevator1.getPassengers().size());
        Assert.assertEquals(1, elevator2.getPassengers().size());
        Assert.assertEquals(2, elevatorControlSystem.getPassengers().size());
        Assert.assertEquals(0, elevator1.getScheduledPassengers().size());
        Assert.assertEquals(0, elevator2.getScheduledPassengers().size());

        elevatorControlSystem.step();
        Assert.assertEquals(5, elevator2.getCurrentFloor());
        Assert.assertEquals(1, elevatorControlSystem.getPassengers().size());
        Assert.assertEquals(Direction.IDLE, elevator2.getDirection());
        Assert.assertEquals(1, elevator1.getCurrentFloor());

        elevatorControlSystem.step();
        Assert.assertEquals(1, elevator1.getCurrentFloor());
        Assert.assertEquals(Direction.IDLE, elevator1.getDirection());
        Assert.assertEquals(0, elevatorControlSystem.getPassengers().size());


    }

}
