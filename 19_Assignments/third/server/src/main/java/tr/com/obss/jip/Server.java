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
    private static Selector selector;
    private static ServerSocketChannel server;
    private static Random rand;

    public static void main(String[] args) {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            server = serverSocketChannel;
            selector = Selector.open();

            server.bind(new InetSocketAddress(3939));
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);

            rand = new Random();
            buffer = ByteBuffer.allocate(4);

            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();

                    try {
                        if (key.isAcceptable()) {
                            accept();
                        }

                        if (key.isReadable()) {
                            read(key);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    iter.remove();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void accept() throws IOException {
        SocketChannel client = null;
        try {
            client = server.accept();
            client.configureBlocking(false);

            ClientContext context = new ClientContext(rand.nextInt(10), 0);

            client.register(selector, SelectionKey.OP_READ, context);
        } catch (Exception ex) {
            ex.printStackTrace();
            if (client != null) {
                client.close();
            }
        }
    }

    private static void read(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        try {
            ClientContext context = (ClientContext) key.attachment();
            int tries = context.getTries();
            int randNum = context.getRandNum();

            int num = receive(client);

            if (num == randNum) {
                respond(client, Status.CORRECT);
                client.close();
            } else if (tries == 2) {
                respond(client, Status.FAIL);
                client.close();
            } else if (num > randNum) {
                respond(client, Status.SMALLER);
            } else {
                respond(client, Status.BIGGER);
            }

            context.setTries(++tries);
            key.attach(context);
        } catch (Exception ex) {
            ex.printStackTrace();
            client.close();
        }
    }

    private static void respond(SocketChannel client, Status status) throws IOException {
        buffer.putInt(status.value());
        buffer.flip();
        client.write(buffer);
        buffer.clear();
    }

    private static int receive(SocketChannel client) throws IOException {
        client.read(buffer);
        buffer.flip();
        int num = buffer.getInt();
        buffer.clear();
        return num;
    }
}
