package common;

import java.util.Scanner;

public class ReaderUtil {

    private static final Scanner sc = new Scanner(System.in);

    private ReaderUtil() {}

    public static String read() {
        return sc.nextLine();
    }

    public static int getValidOption(int count) {
        while (true) {
            try {
                System.out.print(">> ");
                int pick = Integer.parseInt(ReaderUtil.read());
                if (pick <= 0 || pick > count) {
                    System.out.println("입력 값이 보기 범위를 벗어났습니다. 다시 시도하세요.");
                    continue;
                }
                return pick;
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }
}
