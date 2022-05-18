package serverStuff;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ObjectSender {
    public ObjectSender(Transfer transfer) {
        this.transfer = transfer;
    }

    private Transfer transfer;

    public void send(DatagramChannel datagramChannel, InetSocketAddress address){
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1048576);
            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            ObjectOutputStream objectOS = new ObjectOutputStream(byteArrayOS);

            objectOS.writeObject(this.transfer);
            objectOS.flush();

            buffer.put(byteArrayOS.toByteArray());
            buffer.flip();
            datagramChannel.send(buffer, address);

            byteArrayOS.close();
            objectOS.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
