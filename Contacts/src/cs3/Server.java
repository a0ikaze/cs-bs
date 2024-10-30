package cs3;

import java.io.*;
import java.net.*;
import java.util.List;

public class Server {
    private static final int PORT = 12345;
    private static final ContactService contactService = new ContactService();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

                while (true) {
                    int option = (int) in.readObject();
                    switch (option) {
                        case 1: // 查看所有联系人
                            List<Contact> contacts = contactService.getAllContacts();
                            out.writeObject(contacts.toArray(new Contact[0]));
                            out.flush();
                            break;

                        case 2: // 添加联系人
                            Contact newContact = (Contact) in.readObject();
                            contactService.addContact(newContact);
                            break;

                        case 3: // 更新联系人
                            UpdateContactRequest updateRequest = (UpdateContactRequest) in.readObject();
                            contactService.updateContact(updateRequest);
                            break;

                        case 4: // 删除联系人
                            DeleteContactRequest deleteRequest = (DeleteContactRequest) in.readObject();
                            contactService.deleteContact(deleteRequest.getId());
                            break;

                        case 5: // 退出
                            System.out.println("Client disconnected.");
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
}
