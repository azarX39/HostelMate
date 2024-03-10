package HostelManagmentSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ManageRecords extends JFrame {
    private JTextArea statusArea;

    public ManageRecords() {
        setTitle("Applications");
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
        String fileName = "Connect.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 1;

                while ((line = reader.readLine()) != null) {                
                    String[] bookingInfo = line.split(",");
                    if (bookingInfo.length == 5) {
                    String roomType = bookingInfo[0];
                    String checkInDate = bookingInfo[1];
                    String checkOutDate = bookingInfo[2];
                    String price = bookingInfo[3];
                    String status = bookingInfo[4];

                    statusArea.append("Booking #" + lineNumber + "\n");
                    statusArea.append("Room Type: " + roomType + "\n");
                    statusArea.append("Check-in Date: " + checkInDate + "\n");
                    statusArea.append("Check-out Date: " + checkOutDate + "\n");
                    statusArea.append("Status: " + status + "\n");
                    statusArea.append("Price: " + price + " every night\n");
                    statusArea.append("\n");

                    JPanel buttonPanel = new JPanel();

                    JButton acceptButton = new JButton("Accept");
                    acceptButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String updatedStatus = roomType + "," + checkInDate + "," + checkOutDate + "," + price + "," + status + "," + "Accepted";
                            writeToFile(updatedStatus);
                        }
                    });
                    buttonPanel.add(acceptButton);

                    JButton cancelButton = new JButton("Cancel");
                    cancelButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String updatedStatus = roomType + "," + checkInDate + "," + checkOutDate + "," + price + "," + status + "," + "Canceled";
                            writeToFile(updatedStatus);
                        }
                    });
                    buttonPanel.add(cancelButton);

                    int finalLineNumber = lineNumber; // Create a separate variable for ActionListener
                    
                    
                    
                    
                    JButton exitlButton = new JButton("Back");
                    exitlButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dispose();
                            new AdminPage1().setVisible(true);
               
                        }
                    });
                    
                    
                    buttonPanel.add(exitlButton);
                    



                    add(buttonPanel, BorderLayout.SOUTH);

                    lineNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void writeToFile(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Records.txt", true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ManageRecords();
        });
    }
}
