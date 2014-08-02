package io.mesosphere.interview;

import java.util.ArrayList;
import java.util.List;

public class Elevator {

    public static enum Direction {UP, IDLE, DOWN};

    private int elevatorID;
    private Direction direction;
    private List<Passenger> passengersInside;
    private List<Passenger> passengersToPickUp;
    private int currentFloor;

    public Elevator(int elevatorID, int currentFloor) {
        this.elevatorID = elevatorID;
        this.currentFloor = currentFloor;
        direction = Direction.IDLE;
        passengersInside = new ArrayList<Passenger>();
        passengersToPickUp = new ArrayList<Passenger>();
    }

    public void loadPassenger(Passenger passenger) {
        if (passenger.getCurrentFloor() != currentFloor) {
            schedule(passenger);
        } else {
            passengersInside.add(passenger);
            passenger.setInsideElevator(true);
            passenger.setCurrentElevator(this);
            this.direction = passenger.getDirection();
        }
    }

    public void unloadPassenger(Passenger passenger) {
        if (passenger.getGoalFloor() == currentFloor) {
            passengersInside.remove(passenger);
            passenger.setInsideElevator(false);
            passenger.setCurrentElevator(null);
        }
    }

    public void schedule(Passenger passenger) {
        passengersToPickUp.add(passenger);
        passenger.setSchduled(true);
        passenger.setCurrentElevator(this);
        if (direction == Direction.IDLE) {
            direction = (this.currentFloor > passenger.getCurrentFloor()) ? Direction.DOWN : Direction.UP;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void incrementFloor(int step) {
        currentFloor += step;
    }

    public void decrementFloor(int step) {
        currentFloor -= step;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getElevatorID() {
        return elevatorID;
    }

    public void setCurrentFloor(int floor) {
        currentFloor = floor;
    }

    public void setElevatorID(int id) {
        elevatorID = id;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public List<Passenger> getPassengers() {
        return passengersInside;
    }

    public List<Passenger> getScheduledPassengers() {
        return passengersToPickUp;
    }

}
