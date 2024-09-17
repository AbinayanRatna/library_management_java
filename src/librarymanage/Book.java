package librarymanage;

/**
 * Represents a book in the library system.
 * Inherits from the abstract Item class and adds book-specific properties such as author, genre, and ISBN.
 */
public class Book extends Item {
    private String author; // Author of the book
    private String genre;  // Genre of the book
    private String isbn;   // ISBN number of the book

    /**
     * Constructor to initialize the Book with specific attributes.
     * 
     * @param id        Unique identifier for the book.
     * @param title     Title of the book.
     * @param type      The type of the item (Book).
     * @param available Availability status of the book.
     * @param author    Name of the author.
     * @param genre     Genre of the book.
     * @param isbn      ISBN number of the book.
     */
    public Book(int id, String title, String type, boolean available, String author, String genre, String isbn) {
        super(id, title, type, available); // Call the superclass constructor to initialize common attributes
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
    }

    /**
     * Displays the details of the book, including ID, title, author, genre, ISBN, and availability status.
     */
    @Override
    public void displayDetails() {
        System.out.println("Book ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Genre: " + genre);
        System.out.println("ISBN: " + isbn);
        System.out.println("Available: " + available);
        System.out.println("");
    }
}