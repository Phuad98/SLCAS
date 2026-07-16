import model.*;
import utils.RecursiveUtils;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

    // Book needs: itemId, title, author, year, genre, isbn
items.add(new Book("B001", "Clean Code", "Robert Martin", 2008, "Technology", "978-0132350884"));
items.add(new Book("B002", "The Pragmatic Programmer", "David Thomas", 1999, "Technology", "978-0201616224"));
items.add(new Book("B003", "Algorithms Unlocked", "Thomas Cormen", 2013, "Computer Science", "978-0262518802"));

// Magazine needs: itemId, title, author, year, issueNumber, publisher
items.add(new Magazine("M001", "Time Magazine", "Various", 2023, 45, "Time USA"));
items.add(new Magazine("M002", "Forbes", "Various", 2022, 10, "Forbes Media"));

// Journal needs: itemId, title, author, year, volume, researchField
items.add(new Journal("J001", "Advanced Java", "James Gosling", 2020, 3, "Computer Science"));

        // ── COUNT BY CATEGORY ──
        System.out.println("── Count by Category ──");
        int bookCount     = RecursiveUtils.countByCategory(items, "Book", 0);
        int magCount      = RecursiveUtils.countByCategory(items, "Magazine", 0);
        int journalCount  = RecursiveUtils.countByCategory(items, "Journal", 0);

        System.out.println("Books:     " + bookCount);
        System.out.println("Magazines: " + magCount);
        System.out.println("Journals:  " + journalCount);

        // ── OVERDUE FINE ──
        System.out.println("\n── Overdue Fine Calculator ──");
        System.out.println("3 days overdue:  $" + RecursiveUtils.computeOverdueFine(3));
        System.out.println("7 days overdue:  $" + RecursiveUtils.computeOverdueFine(7));
        System.out.println("14 days overdue: $" + RecursiveUtils.computeOverdueFine(14));

        // ── PRINT CATALOGUE ──
        System.out.println("\n── Full Catalogue ──");
        RecursiveUtils.printCatalogue(items, 0);
    }
}
