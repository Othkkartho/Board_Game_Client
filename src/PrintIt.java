import java.nio.channels.SocketChannel;
import java.util.StringTokenizer;

public class PrintIt {
    public static String[] receive(SocketChannel channel) {
        String msg = HelperMethods.receiveMessage(channel);

        StringTokenizer tokenizer = new StringTokenizer(msg, "#");
        String what = null, data1 = null, data2 = null, data3 = null, data4 = null;
        try {
            what = tokenizer.nextToken();
            data1 = tokenizer.nextToken();
            data2 = tokenizer.nextToken();
            data3 = tokenizer.nextToken();
            data4 = tokenizer.nextToken();
        } catch (Exception ignored) {}

        String[] msgs = {what, data1, data2, data3, data4};

        return msgs;
    }

    public static void check(String[] msgs, int diceNum, SocketChannel channel) {
        if (msgs[1] != null) {
            if (msgs[0].equals("jump")) {
                System.out.println(diceNum + "칸을 점프해 " + msgs[1] + "칸에 도착했습니다.");
            } else if (msgs[0].equals("back")) {
                System.out.println(diceNum + "칸 뒤로가 " + msgs[1] + "칸에 도착했습니다.");
            } else if (msgs[0].equals("skip")) {
                System.out.println(msgs[1] + "칸에 도착했지만 무인도에 도착해 한 턴 휴식합니다.");
            } else if (msgs[0].equals("skip2")) {
                System.out.println("다음턴에 이동할 수 있습니다.");
            } else if (msgs[0].equals("catch")) {
                System.out.println("상대 말을 잡았습니다.");
                if (msgs[1].equals("jump")) {
                    System.out.println(diceNum + "칸을 점프해 " + msgs[2] + "칸에 도착했습니다.");
                } else if (msgs[1].equals("back")) {
                    System.out.println(diceNum + "칸 뒤로가 " + msgs[2] + "칸에 도착했습니다.");
                } else if (msgs[1].equals("skip")) {
                    System.out.println(msgs[2] + "칸에 도착했지만 무인도에 도착해 한 턴 휴식합니다.");
                } else {
                    System.out.println(msgs[1] + "칸에 도착했습니다.");
                }
            } else if (msgs[0].equals("go")) {
                System.out.println(msgs[1] + "칸에 도착했습니다.");
            } else {
                if (msgs[1].equals("jump")) {
                    System.out.println(msgs[0] + "이/가 " + msgs[3] + "칸을 점프해 " + msgs[2] + "칸에 도착했습니다.");
                } else if (msgs[1].equals("back")) {
                    System.out.println(msgs[0] + "이/가 " + msgs[3] + "칸을 점프해 " + msgs[2] + "칸에 도착했습니다.");
                } else if (msgs[1].equals("skip")) {
                    System.out.println(msgs[0] + "이/가 " + msgs[3] + "칸을 점프해 " + msgs[2] + "칸에 도착했습니다.");
                } else {
                    catcher(msgs);
                }
            }
        } else {
            System.out.println(msgs[0]);
        }
    }

    private static void catcher(String[] msgs) {
        if (msgs[1].equals("catch")) {
            System.out.println("말이 잡혀 처음으로 이동했습니다.");
            if (msgs[2].equals("jump")) {
                System.out.println(msgs[0] + "이/가 " + msgs[4] + "칸을 점프해 " + msgs[3] + "칸에 도착했습니다.");
            } else if (msgs[2].equals("back")) {
                System.out.println(msgs[0] + "이/가 " + msgs[4] + "칸 뒤로가 " + msgs[3] + "칸에 도착했습니다.");
            } else if (msgs[2].equals("skip")) {
                System.out.println(msgs[0] + "이/가 " + msgs[3] + "칸에 도착했지만 무인도에 도착해 한 턴 휴식합니다.");
            } else {
                System.out.println(msgs[0] + "이/가 " + msgs[2] + "칸에 도착했습니다.");
            }
        } else {
            System.out.println(msgs[0] + "이 " + msgs[3] + "칸을 이동해 " + msgs[2] + "칸에 도착했습니다.");
        }
    }

    public static void check(String[] msgs, SocketChannel channel) {
        if (msgs[1] != null) {
            if (msgs[1].equals("jump")) {
                System.out.println(msgs[0] + "이/가 " + msgs[2] + "칸을 점프해 " + msgs[2] + "칸에 도착했습니다.");
            } else if (msgs[1].equals("back")) {
                System.out.println(msgs[0] + "이/가 " + msgs[2] + "칸을 점프해 " + msgs[2] + "칸에 도착했습니다.");
            } else if (msgs[1].equals("skip")) {
                System.out.println(msgs[0] + "이/가 " + msgs[2] + "칸을 점프해 " + msgs[2] + "칸에 도착했습니다.");
            } else catcher(msgs);
        } else {
            System.out.println(msgs[0]);
        }
    }
}
