package utils;

import model.LibraryItem;
import java.util.ArrayList;

public class RecursiveUtils {

    // ── RECURSIVE 1: Count items by category ─────────────────
    // Counts how many items match a given type (Book, Magazine, Journal)
    // by recursively walking through the list

    public static int countByCategory(ArrayList<LibraryItem> items, String type, int index) {

        // base case: we've checked every item
        if (index >= items.size()) {
            return 0;
        }

        // check current item — does it match the type?
        int match = items.get(index).getItemType().equalsIgnoreCase(type) ? 1 : 0;

        // add match to result of checking the rest of the list
        return match + countByCategory(items, type, index + 1);
    }


    // ── RECURSIVE 2: Compute overdue fine ────────────────────
    // Fine is $0.50 per overdue day
    // Recursively adds $0.50 for each day until daysOverdue = 0

    public static double computeOverdueFine(int daysOverdue) {

        // base case: no days overdue, no fine
        if (daysOverdue <= 0) {
            return 0.0;
        }

        // recursive case: $0.50 for today + fine for remaining days
        return 0.50 + computeOverdueFine(daysOverdue - 1);
    }


    // ── RECURSIVE 3: Print all items in catalogue ─────────────
    // Walks through the list recursively and prints each item

    public static void printCatalogue(ArrayList<LibraryItem> items, int index) {

        // base case: end of list
        if (index >= items.size()) {
            return;
        }

        // print current item
        System.out.println((index + 1) + ". " + items.get(index));

        // recurse to next item
        printCatalogue(items, index + 1);
    }
}