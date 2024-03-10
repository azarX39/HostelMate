package HostelManagmentSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ViewRecords extends JFrame {
    private JTextArea statusArea;

    public ViewRecords() {
        setTitle("View Booking Records");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(714, 356);
        setLayout(new BorderLayout());

        statusArea = new JTextArea();
        statusArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(statusArea);
        add(scrollPane, BorderLayout.CENTER);

        displayBookingRecords();

        JButton exitButton = new JButton("Back");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminPage1().setVisible(true);
            }
        });
        add(exitButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Center the JFrame on the screen
        setVisible(true);
    }

    private void displayBookingRecords() {
        String fileName = "Records.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                String[] bookingInfo = line.split(",");
                if (bookingInfo.length == 6) {
                    String roomType = bookingInfo[0];
                    String checkInDate = bookingInfo[1];
                    String checkOutDate = bookingInfo[2];
                    String price = bookingInfo[3];
                    String status = bookingInfo[4];
                    String bookingStatus = bookingInfo[5];

                    statusArea.append("Booking #" + lineNumber + "\n");
                    statusArea.append("Room Type: " + roomType + "\n");
                    statusArea.append("Check-in Date: " + checkInDate + "\n");
                    statusArea.append("Check-out Date: " + checkOutDate + "\n");
                    statusArea.append("Price: " + price + " every night\n");
                    statusArea.append("Status: " + status + "\n");
                    statusArea.append("Booking Status: " + bookingStatus + "\n");
                    statusArea.append("\n");

                    lineNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ViewRecords();
        });
    }
}
