package cs2;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Connection connection;

    public ClientHandler(Socket socket) throws SQLException {
        this.socket = socket;
        this.connection = DatabaseConnection.getConnection(); // 获取数据库连接
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {

            String option;
            while ((option = (String) input.readObject()) != null) {
                switch (option) {
                    case "1": // View All Contacts
                        List<Contact> contacts = getAllContacts();
                        output.writeObject(contacts);
                        output.flush();
                        break;

                    case "2": // Add New Contact
                        Contact newContact = (Contact) input.readObject();
                        addContact(newContact);
                        break;

                    case "3": // Update Existing Contact
                        Contact updatedContact = (Contact) input.readObject();
                        updateContact(updatedContact);
                        break;

                    case "4": // Delete Contact
                        int deleteId = (Integer) input.readObject();
                        deleteContact(deleteId);
                        break;

                    case "5": // Exit
                        System.out.println("Client disconnected.");
                        return;

                    default:
                        System.out.println("Invalid option received from client.");
                        break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close(); // 关闭数据库连接
                }
                socket.close(); // 关闭客户端连接
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                contacts.add(new Contact(id, name, address, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    private void addContact(Contact contact) {
        String sql = "INSERT INTO contacts (name, address, phone) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getAddress());
            statement.setString(3, contact.getPhone());
            statement.executeUpdate();
            System.out.println("Contact added: " + contact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateContact(Contact contact) {
        String sql = "UPDATE contacts SET name = ?, address = ?, phone = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getAddress());
            statement.setString(3, contact.getPhone());
            statement.setInt(4, contact.getId());
            statement.executeUpdate();
            System.out.println("Contact updated: " + contact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteContact(int id) {
        String sql = "DELETE FROM contacts WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Contact deleted with ID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
