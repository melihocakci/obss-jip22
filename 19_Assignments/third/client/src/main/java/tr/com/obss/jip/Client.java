package tr.com.obss.jip;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (SocketChannel channel = SocketChannel.open()) {
            ByteBuffer buffer = ByteBuffer.allocate(200);

            Scanner scanner = new Scanner(System.in);

            channel.connect(new InetSocketAddress("127.0.0.1", 3939));

            System.out.println("Enter a number between 0-9");

            while (channel.isConnected()) {
                int num = scanner.nextInt();
                buffer.putInt(num);
                buffer.flip();
                channel.write(buffer);
                buffer.clear();

                channel.read(buffer);
                buffer.flip();
                int res = buffer.getInt();
                buffer.clear();

                if (res == Status.CORRECT.value()) {
                    System.out.println("Congratulations! You won!");
                    return;
                } else if (res == Status.SMALLER.value()) {
                    System.out.println("Try a smaller number");
                } else if (res == Status.BIGGER.value()) {
                    System.out.println("Try a bigger number");
                } else {
                    System.out.println("You used all your chances! Game over!");
                    return;
                }
            }
        } catch (Exception ex) {

        }
    }
}
