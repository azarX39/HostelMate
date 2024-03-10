package HostelManagmentSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class PaymentPage extends JFrame {
    private JTextField cardNumberField;
    private JTextField cvvField;

    private String selectedRoomType;
    private LocalDate selectedCheckInDate;
    private LocalDate selectedCheckOutDate;

    public PaymentPage(String roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        this.selectedRoomType = roomType;
        this.selectedCheckInDate = checkInDate;
        this.selectedCheckOutDate = checkOutDate;

        setTitle("Payment Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new GridLayout(4, 2));

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField(16);

        JLabel cvvLabel = new JLabel("CVV:");
        cvvField = new JTextField(3);

        JButton bookButton = new JButton("Book");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validatePaymentDetails()) {
                    if (bookRoom()) {
                        saveBookingInfo();
                        JOptionPane.showMessageDialog(PaymentPage.this, "Booking successful!");
                        dispose();
                        new UserPage2().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(PaymentPage.this, "Room not available for booking", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(PaymentPage.this, "Invalid payment details", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(cardNumberLabel);
        add(cardNumberField);
        add(cvvLabel);
        add(cvvField);
        add(new JLabel()); // Empty label for layout purposes
        add(new JLabel()); // Empty label for layout purposes
        add(bookButton);

        pack();
        setLocationRelativeTo(null); // Center the JFrame on the screen
        setVisible(true);
    }

    PaymentPage() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private boolean validatePaymentDetails() {
        String cardNumber = cardNumberField.getText().trim();
        String cvv = cvvField.getText().trim();

        // Perform your payment details validation here

        return cardNumber.length() == 14 && cvv.length() == 3;
    }

    private boolean bookRoom() {
        // Add your logic for booking the room here
        // Return true if the booking is successful, false otherwise
        return true; // Return true for demonstration purposes
    }

    private void saveBookingInfo() {
        try (FileWriter writer = new FileWriter("BookInfo.txt", true)) {
            int price = 0;

            if (selectedRoomType.equals("Single Room")) {
                price = 120;
            } else if (selectedRoomType.equals("Sharing Room")) {
                price = 70;
            }

            writer.write(selectedRoomType + "," + selectedCheckInDate + "," + selectedCheckOutDate + "," + price + ",paid\n");
            writer.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving booking information", "Error", JOptionPane.ERROR_MESSAGE);
        }

        try (FileWriter writer = new FileWriter("Connect.txt", true)) {
            int price = 0;

            if (selectedRoomType.equals("Single Room")) {
                price = 120;
            } else if (selectedRoomType.equals("Sharing Room")) {
                price = 70;
            }

            writer.write(selectedRoomType + "," + selectedCheckInDate + "," + selectedCheckOutDate + "," + price + ",paid\n");
            writer.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving booking information", "Error", JOptionPane.ERROR_MESSAGE);
        }
                
        
       
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PaymentPage("Single Room", LocalDate.now(), LocalDate.now().plusDays(1));
        });
    }
}
