import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class StreamClient {
    final static int ServerPort = 3005;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        InetAddress ip = InetAddress.getByName("localhost");
        Random random = new Random();

        Socket socket = new Socket(ip, ServerPort);
        System.out.println("Client is connected to the chat server");

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        System.out.print("Name: ");
        String name = sc.nextLine();
        dos.writeUTF(name);

        Thread sendMessage = new Thread(() -> {
            System.out.println("주사위를 굴리려면 y, 끝내려면 n을 입력해 주세요");
//            int diceNum = 2;
            while (true) {
                String game = sc.nextLine();

                if (game.equals("Y") || game.equals("y")) {
                    int diceNum = random.nextInt(1, 7);
//                    diceNum += 1;
                    System.out.println("\n주사위 숫자는 " + diceNum + "입니다.");
                    try {
                        dos.write(diceNum);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (game.equals("N") || game.equals("n")) {
                    break;
                }
                else {
                    System.out.println("주사위를 굴리려면 y, 끝내려면 n을 입력해 주세요");
                }
            }

            try {
                dis.close();
                dos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread readMessage = new Thread(() -> {
            while (true) {
                try {
                    String msg = dis.readUTF();
                    if (msg.equals("win")) {
                        System.out.println("게임에서 승리하였습니다.\n접속을 종료합니다.");
                        break;
                    }
                    else if (msg.equals("lose")) {
                        System.out.println("게임에서 졌습니다.\n접속을 종료합니다.");
                        break;
                    }
                    System.out.println(msg + "\n");
                } catch (IOException e1) {
                    try {
                        socket.close();
                        dis.close();
                        dos.close();
                        break;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    e1.printStackTrace();
                }
            }
            try {
                socket.close();
                dis.close();
                dos.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });

        sendMessage.start();
        readMessage.start();
    }
}