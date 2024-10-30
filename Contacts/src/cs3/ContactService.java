package cs3;

import java.util.List;

public class ContactService {
    private final ContactDAO contactDAO;

    public ContactService() {
        contactDAO = new ContactDAO();
    }

    public List<Contact> getAllContacts() {
        return contactDAO.getAllContacts();
    }

    public void addContact(Contact contact) {
        contactDAO.addContact(contact);
    }

    public void updateContact(UpdateContactRequest request) {
        contactDAO.updateContact(request.getId(), request.getNewName(), request.getNewAddress(), request.getNewPhone());
    }

    public void deleteContact(int id) {
        contactDAO.deleteContact(id);
    }
}
