// controller/LibraryDatabase.java
// This class is the central data store for the entire system.
// It holds all library items and manages the four core data structures.

package controller;

import model.LibraryItem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class LibraryDatabase {

    // -------------------------------------------------------
    // 1. ARRAYLIST — stores every library item in the system
    // -------------------------------------------------------
    // Why ArrayList? Because the number of items changes often
    // (books get added/removed), so we need a resizable list.
    private ArrayList<LibraryItem> items;

    // -------------------------------------------------------
    // 2. QUEUE — reservation waitlist for borrowed items
    // -------------------------------------------------------
    // Why Queue? Because it's FIFO — the first person to
    // join the waitlist should be the first to get the book.
    // Each entry is "itemId:userId" e.g. "B001:U003"
    private Queue<String> reservationQueue;

    // -------------------------------------------------------
    // 3. STACK — undo last admin operation
    // -------------------------------------------------------
    // Why Stack? Because it's LIFO — the last action done
    // should be the first one undone (just like Ctrl+Z).
    // Each entry describes what was done e.g. "ADD:B001"
    private Stack<String> undoStack;

    // -------------------------------------------------------
    // 4. ARRAY — fixed-size cache for most accessed items
    // -------------------------------------------------------
    // Why Array? Because the cache has a hard limit (5 slots).
    // Arrays are perfect for fixed-size, fast-access storage.
    private static final int CACHE_SIZE = 5;
    private LibraryItem[] frequentCache;
    private int cacheCount; // tracks how many slots are filled

    // Constructor — initialises all four data structures
    public LibraryDatabase() {
        items             = new ArrayList<>();
        reservationQueue  = new LinkedList<>();  // LinkedList implements Queue
        undoStack         = new Stack<>();
        frequentCache     = new LibraryItem[CACHE_SIZE];
        cacheCount        = 0;
    }

    // =======================================================
    // ARRAYLIST OPERATIONS
    // =======================================================

    // Add a new item to the library
    public void addItem(LibraryItem item) {
        items.add(item);
        undoStack.push("ADD:" + item.getItemId()); // log for undo
    }

    // Remove an item by its ID
    public boolean removeItem(String itemId) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemId().equals(itemId)) {
                LibraryItem removed = items.remove(i);
                undoStack.push("REMOVE:" + removed.getItemId());
                return true; // removal successful
            }
        }
        return false; // item not found
    }

    // Get all items (used by search and sort)
    public ArrayList<LibraryItem> getAllItems() {
        return items;
    }

    // Get a single item by ID
    public LibraryItem getItemById(String itemId) {
        for (LibraryItem item : items) {
            if (item.getItemId().equals(itemId)) {
                return item;
            }
        }
        return null; // not found
    }

    // =======================================================
    // QUEUE OPERATIONS
    // =======================================================

    // Add a user to the waitlist for a specific item
    public void addToQueue(String itemId, String userId) {
        reservationQueue.add(itemId + ":" + userId);
        System.out.println("User " + userId + " added to waitlist for " + itemId);
    }

    // Get the next reservation from the queue (when book is returned)
    public String getNextReservation() {
        return reservationQueue.poll(); // returns null if queue is empty
    }

    // Check if anyone is waiting for a specific item
    public boolean hasReservation(String itemId) {
        for (String entry : reservationQueue) {
            if (entry.startsWith(itemId + ":")) {
                return true;
            }
        }
        return false;
    }

    // =======================================================
    // STACK OPERATIONS
    // =======================================================

    // Undo the last admin action
    public String undoLastAction() {
        if (undoStack.isEmpty()) {
            return "Nothing to undo.";
        }
        String lastAction = undoStack.pop();
        return "Undone: " + lastAction;
    }

    // Peek at what the next undo would be (without removing it)
    public String peekUndo() {
        if (undoStack.isEmpty()) return "No actions to undo.";
        return "Next undo: " + undoStack.peek();
    }

    // =======================================================
    // CACHE OPERATIONS
    // =======================================================

    // Add an item to the frequently accessed cache
    public void addToCache(LibraryItem item) {
        if (cacheCount < CACHE_SIZE) {
            frequentCache[cacheCount] = item;
            cacheCount++;
        } else {
            // Cache is full — shift everything left, add new item at end
            // This keeps the most recent 5 items
            for (int i = 0; i < CACHE_SIZE - 1; i++) {
                frequentCache[i] = frequentCache[i + 1];
            }
            frequentCache[CACHE_SIZE - 1] = item;
        }
    }

    // Display all cached items
    public void printCache() {
        System.out.println("=== Most Accessed Items ===");
        for (int i = 0; i < cacheCount; i++) {
            System.out.println((i + 1) + ". " + frequentCache[i].getTitle());
        }
    }

    // Get total number of items in the library
    public int getTotalItems() {
        return items.size();
    }
}