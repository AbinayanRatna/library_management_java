package librarymanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The main class that handles library management operations.
 * Provides methods to add, search, borrow, and return items in a library system.
 */
public class LibraryManage {
    private static final Scanner scanner = new Scanner(System.in); // Shared scanner for input

    /**
     * The main method that serves as the entry point of the library management system.
     * Displays a menu to allow users to perform actions such as adding items, searching,
     * borrowing, and returning items.
     */
    public static void main(String[] args) {
        while (true) {
            // Display library management options
            System.out.println("Library Management System");
            System.out.println("1. Add Item");
            System.out.println("2. Search Item");
            System.out.println("3. Borrow Item");
            System.out.println("4. Return Item");
            System.out.println("5. Exit");
            
            // Get a valid integer input for user choice
            int choice = getValidInteger("Choose an option: ");

            try {
                switch (choice) {
                    case 1:
                        addItem(); // Add a new item to the library
                        break;
                    case 2:
                        searchItem(); // Search for an item by title
                        break;
                    case 3:
                        borrowItem(); // Borrow an available item
                        break;
                    case 4:
                        returnItem(); // Return a borrowed item
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return; // Exit the system
                    default:
                        System.out.println("Invalid option. Select from the given options.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage()); // Display any exceptions
            }
        }
    }

    /**
     * Method to get a valid integer input from the user.
     * If the input is not an integer, it will keep asking until a valid integer is provided.
     *
     * @param message The message to display to prompt the user for input.
     * @return The valid integer input.
     */
    public static int getValidInteger(String message) {
        while (true) {
            System.out.print(message);
            try {
                int choice = scanner.nextInt(); // Read integer input
                scanner.nextLine(); // Consume newline character
                return choice; // Return the valid integer
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Answer should be in integer format.");
                scanner.next(); // Clear the invalid input from the scanner
            }
        }
    }

    /**
     * Method to add a new item (either a book or DVD) to the library.
     * Handles input validation and inserts the item into the appropriate table in the database.
     * 
     * @throws InvalidItemTypeException Thrown if the item type is neither "book" nor "DVD".
     * @throws SQLException Thrown if a database error occurs during the insertion.
     */
    public static void addItem() throws InvalidItemTypeException, SQLException {
        String sql = "INSERT INTO items (title, type, available) VALUES (?, ?, ?)";
        String sql2 = "";
        Scanner scanner = new Scanner(System.in);
        
        // Get item details from the user
        System.out.print("Enter item title: ");
        String title = scanner.nextLine();

        System.out.print("Enter item type (book/dvd): ");
        String type = scanner.nextLine();
        
        // Validate item type (either book or dvd)
        if (!type.equalsIgnoreCase("book") && !type.equalsIgnoreCase("dvd")) {
            throw new InvalidItemTypeException("Invalid item type: " + type);
        }
        
        // Variables specific to book or DVD
        String director = "";
        String authorName = "";
        String isbn = "";
        String genre = "";
        int duration = 0;
        
        // If item is a DVD, collect additional DVD-specific details
        if ("dvd".equals(type.toLowerCase())) {
           sql2 = "INSERT INTO dvds (item_id, director, duration) VALUES (?,?, ?)";
           
           System.out.print("Enter DVD director name: ");
           director = scanner.nextLine();
           
           duration = getValidInteger("Enter valid DVD duration: ");
        
        // If item is a book, collect additional book-specific details
        } else {
           sql2 = "INSERT INTO books (item_id, author, genre, isbn) VALUES (?,?, ?, ?)";
            
           System.out.print("Enter book author name: ");
           authorName = scanner.nextLine();
           
           System.out.print("Enter book ISBN: ");
           isbn = scanner.nextLine();
           
           System.out.print("Enter book genre: ");
           genre = scanner.nextLine();
        }
        
        boolean available = true; // New items are available by default
        PreparedStatement pstmtType = null;
        try (
             Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
            // Insert the item into the items table
            pstmt.setString(1, title.toLowerCase());
            pstmt.setString(2, type.toLowerCase());
            pstmt.setBoolean(3, available);
    
            int rowsInserted = pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys(); // Retrieve the generated item ID
            pstmtType = conn.prepareStatement(sql2);
            
            if (generatedKeys.next()) {
                int itemId = generatedKeys.getInt(1);
                
                // Insert additional details into the corresponding table (books or dvds)
                if ("dvd".equals(type.toLowerCase())) {
                  DVD dvd=new DVD(itemId,title,"dvd",true,director,duration);
                  pstmtType.setLong(1, itemId);
                  pstmtType.setString(2,director);
                  pstmtType.setInt(3, duration);
                  System.out.println("");
                  dvd.displayDetails();
                } else {
                  pstmtType.setLong(1, itemId);
                  pstmtType.setString(2, authorName);
                  pstmtType.setString(3, genre);
                  pstmtType.setString(4, isbn);
                  
                  Book book=new Book(itemId,title,"book",true,authorName,genre,isbn);
                  System.out.println("");
                  book.displayDetails();
                }
                
                pstmtType.executeUpdate();
                
            } else {
                throw new SQLException("Creating item failed, no ID obtained.");
            }
            
            if (rowsInserted > 0) {
                System.out.println("Item added successfully!");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        } 
    }

    /**
     * Method to search for an item in the library by title.
     * If the item is found, it displays the details; otherwise, throws an exception.
     * 
     * @throws ItemUnavailableException Thrown if the item is not found in the library.
     * @throws SQLException Thrown if a database error occurs during the search.
     */
    public static void searchItem() throws ItemUnavailableException, SQLException {
        String sql = "SELECT * FROM items WHERE title = ?";
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter item title: ");
        String title = (scanner.nextLine()).toLowerCase();
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
    
            if (rs.next()) {
                // Display item details if found
                System.out.println("Item found: ");
                System.out.println("    ID: " + rs.getInt("item_id"));
                System.out.println("    Title: " + rs.getString("title"));
                System.out.println("    Type: " + rs.getString("type"));
                String availability = rs.getBoolean("available") ? "Available" : "Not available";
                System.out.println("    Availability: " + availability);
            } else {
                throw new ItemUnavailableException(title + " is not available in the library.");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
    
    /**
     * Method to borrow an item from the library.
     * It checks the availability of the item and updates the borrowing history if successful.
     * 
     * @throws ItemUnavailableException Thrown if the item is already borrowed.
     * @throws SQLException Thrown if a database error occurs during the borrow operation.
     */
    public static void borrowItem() throws ItemUnavailableException, SQLException {
        String sqlCheck = "SELECT available FROM items WHERE item_id = ?";
        String sqlUpdate = "UPDATE items SET available = false WHERE item_id = ?";
        String sqlBorrowInsert = "INSERT INTO borrowing_history (item_id, borrow_date, return_date) VALUES (?, ?, ?)";
        
        int item_id = getValidInteger("Enter item id: ");
        String return_date = "Not returned";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
    
            pstmtCheck.setInt(1, item_id);
            ResultSet rs = pstmtCheck.executeQuery();
            
            if (rs.next()) {
                boolean available = rs.getBoolean("available");
                if (available) {
                    System.out.print("Enter borrow date: ");
                    String borrow_date = scanner.nextLine();
                    
                    // Update item status to not available
                    pstmtUpdate.setInt(1, item_id);
                    int rowsUpdated = pstmtUpdate.executeUpdate();
                    
                    // Insert the borrow record into the borrowing history
                    PreparedStatement pstmtInsert = conn.prepareStatement(sqlBorrowInsert);
                    pstmtInsert.setInt(1, item_id);
                    pstmtInsert.setString(2, borrow_date);
                    pstmtInsert.setString(3, return_date);
                    int rowsInserted = pstmtInsert.executeUpdate();
                    
                    if (rowsUpdated > 0 && rowsInserted > 0) {
                        System.out.println("Item borrowed successfully!");
                    }
                } else {
                    throw new ItemUnavailableException("Item with item id " + item_id + " is already borrowed.");
                }
            }
        } catch (SQLException e) {
           throw new SQLException(e);
        }
    }

    /**
     * Method to return a borrowed item to the library.
     * It updates the availability of the item and the borrowing history with the return date.
     * @throws librarymanage.ItemUnavailableException
     * @throws java.sql.SQLException
     */
    public static void returnItem() throws ItemUnavailableException, SQLException {
        String sqlCheck = "SELECT available FROM items WHERE item_id = ?";
        String sql_items = "UPDATE items SET available = true WHERE item_id = ?";
        String sql_borrow = "UPDATE borrowing_history SET return_date = ? WHERE item_id = ?";
        
        int item_id = getValidInteger("Enter item id: ");
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck);
             PreparedStatement pstmtUpdate_items = conn.prepareStatement(sql_items)) {
    
            pstmtCheck.setInt(1, item_id);
            ResultSet rs = pstmtCheck.executeQuery();
            
            if (rs.next()) {
                boolean available = rs.getBoolean("available");
                if (!available) {
                    System.out.print("Enter today's (return) date: ");
                    String return_date = scanner.nextLine();
                    
                    // Update the item status to available
                    pstmtUpdate_items.setInt(1, item_id);
                    int rowsUpdated_item = pstmtUpdate_items.executeUpdate();
                    
                    // Update the borrowing history with the return date
                    PreparedStatement pstmtUpdate_borrow = conn.prepareStatement(sql_borrow);
                    pstmtUpdate_borrow.setString(1, return_date);
                    pstmtUpdate_borrow.setInt(2, item_id);
                    int rowsUpdated_borrow = pstmtUpdate_borrow.executeUpdate();
                    
                    if (rowsUpdated_item > 0 && rowsUpdated_borrow > 0) {
                        System.out.println("Item returned successfully!");
                    }
                } else {
                    throw new ItemUnavailableException("Item with item id " + item_id + " is not borrowed yet.");
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}