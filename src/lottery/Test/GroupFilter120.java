package lottery.Test;

import lottery.ThirdLotteryUtils;
import lottery.shishi.SSCType;

import java.util.LinkedHashSet;

public class GroupFilter120 {
    public static void main(String[] args) {

        LinkedHashSet<String> list = ThirdLotteryUtils.findAllCombinationNumsSSCDirectelection(SSCType.GROUP_120);

        System.out.println(list.size());

        System.out.println("-------------");

        for (String s : list) {
            if (s.contains("0") && !s.contains("3") && !s.contains("6") && !s.contains("9")) {
                System.out.print(s + ",");
            }
        }

    }
}
