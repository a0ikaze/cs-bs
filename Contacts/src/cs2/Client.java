package cs2;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("Select an option: 1. View All 2. Add 3. Update 4. Delete 5. Exit");
                String option = scanner.nextLine();
                output.writeObject(option);
                output.flush();

                switch (option) {
                    case "1":  // View All Contacts
                        List<Contact> contacts = (List<Contact>) input.readObject();
                        System.out.println("Contacts:");
                        for (Contact contact : contacts) {
                            System.out.println(contact);
                        }
                        break;

                    case "2":  // Add New Contact
                        System.out.println("Enter name:");
                        String name = scanner.nextLine();
                        System.out.println("Enter address:");
                        String address = scanner.nextLine();
                        System.out.println("Enter phone:");
                        String phone = scanner.nextLine();
                        Contact newContact = new Contact(0, name, address, phone);  // ID will be set by the server
                        output.writeObject(newContact);
                        output.flush();
                        System.out.println("Contact added successfully.");
                        break;

                    case "3":  // Update Existing Contact
                        System.out.println("Enter contact ID to update:");
                        int updateId = Integer.parseInt(scanner.nextLine());
                        System.out.println("Enter new name:");
                        String newName = scanner.nextLine();
                        System.out.println("Enter new address:");
                        String newAddress = scanner.nextLine();
                        System.out.println("Enter new phone:");
                        String newPhone = scanner.nextLine();
                        Contact updatedContact = new Contact(updateId, newName, newAddress, newPhone);
                        output.writeObject(updatedContact);
                        output.flush();
                        System.out.println("Contact updated successfully.");
                        break;

                    case "4":  // Delete Contact
                        System.out.println("Enter contact ID to delete:");
                        int deleteId = Integer.parseInt(scanner.nextLine());
                        output.writeObject(deleteId);
                        output.flush();
                        System.out.println("Contact deleted successfully.");
                        break;

                    case "5":  // Exit
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid option. Please select a valid option.");
                        break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
