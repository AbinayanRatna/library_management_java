package librarymanage;

/**
 * Abstract class representing a general library item.
 * It includes common properties like id, title, and availability status, 
 * and provides basic functionality such as borrowing and returning an item.
 * This class is intended to be extended by more specific item types, such as books or DVDs.
 */
public abstract class Item {

    // Protected fields for item id, title, and availability status
    protected int id;             // Unique identifier for the item
    protected String title;       // Title of the item 
    protected boolean available;  // Availability status: true if available, false if borrowed
    protected String type;        //Type of the item

    /**
     * Constructor to initialize an item with its id, title, and availability status.
     * 
     * @param id        Unique identifier for the item.
     * @param title     Title of the item.
     * @param type .    Type of the item (DVD or book)
     * @param available Availability status of the item.
     */
    public Item(int id, String title, String type, boolean available) {
        this.id = id;
        this.title = title;
        this.type=type;
        this.available = available;
    }

    /**
     * Abstract method to display the details of the item.
     * Each subclass should implement this method to show specific details of the item type.
     */
    public abstract void displayDetails();

    /**
     * Method to borrow the item. Throws an exception if the item is not available.
     * 
     * @throws Exception if the item is already borrowed (i.e., not available).
     */
    public void borrow() throws Exception {
        if (!available) {
            throw new Exception("Item not available for borrowing.");
        }
        available = false; // Mark the item as borrowed
        System.out.println(title + " has been borrowed.");
    }

    /**
     * Method to return the item. Marks the item as available.
     */
    public void returnItem() {
        available = true; // Mark the item as returned
        System.out.println(title + " has been returned.");
    }

    /**
     * Method to check if the item is available for borrowing.
     * 
     * @return true if the item is available, false if it is borrowed.
     */
    public boolean isAvailable() {
        return available;
    }

    
}