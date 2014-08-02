mesosphere.interview
====================

**Elevator Control System Interview**

This solution contains three classes representing elevator control system, elevator, and passenger.

The elevators are characterized by the elevator ID and the current floor where it is located.

The passengers are characterized by the current floor and the goal floor.

Querying the state of the elevators can be done by using `getElevator()` function in ElevatorControlSystem class.

Receiving an update about the status of an elevator can be done by using the following functions:

```java
updateDirection(Elevator elevator, Direction direction)
updateElevatorID(Elevator elevator, int id)
updateCurrentFloor(Elevator elevator, int floor)
```

which in turn calls the corresponding setter methods in Elevator class.

Similarly, a pick up request can be sent by calling `pickUp(Passenger passenger)` function and time-stepping the simulation is executed by calling `step()` function.

**Scheduling Algorithm**

My scheduling algorithm can be summarized as follows:

Given a passenger, I assign an elevator to pick him up in the following order:
<ol>
<li>Pick an idle elevator located on the current floor of the passenger if such an elevator exists.</li>
<li>Pick an elevator that is closest to the passenger and is either idle or moving in the same direction as the passenger's intended direction.</li>
<li>Pick the elevator with the lowest number of passengers inside the elevator plus passengers scheduled for the elevator.</li>
</ol>
In the second case, if there is a tie, I pick a moving elevator. However, for a purpose of load balancing, we may choose to assign an idle elevator.

This algorithm is better than FCFS because it utilizes not only temporal proximity but also spatial proximity because we consider the distance between the  elevator and the passenger. Also, it's more parallel than FCFS.

We assume that everything happens synchronously. For example, a pick up request cannot be sent while step is executed. Also, we assume that the elevator has an inifite capacity. This can be easily improved by adding a capacity checking method before letting a passenger into an elevator.

**Build Instruction**

This is a maven project so you can build the project by typing `mvn clean package` in the top project directory where pom.xml is located. A JAR file will be created under target directory. Other maven common tasks can be performed as explained in http://maven.apache.org/guides/getting-started/
