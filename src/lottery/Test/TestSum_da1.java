package lottery.Test;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TestSum_da1 {

//    private static String str = "4,60,60,18,94,68,92,81,37,94,6";


    public static boolean isEmpty(String... strings) {
        for (String str : strings) {
            if (str == null || str.trim().length() == 0)
                return true;
        }
        return false;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("��������Ҫ��ʽ�����ַ���");
            String str = scanner.nextLine();

            HashMap<String, Integer> map = new HashMap<>();
            ArrayList<Character> list = new ArrayList<>();
            char[] chars = str.toCharArray();//���ַ���ת�����ַ�����
            for (int i = 0; i < str.length(); i++) {
                char aChar = chars[i];

                if (!isEmpty(String.valueOf(aChar))
                        && !",".equals(String.valueOf(aChar))
                ) {
                    list.add(aChar);//���ַ�����Ԫ����ӵ�������
                    map.put(String.valueOf(aChar), 0);
                }
            }


            ArrayList<String> keyList = new ArrayList<>();
            /**
             * ���Ȼ�ȡKEY ����
             */
            Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Integer> entry = it.next();
                keyList.add(entry.getKey());
            }

            /**
             * ���ȱ���ѭ��ȥ��key
             */

            for (int i = 0; i < keyList.size(); i++) {
                int count = 0;
                String parentKey = keyList.get(i);

                for (int j = 0; j < list.size(); j++) {
                    Character childKey = list.get(j);
                    if (parentKey.equals(String.valueOf(childKey))) {
                        count += 1;
                    }
                }
                map.put(keyList.get(i), count);
            }


          /*  Iterator<Map.Entry<String, Integer>> it1 = map.entrySet().iterator();
            while (it1.hasNext()) {
                Map.Entry<String, Integer> entry = it1.next();
                System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            }*/


            List<Map.Entry<String, Integer>> entryList = new ArrayList<>(map.entrySet());
            Collections.sort(entryList, new MyComparator());


            System.out.println("����2");
            for (Map.Entry<String, Integer> entry : entryList) {
                int value = entry.getValue();
                if (value > 2) {
                    System.out.print(entry.getKey());
                }

            }
            System.out.println("");

            System.out.println("����1");
            for (Map.Entry<String, Integer> entry : entryList) {
                int value = entry.getValue();
                if (value > 1) {
                    System.out.print(entry.getKey() + "-" + value + "   ");
//                    System.out.print(entry.getKey());
                }
            }
            System.out.println("");
        }



    }


    static class MyComparator implements Comparator<Map.Entry> {
        @Override
        public int compare(Map.Entry o1, Map.Entry o2) {
            return ((Integer) o2.getValue()).compareTo((Integer) o1.getValue());
        }
    }


}
