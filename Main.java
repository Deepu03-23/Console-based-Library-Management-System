package library;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Library library = new Library();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
            System.out.println("1. Add New Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Books");
            System.out.println("4. Register Member");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. View Library Statistics");
            System.out.println("8. View Overdue Books");
            System.out.println("9. Export Books to CSV");
            System.out.println("10. Remove Book");
            System.out.println("11. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 -> {
                    System.out.print("ISBN: ");
                    String isbn = sc.nextLine();
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Author: ");
                    String author = sc.nextLine();
                    System.out.print("Year: ");
                    int year = sc.nextInt();
                    sc.nextLine();
                    library.addBook(new Book(isbn, title, author, year));
                }

                case 2 -> library.displayAllBooks();

                case 3 -> {
                    System.out.print("Enter keyword: ");
                    library.searchBooks(sc.nextLine());
                }

                case 4 -> {
                    System.out.print("Member ID: ");
                    String id = sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    library.registerMember(new Member(id, name));
                }

                case 5 -> {
                    System.out.print("ISBN: ");
                    String isbn = sc.nextLine();
                    System.out.print("Member ID: ");
                    String id = sc.nextLine();
                    library.borrowBook(isbn, id);
                }

                case 6 -> {
                    System.out.print("ISBN: ");
                    String isbn = sc.nextLine();
                    System.out.print("Member ID: ");
                    String id = sc.nextLine();
                    library.returnBook(isbn, id);
                }

                case 7 -> library.displayStatistics();

                case 8 -> library.displayOverdueBooks();

                case 9 -> library.exportToCSV();

                case 10 -> {
                    System.out.print("Enter ISBN to remove: ");
                    library.removeBook(sc.nextLine());
                }

                case 11 -> {
                    System.out.println("Exiting system...");
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
