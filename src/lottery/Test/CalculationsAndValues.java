package lottery.Test;

import java.util.*;

/**
 * �����ֵ
 */
public class CalculationsAndValues {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        while (true) {
            Set<Integer> result = new TreeSet<>();//���˽��

            System.out.println("��������Ҫ��ʽ�����ַ���");
            String str = scanner.nextLine();


            if (isEmpty(str)) {
                continue;
            }
            str = str.replace(" ", "-");

            String[] calculation = str.split("-");


            for (String s : calculation) {
                result.add(numsTotal(Integer.valueOf(s)));
            }


            for (Integer integer : result) {
                System.out.print(integer + ",");
            }
        }
    }

    public static Integer numsTotal(Integer nums) {
        int sum = 0;

        while (nums != 0) {
            sum = sum + nums % 10;
            nums = nums / 10;
        }
        return sum;
    }


    public static boolean isEmpty(String... strings) {
        for (String str : strings) {
            if (str == null || str.trim().length() == 0)
                return true;
        }
        return false;
    }
}
