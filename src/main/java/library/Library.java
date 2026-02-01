package library;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Library {

    private List<Book> books;
    private List<Member> members;
    private FileHandler fileHandler;

    public Library() {
        fileHandler = new FileHandler();
        books = fileHandler.loadBooks();
        members = fileHandler.loadMembers();
    }

    public void addBook(Book book) {
        books.add(book);
        fileHandler.saveBooks(books);
        System.out.println("Book added successfully!");
    }

    public void removeBook(String isbn) {
        Book book = findBook(isbn);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("Cannot remove borrowed book!");
            return;
        }
        books.remove(book);
        fileHandler.saveBooks(books);
        System.out.println("Book removed successfully!");
    }

    public void displayAllBooks() {
        System.out.println("\n=== ALL BOOKS ===");
        System.out.println("Total books: " + books.size());
        System.out.println("-".repeat(80));
        int i = 1;
        for (Book b : books) System.out.println(i++ + ". " + b);
    }

    public void searchBooks(String keyword) {
        List<Book> result = books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(keyword.toLowerCase())
                        || b.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        if (result.isEmpty()) System.out.println("No books found.");
        else result.forEach(System.out::println);
    }

    public void registerMember(Member member) {
        members.add(member);
        fileHandler.saveMembers(members);
        System.out.println("Member registered successfully!");
    }

    public void borrowBook(String isbn, String memberId) {
        Book book = findBook(isbn);
        Member member = findMember(memberId);

        if (book == null || member == null) {
            System.out.println("Invalid details!");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("Book already borrowed!");
            return;
        }

        book.setAvailable(false);
        book.setBorrowedBy(memberId);
        book.setDueDate(LocalDate.now().plusDays(14));
        member.borrowBook(isbn);

        fileHandler.saveBooks(books);
        fileHandler.saveMembers(members);

        System.out.println("Book borrowed successfully!");
    }

    public void returnBook(String isbn, String memberId) {
        Book book = findBook(isbn);
        Member member = findMember(memberId);

        if (book == null || member == null) {
            System.out.println("Invalid details!");
            return;
        }

        book.setAvailable(true);
        book.setBorrowedBy(null);
        book.setDueDate(null);
        member.returnBook(isbn);

        fileHandler.saveBooks(books);
        fileHandler.saveMembers(members);

        System.out.println("Book returned successfully!");
    }

    public void displayStatistics() {
        long available = books.stream().filter(Book::isAvailable).count();
        long borrowed = books.size() - available;
        long overdue = books.stream().filter(b -> !b.isAvailable() && b.isOverdue()).count();

        System.out.println("\n=== LIBRARY STATISTICS ===");
        System.out.println("Total Books: " + books.size());
        System.out.println("Available Books: " + available);
        System.out.println("Borrowed Books: " + borrowed);
        System.out.println("Registered Members: " + members.size());
        System.out.println("Overdue Books: " + overdue);
    }

    public void displayOverdueBooks() {
        System.out.println("\n=== OVERDUE BOOKS ===");
        boolean found = false;
        for (Book b : books) {
            if (!b.isAvailable() && b.isOverdue()) {
                System.out.println(b + " | Due: " + b.getDueDate());
                found = true;
            }
        }
        if (!found) System.out.println("No overdue books.");
    }

    public void exportToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("data/books.csv"))) {
            pw.println("ISBN,Title,Author,Year,Available,BorrowedBy,DueDate");
            for (Book b : books) {
                pw.println(
                    b.getIsbn() + "," +
                    b.getTitle() + "," +
                    b.getAuthor() + "," +
                    b.getYear() + "," +
                    b.isAvailable() + "," +
                    b.getBorrowedBy() + "," +
                    b.getDueDate()
                );
            }
            System.out.println("Books exported to CSV.");
        } catch (IOException e) {
            System.out.println("CSV export failed.");
        }
    }

    private Book findBook(String isbn) {
        return books.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst().orElse(null);
    }

    private Member findMember(String id) {
        return members.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }
}
