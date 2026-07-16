package controller;

import model.LibraryItem;
import java.util.ArrayList;

public class SortEngine {

    // ── INSERTION SORT ───────────────────────────────────────
    // Good for small lists — picks each item and inserts it
    // into its correct position in the already-sorted portion

    public static void insertionSortByTitle(ArrayList<LibraryItem> items) {
        for (int i = 1; i < items.size(); i++) {
            LibraryItem key = items.get(i);
            int j = i - 1;

            while (j >= 0 && items.get(j).getTitle()
                    .compareToIgnoreCase(key.getTitle()) > 0) {
                items.set(j + 1, items.get(j));
                j--;
            }
            items.set(j + 1, key);
        }
    }

    public static void insertionSortByAuthor(ArrayList<LibraryItem> items) {
        for (int i = 1; i < items.size(); i++) {
            LibraryItem key = items.get(i);
            int j = i - 1;

            while (j >= 0 && items.get(j).getAuthor()
                    .compareToIgnoreCase(key.getAuthor()) > 0) {
                items.set(j + 1, items.get(j));
                j--;
            }
            items.set(j + 1, key);
        }
    }

    public static void insertionSortByYear(ArrayList<LibraryItem> items) {
        for (int i = 1; i < items.size(); i++) {
            LibraryItem key = items.get(i);
            int j = i - 1;

            while (j >= 0 && items.get(j).getYear() > key.getYear()) {
                items.set(j + 1, items.get(j));
                j--;
            }
            items.set(j + 1, key);
        }
    }

    // ── MERGE SORT ───────────────────────────────────────────
    // Better for large lists — splits list in half repeatedly,
    // sorts each half, then merges them back together

    public static ArrayList<LibraryItem> mergeSortByTitle(ArrayList<LibraryItem> items) {
        if (items.size() <= 1) return items;

        int mid = items.size() / 2;
        ArrayList<LibraryItem> left  = new ArrayList<>(items.subList(0, mid));
        ArrayList<LibraryItem> right = new ArrayList<>(items.subList(mid, items.size()));

        left  = mergeSortByTitle(left);
        right = mergeSortByTitle(right);

        return mergeByTitle(left, right);
    }

    private static ArrayList<LibraryItem> mergeByTitle(
            ArrayList<LibraryItem> left, ArrayList<LibraryItem> right) {

        ArrayList<LibraryItem> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getTitle()
                    .compareToIgnoreCase(right.get(j).getTitle()) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size())  result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));

        return result;
    }
}