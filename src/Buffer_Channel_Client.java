import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.Scanner;

public class Buffer_Channel_Client {
    public Buffer_Channel_Client() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String msg;

        try {
            SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 5001));

            System.out.println("Connected to Chat Server");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            msg = "name#"+name;
            HelperMethods.sendMessage(channel, msg);
            HelperMethods.receiveMessage(channel);

            System.out.println("주사위를 굴리려면 y, 끝내려면 n을 입력해 주세요");
            while (true) {
                String game = scanner.nextLine();

                if (game.equals("Y") || game.equals("y")) {
                    int diceNum = random.nextInt(1, 7);
                    System.out.println("\n주사위 숫자는 " + diceNum + "입니다.");
                    msg = name + "#" + String.valueOf(diceNum);
                    HelperMethods.sendMessage(channel, msg);
                    msg = HelperMethods.receiveMessage(channel);
                    System.out.println(msg);
                    msg = HelperMethods.receiveMessage(channel);
                    System.out.println(msg);
                }
                else if (game.equals("N") || game.equals("n")) {
                    HelperMethods.sendMessage(channel, String.valueOf(0));
                    break;
                }
                else {
                    System.out.println("주사위를 굴리려면 y, 끝내려면 n을 입력해 주세요");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Buffer_Channel_Client();
    }
}