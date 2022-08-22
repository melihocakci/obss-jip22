package tr.com.obss.jip;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        try (SocketChannel channel = SocketChannel.open()) {
            ByteBuffer buffer = ByteBuffer.allocate(200);

            Scanner scanner = new Scanner(System.in);

            channel.connect(new InetSocketAddress("127.0.0.1", 3939));

            channel.read(buffer);
            System.out.println(new String(buffer.array(), 0, buffer.position()));
            buffer.clear();

            while (channel.isConnected()) {
                int num = scanner.nextInt();
                buffer.putInt(num);
                buffer.flip();
                channel.write(buffer);
                buffer.clear();

                channel.read(buffer);
                System.out.println(new String(buffer.array(), 0, buffer.position()));
                buffer.clear();
            }
        } catch (Exception ex) {

        }
    }
}
