
package HostelManagmentSystem;

import HostelManagmentSystem.PaymentPage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class BookRoom extends JFrame {
    private String selectedRoomType;
    private LocalDate selectedCheckInDate;
    private LocalDate selectedCheckOutDate;
    private Map<LocalDate, Map<String, Integer>> roomAvailability;
    private int availableRooms;

    public BookRoom() {
        roomAvailability = new HashMap<>();

        // Read room availability from RoomsInfo.txt file
        loadRoomAvailability();
        LocalDate currentDate = LocalDate.now();
        LocalDate endOfYear = LocalDate.of(currentDate.getYear(), 12, 31);

        LocalDate date = currentDate;
        while (date.isBefore(endOfYear) || date.isEqual(endOfYear)) {
            Map<String, Integer> roomTypes = new HashMap<>();
            roomTypes.put("Single Room", 20);
            roomTypes.put("Sharing Room", 20);
            roomAvailability.put(date, roomTypes);
            date = date.plusDays(1);
        }
        
        availableRooms = 20;


        // Initialize availableRooms
        // Initialize availableRooms

        // Combo box for check-in date selection
        JComboBox<LocalDate> checkInCombo = new JComboBox<>();
        LocalDate checkInDate = currentDate;
        while (checkInDate.isBefore(endOfYear) || checkInDate.isEqual(endOfYear)) {
            checkInCombo.addItem(checkInDate);
            checkInDate = checkInDate.plusDays(1);
        }
        
        
        
 
        
        
        checkInCombo.setSelectedIndex(0);

        // Combo box for check-out date selection
        JComboBox<LocalDate> checkOutCombo = new JComboBox<>();

        // Add item listener to update check-out combo box when check-in date changes
        checkInCombo.addItemListener(e -> {
            LocalDate selectedCheckInDate = (LocalDate) checkInCombo.getSelectedItem();
            checkOutCombo.removeAllItems();
            LocalDate endDate = selectedCheckInDate.plusDays(1);
            while (endDate.isBefore(endOfYear) || endDate.isEqual(endOfYear)) {
                checkOutCombo.addItem(endDate);
                endDate = endDate.plusDays(1);
            }
            
            
            
            
            
            
            

        });
        
        
       

        // Button group for room type selection
        ButtonGroup roomTypeGroup = new ButtonGroup();

        // Single room radio button
        JRadioButton singleRoomButton = new JRadioButton("Single Room");
        roomTypeGroup.add(singleRoomButton);

        // Sharing room radio button
        JRadioButton sharingRoomButton = new JRadioButton("Sharing Room");
        roomTypeGroup.add(sharingRoomButton);

        // Panel to display room availability
        JPanel availabilityPanel = new JPanel(new GridLayout(3, 1));

        // Update room availability panel based on selected room type
        singleRoomButton.addActionListener(e -> {
            availabilityPanel.removeAll();
            availabilityPanel.add(new JLabel("Available Single Rooms: " + availableRooms));
            availabilityPanel.add(new JLabel("Price: Rm 120 every night"));
            availabilityPanel.add(new JLabel("Bathroom is not shared"));
            availabilityPanel.add(new JLabel("Include breakfast"));
            availabilityPanel.revalidate();
            availabilityPanel.repaint();
        });

        sharingRoomButton.addActionListener(e -> {
            availabilityPanel.removeAll();
            availabilityPanel.add(new JLabel("Available Sharing Rooms: " + availableRooms));
            availabilityPanel.add(new JLabel("Price: Rm 70 every night"));
            availabilityPanel.add(new JLabel("Bathroom is shared"));
            availabilityPanel.add(new JLabel("Include breakfast"));
            availabilityPanel.revalidate();
            availabilityPanel.repaint();
        });

        // Panel for the room type and buttons
        JPanel roomTypePanel = new JPanel(new FlowLayout());
        roomTypePanel.add(singleRoomButton);
        roomTypePanel.add(sharingRoomButton);

        // Add components to the main frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));
        add(new JLabel("Check-in Date:"));
        add(checkInCombo);
        add(new JLabel("Check-out Date:"));
        add(checkOutCombo);
        add(new JLabel("Select Room Type:"));
        add(roomTypePanel);
        
      
        add(availabilityPanel);

        // Example usage: Book a room based on user selections
        JButton bookButton = new JButton("Book");
        bookButton.addActionListener(e -> {
            LocalDate selectedCheckInDate = (LocalDate) checkInCombo.getSelectedItem();
            LocalDate selectedCheckOutDate = (LocalDate) checkOutCombo.getSelectedItem();
            String selectedRoomType = singleRoomButton.isSelected() ? "Single Room" : "Sharing Room";

            // Check if room type is selected
            if (!singleRoomButton.isSelected() && !sharingRoomButton.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please select a room type", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if check-in and check-out dates are selected and valid
            if (selectedCheckInDate == null || selectedCheckOutDate == null || selectedCheckInDate.isAfter(selectedCheckOutDate)) {
                JOptionPane.showMessageDialog(this, "Please select valid check-in and check-out dates", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check room availability and book the room
            bookRoom(selectedCheckInDate, selectedCheckOutDate, selectedRoomType);

            // Update the available rooms label
            if (singleRoomButton.isSelected()) {
                availabilityPanel.removeAll();
                availabilityPanel.add(new JLabel("Available Single Rooms: " + availableRooms));
                availabilityPanel.add(new JLabel("Price: Rm 120 every night"));
                availabilityPanel.add(new JLabel("Bathroom is not shared"));
                availabilityPanel.add(new JLabel("Include breakfast"));
                availabilityPanel.revalidate();
                availabilityPanel.repaint();
            } else if (sharingRoomButton.isSelected()) {
                availabilityPanel.removeAll();
                availabilityPanel.add(new JLabel("Available Sharing Rooms: " + availableRooms));
                availabilityPanel.add(new JLabel("Price: Rm 70 every night"));
                availabilityPanel.add(new JLabel("Bathroom is shared"));
                availabilityPanel.add(new JLabel("Include breakfast"));
                availabilityPanel.revalidate();
                availabilityPanel.repaint();
            }
            
            
            dispose(); // Dispose the BookRoom frame after booking

          new PaymentPage(selectedRoomType, selectedCheckInDate, selectedCheckOutDate).setVisible(true);
    
        });

        add(bookButton);

        setSize(714, 356);
        setLocationRelativeTo(null); // Center the JFrame on the screen
        setVisible(true);
    }
    
    
    
private void loadRoomAvailability() {
    try {
        Path filePath = Paths.get("RoomsInfo.txt");
        Scanner scanner = new Scanner(filePath);

        roomAvailability.clear(); // Clear the existing room availability data

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            if (parts.length == 2) {
                String roomType = parts[0].trim();
                int availableCount = Integer.parseInt(parts[1].trim());

                Map<String, Integer> roomTypeAvailability = roomAvailability.computeIfAbsent(LocalDate.now(), k -> new HashMap<>());
                roomTypeAvailability.put(roomType, availableCount);
            }
        }

        scanner.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}



    public void bookRoom(LocalDate checkInDate, LocalDate checkOutDate, String selectedRoomType) {
        // Check if the room type is available for the given check-in and check-out dates
        if (!roomAvailability.containsKey(checkInDate) || !roomAvailability.get(checkInDate).containsKey(selectedRoomType)
                || roomAvailability.get(checkInDate).get(selectedRoomType) == 0) {
            JOptionPane.showMessageDialog(this, "Selected room type is not available for the chosen dates", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        
                Map<String, Integer> rooms = roomAvailability.get(checkInDate);
        rooms.put(selectedRoomType, rooms.get(selectedRoomType) - 1);
        availableRooms--;

        // Update the room availability in the file
        updateRoomAvailability();

     }

    public void checkRoomAvailability(LocalDate checkInDate, LocalDate checkOutDate) {
        // Room availability check logic goes here...
    
    }
    
    
    
        private void updateRoomAvailability() {
            try {
                Path filePath = Paths.get("RoomsInfo.txt");
                StringBuilder sb = new StringBuilder();

                for (LocalDate date : roomAvailability.keySet()) {
                    Map<String, Integer> rooms = roomAvailability.get(date);

                    for (String roomType : rooms.keySet()) {
                        int availableCount = rooms.get(roomType);
                        sb.append(roomType).append(",").append(availableCount).append(System.lineSeparator());
                    }
                }

                Files.write(filePath, sb.toString().getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
    private void centerOnScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;
        setLocation(x, y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BookRoom();
        });
    }
}

