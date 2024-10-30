package cs3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    private final String URL = "jdbc:mysql://172.31.2.32:3306/contact";
    private final String USERNAME = "contact";
    private final String PASSWORD = "K5NjXw5am4sXc7f5"; // 替换为实际密码

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                contacts.add(new Contact(id, name, address, phone));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public void addContact(Contact contact) {
        String sql = "INSERT INTO contacts (name, address, phone) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contact.getName());
            pstmt.setString(2, contact.getAddress());
            pstmt.setString(3, contact.getPhone());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateContact(int id, String newName, String newAddress, String newPhone) {
        String sql = "UPDATE contacts SET name = ?, address = ?, phone = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newName);
            pstmt.setString(2, newAddress);
            pstmt.setString(3, newPhone);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteContact(int id) {
        String sql = "DELETE FROM contacts WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
