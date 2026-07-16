// gui/AdminPanel.java
// Lets the admin add new items and undo the last action.
// Demonstrates: text fields, combo boxes, buttons, dialogs,
// input validation, and the undo Stack in action.

package gui;

import controller.LibraryDatabase;
import model.*;
import utils.ReportGenerator;
import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel implements Refreshable {

    private LibraryDatabase database;
    private MainWindow      mainWindow;

    // Form fields
    private JTextField  titleField, authorField, yearField, extraField1, extraField2;
    private JComboBox<String> typeCombo;
    private JLabel      extra1Label, extra2Label;
    private JTextArea   logArea;

    public AdminPanel(LibraryDatabase database, MainWindow mainWindow) {
        this.database   = database;
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }

    private void initComponents() {
        // --- Form panel (left side) ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Item"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(6, 8, 6, 8);
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.anchor  = GridBagConstraints.WEST;

        // Type selector
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Item Type:"), gbc);
        gbc.gridx = 1;
        typeCombo = new JComboBox<>(new String[]{"Book", "Magazine", "Journal"});
        typeCombo.addActionListener(e -> updateExtraFields());
        formPanel.add(typeCombo, gbc);

        // Title
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(20);
        formPanel.add(titleField, gbc);

        // Author
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        authorField = new JTextField(20);
        formPanel.add(authorField, gbc);

        // Year
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        yearField = new JTextField(20);
        formPanel.add(yearField, gbc);

        // Dynamic extra fields (change based on type)
        gbc.gridx = 0; gbc.gridy = 4;
        extra1Label = new JLabel("Genre:");
        formPanel.add(extra1Label, gbc);
        gbc.gridx = 1;
        extraField1 = new JTextField(20);
        formPanel.add(extraField1, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        extra2Label = new JLabel("ISBN:");
        formPanel.add(extra2Label, gbc);
        gbc.gridx = 1;
        extraField2 = new JTextField(20);
        formPanel.add(extraField2, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn  = new JButton("Add Item");
        JButton undoBtn = new JButton("Undo Last Action");
        JButton clearBtn = new JButton("Clear Form");

        addBtn.setToolTipText("Add a new item to the library");
        undoBtn.setToolTipText("Undo the most recent admin action");

        addBtn.addActionListener(e  -> addItem());
        undoBtn.addActionListener(e -> undoAction());
        clearBtn.addActionListener(e -> clearForm());

        btnPanel.add(addBtn);
        btnPanel.add(undoBtn);
        btnPanel.add(clearBtn);
        formPanel.add(btnPanel, gbc);

        add(formPanel, BorderLayout.WEST);

        // --- Log area (right side) ---
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("Action Log"));
        add(logScroll, BorderLayout.CENTER);
    }

    // Updates the two dynamic labels/fields based on selected type
    private void updateExtraFields() {
        String type = (String) typeCombo.getSelectedItem();
        switch (type) {
            case "Book":
                extra1Label.setText("Genre:");
                extra2Label.setText("ISBN:");
                break;
            case "Magazine":
                extra1Label.setText("Issue No:");
                extra2Label.setText("Publisher:");
                break;
            case "Journal":
                extra1Label.setText("Volume:");
                extra2Label.setText("Research Field:");
                break;
        }
        revalidate();
        repaint();
    }

    private void addItem() {
        // --- Input validation ---
        String title  = titleField.getText().trim();
        String author = authorField.getText().trim();
        String yearTxt = yearField.getText().trim();
        String extra1 = extraField1.getText().trim();
        String extra2 = extraField2.getText().trim();

        if (title.isEmpty() || author.isEmpty() || yearTxt.isEmpty()
                || extra1.isEmpty() || extra2.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields.", "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearTxt);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Year must be a number.", "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- Create the right type of item ---
        String type   = (String) typeCombo.getSelectedItem();
        String itemId = type.substring(0, 1).toUpperCase() + System.currentTimeMillis();
        LibraryItem newItem;

        switch (type) {
            case "Book":
                newItem = new Book(itemId, title, author, year, extra1, extra2);
                break;
            case "Magazine":
                newItem = new Magazine(itemId, title, author, year,
                                       Integer.parseInt(extra1), extra2);
                break;
            default: // Journal
                newItem = new Journal(itemId, title, author, year,
                                      Integer.parseInt(extra1), extra2);
        }

        database.addItem(newItem);
        log("ADDED: " + newItem);
        mainWindow.setStatus("Added: " + title);
        mainWindow.refreshAll();
        clearForm();
    }

    private void undoAction() {
        String result = database.undoLastAction();
        log("UNDO: " + result);
        mainWindow.setStatus(result);
        mainWindow.refreshAll();
    }

    private void clearForm() {
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
        extraField1.setText("");
        extraField2.setText("");
    }

    private void log(String message) {
        logArea.append(message + "\n");
    }

    @Override
    public void refresh() {
        log("Panel refreshed.");
    }
}