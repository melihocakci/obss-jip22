package tr.com.obss.jip;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Server {
    private static ByteBuffer buffer;

    public static void main(String[] args) {
        try (ServerSocketChannel server = ServerSocketChannel.open()) {
            Selector selector = Selector.open();

            server.bind(new InetSocketAddress(3939));
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);

            Random rand = new Random();
            buffer = ByteBuffer.allocate(256);

            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();

                    if (key.isAcceptable()) {
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);

                        sendMessage(client, "Enter a number between 0-9");

                        ClientContext context = new ClientContext(rand.nextInt(10), 0);

                        client.register(selector, SelectionKey.OP_READ, context);
                    }

                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();

                        ClientContext context = (ClientContext) key.attachment();
                        int tries = context.getTries();
                        int randNum = context.getRandNum();

                        client.read(buffer);
                        buffer.flip();
                        int num = buffer.getInt();
                        buffer.clear();

                        if (num == randNum) {
                            sendMessage(client, "Congratulations! You won!");
                            client.close();
                        } else if (tries == 2) {
                            sendMessage(client, "You lost! The number was " + randNum);
                            client.close();
                        } else if (num > randNum) {
                            sendMessage(client, "Try a smaller number");
                        } else {
                            sendMessage(client, "Try a bigger number");
                        }

                        context.setTries(++tries);
                        key.attach(context);
                    }
                    iter.remove();
                }
            }
        } catch (Exception ex) {

        }
    }

    private static void sendMessage(SocketChannel client, String message) throws IOException {
        buffer.put(message.getBytes());
        buffer.flip();
        client.write(buffer);
        buffer.clear();
    }
}
