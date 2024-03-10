/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HostelManagmentSystem;


import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Records extends JFrame {
    private JTextArea statusArea;

    public Records() {
        setTitle("Booking History");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(714, 356);
        setLayout(new BorderLayout());

        statusArea = new JTextArea();
        statusArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(statusArea);
        add(scrollPane, BorderLayout.CENTER);

        displayBookingInfo();

        setLocationRelativeTo(null); // Center the JFrame on the screen
        setVisible(true);
    }

    private void displayBookingInfo() {
        String fileName = "BookInfo.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                String[] bookingInfo = line.split(",");
                if (bookingInfo.length == 5) {
                    String roomType = bookingInfo[0];
                    String checkInDate = bookingInfo[1];
                    String checkOutDate = bookingInfo[2];
                    String Price = bookingInfo[3];
                    String Status = bookingInfo[4];
                    
                    
                    statusArea.append("Booking #" + lineNumber + "\n");
                    statusArea.append("Room Type: " + roomType + "\n");
                    statusArea.append("Check-in Date: " + checkInDate + "\n");
                    statusArea.append("Check-out Date: " + checkOutDate + "\n");
                    statusArea.append("Status: " + Status + "\n");
                    statusArea.append("Price: " + Price + "\n");
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
            new Records();
        });
    }
}
