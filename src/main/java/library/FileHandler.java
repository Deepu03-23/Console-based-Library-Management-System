package library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String BOOKS_FILE = "data/books.txt";
    private static final String MEMBERS_FILE = "data/members.txt";

    // -------- BOOKS --------
    public List<Book> loadBooks() {
        new File("data").mkdirs();
        List<Book> books = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                Book b = new Book(d[0], d[1], d[2], Integer.parseInt(d[3]));
                b.setAvailable(Boolean.parseBoolean(d[4]));
                if (!d[5].equals("null")) b.setBorrowedBy(d[5]);
                if (!d[6].equals("null")) b.setDueDate(LocalDate.parse(d[6]));
                books.add(b);
            }
        } catch (IOException e) {
            System.out.println("Books file not found, starting fresh.");
        }
        return books;
    }

    public void saveBooks(List<Book> books) {
        try {
            new File("data").mkdirs();
            PrintWriter pw = new PrintWriter(new FileWriter(BOOKS_FILE));
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
            pw.close();
        } catch (IOException e) {
            System.out.println("Error saving books.");
        }
    }

    // -------- MEMBERS --------
    public List<Member> loadMembers() {
        new File("data").mkdirs();
        List<Member> members = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(MEMBERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                members.add(new Member(d[0], d[1]));
            }
        } catch (IOException e) {
            System.out.println("Members file not found, starting fresh.");
        }
        return members;
    }

    public void saveMembers(List<Member> members) {
        try {
            new File("data").mkdirs();
            PrintWriter pw = new PrintWriter(new FileWriter(MEMBERS_FILE));
            for (Member m : members) {
                pw.println(m.getId() + "," + m.getName());
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error saving members.");
        }
    }
}
