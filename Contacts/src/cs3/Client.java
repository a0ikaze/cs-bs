package cs3;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1"; // 服务器地址
    private static final int SERVER_PORT = 12345; // 服务器端口

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("Select an option: 1. View All 2. Add 3. Update 4. Delete 5. Exit");
                int option = scanner.nextInt();
                out.writeObject(option);
                out.flush();

                switch (option) {
                    case 1:
                        // 查看所有联系人
                        System.out.println("Contacts:");
                        Object[] contacts = (Object[]) in.readObject();
                        for (Object contact : contacts) {
                            System.out.println(contact);
                        }
                        break;

                    case 2:
                        // 添加联系人
                        System.out.print("Enter name: ");
                        String name = scanner.next();
                        System.out.print("Enter address: ");
                        String address = scanner.next();
                        System.out.print("Enter phone: ");
                        String phone = scanner.next();
                        out.writeObject(new Contact(name, address, phone));
                        break;

                    case 3:
                        // 更新联系人
                        System.out.print("Enter contact ID to update: ");
                        int idToUpdate = scanner.nextInt();
                        System.out.print("Enter new name: ");
                        String newName = scanner.next();
                        System.out.print("Enter new address: ");
                        String newAddress = scanner.next();
                        System.out.print("Enter new phone: ");
                        String newPhone = scanner.next();
                        out.writeObject(new UpdateContactRequest(idToUpdate, newName, newAddress, newPhone));
                        break;

                    case 4:
                        // 删除联系人
                        System.out.print("Enter contact ID to delete: ");
                        int idToDelete = scanner.nextInt();
                        out.writeObject(new DeleteContactRequest(idToDelete));
                        break;

                    case 5:
                        // 退出
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid option.");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

