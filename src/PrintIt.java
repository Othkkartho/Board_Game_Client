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

    public static void check(String[] msgs, SocketChannel channel) {
        if (msgs[1] != null) {
            switch (msgs[0]) {
                case "jump":
                    System.out.println(msgs[2] + "칸을 점프해 " + msgs[1] + "칸에 도착했습니다.");
                    break;
                case "back":
                    System.out.println(msgs[2] + "칸 뒤로가 " + msgs[1] + "칸에 도착했습니다.");
                    break;
                case "skip":
                    System.out.println(msgs[1] + "칸에 도착했지만 무인도에 도착해 한 턴 휴식합니다.");
                    break;
                case "skip2":
                    System.out.println("다음턴에 이동할 수 있습니다.");
                    break;
                case "catch":
                    System.out.println("상대 말을 잡았습니다.");
                    switch (msgs[1]) {
                        case "jump" -> System.out.println(msgs[3] + "칸을 점프해 " + msgs[2] + "칸에 도착했습니다.");
                        case "back" -> System.out.println(msgs[3] + "칸 뒤로가 " + msgs[2] + "칸에 도착했습니다.");
                        case "skip" -> System.out.println(msgs[2] + "칸에 도착했지만 무인도에 도착해 한 턴 휴식합니다.");
                        default -> System.out.println(msgs[1] + "칸에 도착했습니다.");
                    }
                    break;
                case "go":
                    System.out.println(msgs[1] + "칸에 도착했습니다.");
                    break;
                case "host":
                    break;
                case "win":
                    break;
                case "lose":
                    break;
                default:
                    switch (msgs[1]) {
                        case "jump" ->
                                System.out.println(msgs[0] + "이/가 " + msgs[3] + "칸을 점프해 " + msgs[2] + "칸에 도착했습니다.");
                        case "back" ->
                                System.out.println(msgs[0] + "이/가 " + msgs[3] + "칸을 뒤로가 " + msgs[2] + "칸에 도착했습니다.");
                        case "catch" ->
                                System.out.println(msgs[0] + "이/가 당신을 잡아 처음으로 돌아갑니다.");
                        case "skip" -> System.out.println(msgs[0] + "이/가 " + msgs[2] + "칸에 도착했지만 무인도에 도착해 한 턴 휴식합니다.");
                        case "skip2" -> System.out.println(msgs[0] + "은/는 다음턴에 이동할 수 있습니다.");
                        default -> catcher(msgs);
                    }
                    break;
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
}
