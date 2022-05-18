package serverStuff;

import mainPart.SpaceMarine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

public class ObjectReceiver {
    public ObjectReceiver(ByteBuffer byteBuffer){
        this.byteBuffer = byteBuffer;
    }

    private ByteBuffer byteBuffer;

    public Transfer unpack(){
        try {
            ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(this.byteBuffer.array());
            ObjectInputStream objectIS = new ObjectInputStream(byteArrayIS);

            Transfer transfer = (Transfer) objectIS.readObject();

            objectIS.close();
            byteArrayIS.close();

            return transfer;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
