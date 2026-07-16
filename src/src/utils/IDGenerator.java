// utils/IDGenerator.java
// Automatically generates unique IDs for library items.
// Books get B001, B002... Magazines get M001... Journals get J001...

package utils;

public class IDGenerator {

    // Separate counters for each item type
    private static int bookCounter     = 1;
    private static int magazineCounter = 1;
    private static int journalCounter  = 1;

    // Generates the next ID based on item type
    public static String generate(String type) {
        switch (type) {
            case "Book":
                return "B" + String.format("%03d", bookCounter++);
            case "Magazine":
                return "M" + String.format("%03d", magazineCounter++);
            case "Journal":
                return "J" + String.format("%03d", journalCounter++);
            default:
                return "X" + String.format("%03d", bookCounter++);
        }
    }

    // Resets all counters — useful when loading from file
    public static void reset() {
        bookCounter     = 1;
        magazineCounter = 1;
        journalCounter  = 1;
    }
}