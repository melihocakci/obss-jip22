package tr.com.obss.jip;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Random;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocketChannel channel = ServerSocketChannel.open()) {
            channel.socket().bind(new InetSocketAddress(3939));

            ByteBuffer buffer = ByteBuffer.allocate(200);
            Random rand = new Random();

            SocketChannel client = channel.accept();

            int randNum = rand.nextInt(10);

            buffer.put("Enter a number between 0-9".getBytes());
            buffer.flip();
            client.write(buffer);
            buffer.clear();

            int i = 0, num;
            while (true) {
                client.read(buffer);
                buffer.flip();
                num = buffer.getInt();
                buffer.clear();

                System.out.println(num);

                if (i++ == 2 || num == randNum) {
                    break;
                } else if (num > randNum) {
                    buffer.put("Try smaller".getBytes());
                } else {
                    buffer.put("Try bigger".getBytes());
                }

                buffer.flip();
                client.write(buffer);
                buffer.clear();
            }

            if (num == randNum) {
                buffer.put("Congratulations!".getBytes());
            } else {
                buffer.put(("You lost! The number was " + randNum).getBytes());
            }

            buffer.flip();
            client.write(buffer);
        } catch (Exception ex) {

        }
    }
}
