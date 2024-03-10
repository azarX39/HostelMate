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

public class UserDetailsFrame extends JFrame {
    private JTextArea userDetailsArea;

    public UserDetailsFrame() {
        setTitle("User Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the JFrame on the screen

        userDetailsArea = new JTextArea();
        userDetailsArea.setEditable(false);
        userDetailsArea.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(userDetailsArea);

        add(scrollPane);
    }

    public void displayUserDetails() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"))) {
            String line;
            StringBuilder userDetails = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                String username = data[0];
                String gender = data[1];
                int age = Integer.parseInt(data[2]);
                String password = data[3];

                userDetails.append("Username: ").append(username).append("\n");
                userDetails.append("Gender: ").append(gender).append("\n");
                userDetails.append("Age: ").append(age).append("\n");
                userDetails.append("Password: ").append(password).append("\n");
                userDetails.append("\n");
            }

            userDetailsArea.setText(userDetails.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserDetailsFrame frame = new UserDetailsFrame();
            frame.displayUserDetails();
            frame.setVisible(true);
        });
    }
}
