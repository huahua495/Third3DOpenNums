package lottery.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * ɾ��β��
 */
public class RemoveTail {
    public static void main(String[] args) {

        /**
         * ����β��
         */
        ArrayList<String> tail = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            tail.add(String.valueOf(i));
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            ArrayList<String> result = new ArrayList<>();//���˽��
            System.out.println("��������Ҫ��ʽ�����ַ���");
            String str = scanner.nextLine();

            Set<String> setTail = new HashSet<>();


            char[] chars = str.toCharArray();//���ַ���ת�����ַ�����
            for (int i = 0; i < str.length(); i++) {
                char aChar = chars[i];

                if (!isEmpty(String.valueOf(aChar))
                        && !",".equals(String.valueOf(aChar))
                ) {
                    setTail.add(String.valueOf(aChar));
                }
            }


            for (int i = 0; i < tail.size(); i++) {
                if (!setTail.contains(tail.get(i))) {
                    result.add(tail.get(i));
                }
            }


            for (String s : result) {
                String sout = s + ",";
                sout = sout.substring(0, sout.length() - 1);
                System.out.print(sout);
            }

            System.out.println("");
        }
    }


    public static boolean isEmpty(String... strings) {
        for (String str : strings) {
            if (str == null || str.trim().length() == 0)
                return true;
        }
        return false;
    }
}
