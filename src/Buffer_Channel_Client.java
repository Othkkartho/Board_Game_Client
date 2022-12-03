import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.Scanner;

public class Buffer_Channel_Client {
    public Buffer_Channel_Client() {
        SocketAddress address = new InetSocketAddress("127.0.0.1", 5001);
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        try (SocketChannel channel = SocketChannel.open(address)) {
            System.out.println("Connected to Chat Server");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            HelperMethods.sendMessage(channel, name);

//            System.out.println("Waiting for Other Player ...");
//            System.out.println("Message: " + HelperMethods.receiveMessage(channel));

            System.out.println("주사위를 굴리려면 y, 끝내려면 n을 입력해 주세요");
            while (true) {
                String game = scanner.nextLine();

                if (game.equals("Y") || game.equals("y")) {
                    int diceNum = random.nextInt(1, 7);
                    System.out.println("\n주사위 숫자는 " + diceNum + "입니다.");
                    HelperMethods.sendMessage(channel, String.valueOf(diceNum));
                }
                else if (game.equals("N") || game.equals("n")) {
                    HelperMethods.sendMessage(channel, String.valueOf(0));
                    break;
                }
                else {
                    System.out.println("주사위를 굴리려면 y, 끝내려면 n을 입력해 주세요");
                }

                System.out.println("Waiting for Other Player ...");
                System.out.println("Message: " + HelperMethods.receiveMessage(channel));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Buffer_Channel_Client();
    }
}