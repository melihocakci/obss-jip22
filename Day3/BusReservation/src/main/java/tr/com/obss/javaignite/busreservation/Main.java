package tr.com.obss.javaignite.busreservation;

public class Main {
    public static void main(String[] args) {
        Bus bus = new Bus(1);
        bus.setDestination(Destination.ISTANBUL);

        Passenger passenger1 = new Passenger("Melih");
        Passenger passenger2 = new Passenger("Jack");
        Passenger passenger3 = new Passenger("NiKo");

        passenger1.setDestination(Destination.ISTANBUL);
        passenger2.setDestination(Destination.ANKARA);
        passenger3.setDestination(Destination.ISTANBUL);

        bus.insertPassenger(passenger1);
        bus.insertPassenger(passenger2);
        bus.insertPassenger(passenger3);
    }
}
