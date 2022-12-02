import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Buffer_Channel_Client {
    public Buffer_Channel_Client() {
        SocketAddress address = new InetSocketAddress("127.0.0.1", 5001);

        try (SocketChannel channel = SocketChannel.open(address)) {
            System.out.println("Connected to Chat Server");
            String message;
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Waiting for message from the server ...");
                System.out.println("Message: " + HelperMethods.receiveMessage(channel));
                System.out.print("> ");
                message = scanner.nextLine();
                if (message.equalsIgnoreCase("quit")) {
                    HelperMethods.sendMessage(channel, "Client terminating");
                    break;
                }
                HelperMethods.sendMessage(channel, message);;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Buffer_Channel_Client();
    }
}