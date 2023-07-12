package org.esgi.cookmaster.controller;

import org.esgi.cookmaster.database.User;
import org.esgi.cookmaster.database.Event;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class GenerateGUI<T> extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    private String[] columnNames;
    private String tableTitle;

    public GenerateGUI(String title) {
        this.tableTitle = title;

    }

    private Object[] itemToArray(T item) {
        if (item instanceof User) {
            User user = (User) item;
            return new Object[]{
                    user.getId(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getLastName(),
                    user.getFirstName(),
                    user.getAddress(),
                    user.getRole()
            };
        } else if (item instanceof Event) {
            Event event = (Event) item;
            return new Object[]{
                    event.getId(),
                    event.getName(),
                    event.getMax_capacity(),
                    event.getDescription(),
                    event.getType(),
                    event.getStart_time(),
                    event.getEnd_time(),
                    event.getRoom().toString()
            };
        }
        return null;
    }

    public void generateNewGUI(List<T> data) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Table Example");

        if (List.class.isAssignableFrom(data.getClass()) && !data.isEmpty()) {
            T firstItem = data.get(0);
            if (firstItem instanceof Event) {
                columnNames = new String[]{"ID", "Name", "Max Capacity", "Description", "Type", "Start Time", "End Time", "Room"};
            } else if (firstItem instanceof User) {
                columnNames = new String[]{"ID", "Email", "Phone", "Last Name", "First Name", "Address", "Role"};
            }
        }

        // Create a DefaultTableModel with columnNames and 0 rows
        tableModel = new DefaultTableModel(columnNames, 0);
        this.table = new JTable(tableModel);

        for (T item : data) {
            tableModel.addRow(itemToArray(item));
        }

        // Create a JScrollPane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a JButton
        JButton buttonUser = new JButton("Load User");
        JButton buttonEvent = new JButton("Load Event");
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(10); // Adjust the size as needed
        // Create the "GenerateClientStat" button
        JButton buttonClientStat = new JButton("Generate Client Stat");
        // Create the "GenerateEventStat" button

        JButton buttonEventStat = new JButton("Generate Event Stat");

        // Add an ActionListener to the button user
        buttonUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the list of stored users
                UserController testUserController = new UserController();
                List<User> storedUser = null;
                String quantityText = quantityField.getText();
                int quantity = 0;
                try {
                    quantity = Integer.parseInt(quantityText);
                } catch (NumberFormatException ex) {
                    quantity = 0;
                }
                columnNames = new String[]{"ID", "Email", "Phone", "Last Name", "First Name", "Address", "Role"};
                tableModel = new DefaultTableModel(columnNames, 0);
                table.setModel(tableModel);
                try {
                    storedUser = testUserController.getLastUsers(quantity);

                    // Remove all existing rows from the table
                    for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
                        tableModel.removeRow(i);
                    }

                    // Add new rows to the table
                    for (User user : storedUser) {
                        tableModel.addRow(itemToArray((T) user)); // Add the user to the table
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        // Add an ActionListener to the button event

        buttonEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the list of stored users
                EventController getEvent = new EventController();
                List<Event> storedEvent = null;
                String quantityText = quantityField.getText();
                int quantity = 0;
                columnNames = new String[]{"ID", "Name", "Max Capacity", "Description", "Type", "Start Time", "End Time", "Room"};
                tableModel = new DefaultTableModel(columnNames, 0);
                table.setModel(tableModel);
                try {
                    quantity = Integer.parseInt(quantityText);

                } catch (NumberFormatException ex) {
                    quantity = 0;
                }

                try {
                    storedEvent = getEvent.getLastEvents(quantity);

                    // Remove all existing rows from the table
                    for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
                        tableModel.removeRow(i);
                    }

                    // Add new rows to the table
                    for (Event event : storedEvent) {
                        tableModel.addRow(itemToArray((T) event)); // Add the user to the table
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        // Add an ActionListener to the button client stat
        buttonClientStat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerateClientStat userGraph = new GenerateClientStat();
                userGraph.extractAllCharts();
                JOptionPane.showMessageDialog(null, "User stat completed.");

            }
        });
        // Add an ActionListener to the button client stat

        buttonClientStat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform an action when the button is clicked
                GenerateEventStat eventGraph = new GenerateEventStat();
                eventGraph.extractAllCharts();
                JOptionPane.showMessageDialog(null, "Events stat completed.");

            }
        });
        // Set the layout of the JFrame to BorderLayout
        setLayout(new BorderLayout());

        // Add the scrollPane to the center of the JFrame
        add(scrollPane, BorderLayout.CENTER);
// Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(buttonUser);
        buttonPanel.add(buttonEvent);
        buttonPanel.add(buttonEventStat);
        buttonPanel.add(buttonClientStat);


// Create a panel for the input field
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));


        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);

// Create a panel for the bottom section
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.WEST);
        bottomPanel.add(inputPanel, BorderLayout.EAST);

// Add the bottom panel to the bottom of the JFrame
        add(bottomPanel, BorderLayout.SOUTH);

        // Set the size of the JFrame and make it visible
        setSize(600, 400);
        setVisible(true);
    }

}
