mesosphere.interview
====================

**Elevator Control System Interview**

This solution contains three classes representing elevator control system, elevator, and passenger.

The elevators are characterized by the elevator ID and the current floor where it is located.

The passengers are characterized by the current floor and the goal floor.

Querying the state of the elevators can be done by using `java getElevator()` function in ElevatorControlSystem class.

Receiving an update about the status of an elevator can be done by using the following functions:

```java
updateDirection(Elevator elevator, Direction direction)
updateElevatorID(Elevator elevator, int id)
updateCurrentFloor(Elevator elevator, int floor)
```

which in turn calls the corresponding setter methods in Elevator class.

Similarly, a pick up request can be sent by calling `java pickUp(Passenger passenger)` function and time-stepping the simulation is executed by calling `java step()` function.

**Scheduling Algorithm**
<ol>
</ol>
