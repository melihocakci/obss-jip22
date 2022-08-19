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
            channel.bind(new InetSocketAddress(3939));

            ByteBuffer buffer = ByteBuffer.allocate(200);
            Random rand = new Random();

            SocketChannel client = channel.accept();

            int randNum = rand.nextInt(9);

            client.write(ByteBuffer.wrap("Enter a number between 0-9".getBytes()));

            for (int i = 0; i < 3; i++) {
                client.read(buffer);

                int num = Integer.parseInt(new String(buffer.array()).trim());
                buffer.clear();

                if (num > randNum) {
                    client.write(ByteBuffer.wrap("Try smaller".getBytes()));
                } else if (num < randNum) {
                    client.write(ByteBuffer.wrap("Try bigger".getBytes()));
                } else {
                    client.write(ByteBuffer.wrap("Congratulations!".getBytes()));
                    return;
                }
            }

            client.write(ByteBuffer.wrap("You lost!".getBytes()));
        }
    }
}
