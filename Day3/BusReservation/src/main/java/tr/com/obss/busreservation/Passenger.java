package tr.com.obss.busreservation;

public class Passenger {
    private String name;
    private Destination destination;

    public Passenger(String name) {
        this.name = name;
    }

    public Passenger(String name, Destination destination) {
        this.name = name;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
