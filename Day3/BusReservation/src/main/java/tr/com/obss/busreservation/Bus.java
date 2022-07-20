package tr.com.obss.busreservation;

public class Bus {
    private Passenger[] passengers;
    private Destination destination;

    public Bus(int size) {
        passengers = new Passenger[size];
    }

    public void insertPassenger(Passenger passenger) {
        if (passenger.getDestination() != destination) {
            System.out.println("Destinations do not match");
            return;
        }

        for(int i = 0; i < passengers.length; i++) {
            if(passengers[i] == null) {
                passengers[i] = passenger;
                System.out.println("Passenger inserted");
                return;
            }
        }

        System.out.println("Bus is full");
    }

    public Passenger[] getPassengers() {
        return passengers;
    }

    public void setPassengers(Passenger[] passengers) {
        this.passengers = passengers;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
