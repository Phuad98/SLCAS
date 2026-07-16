package controller;

import model.LibraryItem;
import java.util.ArrayList;

public class SearchEngine {

    // ── LINEAR SEARCH ────────────────────────────────────────
    // Checks every item one by one — works on unsorted lists
    public static LibraryItem linearSearchByTitle(ArrayList<LibraryItem> items, String title) {
        for (LibraryItem item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                return item;
            }
        }
        return null; // not found
    }

    public static ArrayList<LibraryItem> linearSearchByAuthor(ArrayList<LibraryItem> items, String author) {
        ArrayList<LibraryItem> results = new ArrayList<>();
        for (LibraryItem item : items) {
            if (item.getAuthor().equalsIgnoreCase(author)) {
                results.add(item);
            }
        }
        return results;
    }

    public static ArrayList<LibraryItem> linearSearchByType(ArrayList<LibraryItem> items, String type) {
        ArrayList<LibraryItem> results = new ArrayList<>();
        for (LibraryItem item : items) {
            if (item.getItemType().equalsIgnoreCase(type)) {
                results.add(item);
            }
        }
        return results;
    }

    // ── BINARY SEARCH ────────────────────────────────────────
    // Much faster but list MUST be sorted by title first
    public static LibraryItem binarySearchByTitle(ArrayList<LibraryItem> items, String title) {
        int low = 0;
        int high = items.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            String midTitle = items.get(mid).getTitle().toLowerCase();
            String target = title.toLowerCase();

            int comparison = midTitle.compareTo(target);

            if (comparison == 0) {
                return items.get(mid); // found it
            } else if (comparison < 0) {
                low = mid + 1;         // search right half
            } else {
                high = mid - 1;        // search left half
            }
        }
        return null; // not found
    }

    // ── RECURSIVE SEARCH ─────────────────────────────────────
    // Searches by title using recursion — calls itself with a
    // smaller portion of the list each time
    public static LibraryItem recursiveSearchByTitle(
            ArrayList<LibraryItem> items, String title, int index) {

        // base case 1: reached end of list without finding it
        if (index >= items.size()) {
            return null;
        }

        // base case 2: found it
        if (items.get(index).getTitle().equalsIgnoreCase(title)) {
            return items.get(index);
        }

        // recursive case: check the next index
        return recursiveSearchByTitle(items, title, index + 1);
    }
}