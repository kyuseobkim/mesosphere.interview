package io.mesosphere.interview;

import io.mesosphere.interview.Elevator.Direction;

import java.util.ArrayList;
import java.util.List;

public class ElevatorControlSystem {

    List<Elevator> elevators;
    List<Passenger> passengers;

    public ElevatorControlSystem() {
        elevators = new ArrayList<Elevator>();
        passengers = new ArrayList<Passenger>();
    }

    public ElevatorControlSystem(List<Elevator> elevators, List<Passenger> passengers) {
        this.elevators = elevators;
        this.passengers = passengers;
    }

    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
    }

    public void pickUp(Passenger passenger) {
        passengers.add(passenger);
    }

    public void updateDirection(Elevator elevator, Direction direction) {
        elevator.setDirection(direction);
    }

    public void updateElevatorID(Elevator elevator, int id) {
        elevator.setElevatorID(id);
    }

    public void updateCurrentFloor(Elevator elevator, int floor) {
        elevator.setCurrentFloor(floor);
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void step() {

        for (Passenger passenger : passengers) {

            if (passenger.isInsideElevator()) {
                continue;
            }

            schedule(passenger);

        }


        for (Elevator elevator : elevators) {

            update(elevator);

        }
    }

    private void schedule(Passenger passenger) {

        Elevator minLoadElevator = elevators.get(0);
        Elevator closestElevatorMovingToFloor = null;
        Elevator closestStandingElevator = null;


        for (Elevator elevator : elevators) {

            if(elevator.getPassengers().size() + elevator.getScheduledPassengers().size() < minLoadElevator.getPassengers().size() + minLoadElevator.getScheduledPassengers().size()) {
                minLoadElevator = elevator;
            }

            int floorDifference = passenger.getCurrentFloor() - elevator.getCurrentFloor();

            if(elevator.getDirection() == Direction.IDLE) {
                closestStandingElevator = findCloser(closestStandingElevator, elevator, passenger);
            }

            if(elevator.getDirection() == passenger.getDirection()) {

                int multiplier = (passenger.getDirection() == Direction.UP) ? 1 : -1;
                int floorDiff = floorDifference * multiplier;

                if (floorDiff > 0) {
                    closestElevatorMovingToFloor = findCloser(closestElevatorMovingToFloor, elevator, passenger);
                }
            }

            if (elevator.getCurrentFloor() == passenger.getCurrentFloor()) {
                if (elevator.getDirection() == Direction.IDLE) {
                    passenger.getOn(elevator);
                }
                else if (elevator == passenger.getCurrentElevator() && passenger.isScheduled()) {
                    passenger.getOn(elevator);
                }
                else if(elevator.getDirection() == passenger.getDirection()) {
                    passenger.getOn(elevator);
                }
                break;
            }
        }

        if (!passenger.isInsideElevator() && !passenger.isScheduled()) {
            if (closestElevatorMovingToFloor == null && closestStandingElevator == null) {
                minLoadElevator.schedule(passenger);
            } else if (closestElevatorMovingToFloor != null && closestStandingElevator == null) {
                closestElevatorMovingToFloor.schedule(passenger);
            } else if (closestElevatorMovingToFloor == null && closestStandingElevator != null) {
                closestStandingElevator.schedule(passenger);
            } else {
                findCloser(closestStandingElevator, closestElevatorMovingToFloor, passenger).schedule(passenger);
            }
        }

    }

    private void update(Elevator elevator) {

        List<Passenger> passengerInsideElevator = new ArrayList<Passenger>(elevator.getPassengers());

        for (Passenger passenger : passengerInsideElevator) {
            if (passenger.getGoalFloor() == elevator.getCurrentFloor()) {
                elevator.unloadPassenger(passenger);
                // passenger successfully served
                passengers.remove(passenger);
            }
        }

        if (elevator.getPassengers().size() == 0 && elevator.getScheduledPassengers().size() == 0) {
            elevator.setDirection(Direction.IDLE);
        }


        if(elevator.getDirection() == Direction.UP) {
            elevator.incrementFloor(1);
        } else if (elevator.getDirection() == Direction.DOWN) {
            elevator.decrementFloor(1);
        }

    }

    private Elevator findCloser(Elevator min, Elevator arg1, Passenger passenger) {

        if (min == null) {
            return arg1;
        } else {
            int floorDiff0 = Math.abs(min.getCurrentFloor() - passenger.getCurrentFloor());
            int floorDiff1 = Math.abs(arg1.getCurrentFloor() - passenger.getCurrentFloor());
            if (floorDiff0 > floorDiff1) {
                return arg1;
            }
            return min;
        }

    }

}
