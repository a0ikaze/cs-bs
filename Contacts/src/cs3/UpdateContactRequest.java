package cs3;

import java.io.Serializable;

public class UpdateContactRequest implements Serializable {
    private final int id;
    private final String newName;
    private final String newAddress;
    private final String newPhone;

    public UpdateContactRequest(int id, String newName, String newAddress, String newPhone) {
        this.id = id;
        this.newName = newName;
        this.newAddress = newAddress;
        this.newPhone = newPhone;
    }

    public int getId() {
        return id;
    }

    public String getNewName() {
        return newName;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public String getNewPhone() {
        return newPhone;
    }
}

