import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.security.SecureRandom;
import java.util.*;

public class Buffer_Channel_Client {
    public Buffer_Channel_Client() {
        Scanner scanner = new Scanner(System.in);
        SecureRandom random = new SecureRandom();
        String msg;
        String[] msgs;
        int diceNum;

        try {
            Selector selector = Selector.open();
            SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 5001));

            TimerTask task = new TimerTask() {
                public void run() {
                    System.out.println("open");
                    HelperMethods.sendMessage(channel, "open#null");
                }
            };

            System.out.println("Connected to Chat Server");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            msg = "name#"+name;
            HelperMethods.sendMessage(channel, msg);

            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);

            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();

                while (it.hasNext()) {
                    SelectionKey key = it.next();

                    if (key.isReadable()) {
                        msg = HelperMethods.receiveMessage(channel);
                        System.out.println(msg);
                    }

                    if ("host".equals(msg)) {
                        Timer timer = new Timer("Timer");
                        long delay = 20000L;
                        timer.schedule(task, delay);
                    }

                    if ("Your Turn".equals(msg)) {
                        while (true) {
                            System.out.println("주사위를 굴리려면 y, 게임을 끝내려면 n을 입력해 주세요");
                            String game = scanner.nextLine();
                            if (game.equals("Y") || game.equals("y")) {
                                diceNum = random.nextInt(1, 7);
                                System.out.println("\n주사위 숫자는 " + diceNum + "입니다.");
                                msg = name + "#" + diceNum;
                                HelperMethods.sendMessage(channel, msg);
                                break;
                            } else if (game.equals("N") || game.equals("n")) {
                                HelperMethods.sendMessage(channel, "tony#quit");
                                msg = "quit";
                                break;
                            }
                        }
                        if (msg.equals("quit"))
                            break;
                    }

                    if ("win".equals(msg)) {
                        msg = "quit";
                        System.out.println("이겼습니다.");
                    } else if ("lose".equals(msg)) {
                        msg = "quit";
                        System.out.println("졌습니다.");
                    }
                    it.remove();
                }
                if (msg.equals("quit"))
                    System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Buffer_Channel_Client();
    }
}