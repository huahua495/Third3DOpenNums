package lottery.Test;

import lottery.ThirdLotteryUtils;
import lottery.shishi.SSCType;

import java.util.Scanner;

public class CheckOpenNumsTyle {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("������");
            String str = scanner.nextLine();


            SSCType type = ThirdLotteryUtils.findOpenNumsType(str);

            switch (type) {
                case GROUP_120:
                    System.out.println(str + "\t����120");
                    break;
                case GROUP_60:
                    System.out.println(str + "\t����60");
                    break;
                case GROUP_30:
                    System.out.println(str + "\t����30");
                    break;
                case GROUP_20:
                    System.out.println(str + "\t����20");
                    break;
                case GROUP_10:
                    System.out.println(str + "\t����10");
                    break;
                case GROUP_5:
                    System.out.println(str + "\t����5");
                    break;
                case GROUP_1:
                    System.out.println(str + "\t����1");
                    break;
            }
        }

    }
}
