package cs3;

import java.io.Serializable;

public class DeleteContactRequest implements Serializable {
    private final int id;

    public DeleteContactRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
