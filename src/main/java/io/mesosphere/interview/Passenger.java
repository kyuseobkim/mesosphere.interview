package io.mesosphere.interview;

import io.mesosphere.interview.Elevator.Direction;

public class Passenger {

    private int goalFloor;
    private int currentFloor;
    private boolean insideElevator;
    private boolean scheduled;
    private Direction direction;
    private Elevator elevator;

    public Passenger(int currentFloor, int goalFloor) {
        this.goalFloor = goalFloor;
        this.currentFloor = currentFloor;
        direction = (currentFloor < goalFloor) ? Direction.UP : Direction.DOWN;
        this.insideElevator = false;
        this.scheduled = false;
        elevator = null;
    }

    public void getOn(Elevator elevator) {
        if (elevator.getCurrentFloor() == currentFloor) {
            elevator.loadPassenger(this);
            elevator.getScheduledPassengers().remove(this);
            if(elevator.getDirection() == Direction.IDLE) {
                elevator.setDirection(direction);
            }
            this.elevator = elevator;
            insideElevator = true;
        } else {
            elevator.schedule(this);
            scheduled = true;
        }
    }

    public int getGoalFloor() {
        return goalFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Elevator getCurrentElevator() {
        return elevator;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isInsideElevator() {
        return insideElevator;
    }

    public void setInsideElevator(boolean value) {
        insideElevator = value;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public void setSchduled(boolean value) {
        scheduled = value;
    }

    public void setCurrentElevator(Elevator elevator) {
        this.elevator = elevator;
    }

}
