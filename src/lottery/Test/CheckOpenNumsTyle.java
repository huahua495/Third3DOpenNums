package lottery.Test;

import lottery.ThirdLotteryUtils;
import lottery.shishi.SSCType;

import java.util.Scanner;

public class CheckOpenNumsTyle {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("请输入");
            String str = scanner.nextLine();


            SSCType type = ThirdLotteryUtils.findOpenNumsType(str);

            switch (type) {
                case GROUP_120:
                    System.out.println(str + "\t是组120");
                    break;
                case GROUP_60:
                    System.out.println(str + "\t是组60");
                    break;
                case GROUP_30:
                    System.out.println(str + "\t是组30");
                    break;
                case GROUP_20:
                    System.out.println(str + "\t是组20");
                    break;
                case GROUP_10:
                    System.out.println(str + "\t是组10");
                    break;
                case GROUP_5:
                    System.out.println(str + "\t是组5");
                    break;
                case GROUP_1:
                    System.out.println(str + "\t是组1");
                    break;
            }
        }

    }
}
