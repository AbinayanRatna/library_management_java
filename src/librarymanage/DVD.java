package librarymanage;

/**
 * Represents a DVD in the library system.
 * Inherits from the abstract Item class and adds DVD-specific properties such as director and duration.
 */
public class DVD extends Item {
    private String director; // Director of the DVD
    private int duration;    // Duration of the DVD in minutes

    /**
     * Constructor to initialize the DVD with specific attributes.
     * 
     * @param id        Unique identifier for the DVD.
     * @param title     Title of the DVD.
     * @param type      The type of the item (DVD).
     * @param available Availability status of the DVD.
     * @param director  Name of the director.
     * @param duration  Duration of the DVD in minutes.
     */
    public DVD(int id, String title, String type, boolean available, String director, int duration) {
        super(id, title, type, available); // Call the superclass constructor to initialize common attributes
        this.director = director;
        this.duration = duration;
    }

    /**
     * Displays the details of the DVD, including ID, title, director, duration, and availability status.
     */
    @Override
    public void displayDetails() {
        System.out.println("DVD ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Director: " + director);
        System.out.println("Duration: " + duration + " minutes");
        System.out.println("Available: " + available);
        System.out.println("");
    }
}