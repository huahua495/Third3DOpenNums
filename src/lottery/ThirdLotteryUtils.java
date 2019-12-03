package lottery;

import lottery.shishi.SSCType;
import lottery.third3d.Third3Type;

import javax.print.DocFlavor;
import java.util.*;

public class ThirdLotteryUtils {

    public static LinkedHashSet<String> findAllCombinationNums() {
//       ArrayList<String> allNums = new ArrayList<>();


        LinkedHashSet<String> linkedNums = new LinkedHashSet<>();

        for (int i = 0; i < 10; i++) {
            String bai = String.valueOf(i);
            for (int j = 0; j < 10; j++) {
                String shi = String.valueOf(j);
                for (int k = 0; k < 10; k++) {
                    String ge = String.valueOf(k);
                    String result = bai + shi + ge;
                    linkedNums.add(sortString(result));
                }
            }
        }

        return linkedNums;
    }


    public static LinkedHashSet<String> findAllCombinationSSCNums() {
//       ArrayList<String> allNums = new ArrayList<>();


        LinkedHashSet<String> linkedNums = new LinkedHashSet<>();

        for (int i = 0; i < 10; i++) {
            String WAN = String.valueOf(i);
            for (int j = 0; j < 10; j++) {
                String QIAN = String.valueOf(j);
                for (int k = 0; k < 10; k++) {
                    String BAI = String.valueOf(k);
                    for (int l = 0; l < 10; l++) {
                        String SHI = String.valueOf(l);
                        for (int m = 0; m < 10; m++) {
                            String result = WAN + QIAN + BAI + SHI + String.valueOf(i);
                            linkedNums.add(sortString(result));
                        }
                    }
                }
            }
        }

        return linkedNums;
    }


    public static String sortString(String result) {
        ArrayList<String> sortList = new ArrayList<>();
        for (int i = 0; i < result.length(); i++) {
            sortList.add(String.valueOf(result.charAt(i)));
        }
        sortList.sort(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return Integer.valueOf(s) - Integer.valueOf(t1);
            }
        });
        String data = "";
        for (int i = 0; i < sortList.size(); i++) {
            data += sortList.get(i);
        }
        return data;
    }

    /**
     * 判断是组六
     * 组三
     * 豹子
     *
     * @return
     */
    public static Third3Type FindThirdNumsType(String openNums) {
        Set<String> set = new HashSet<>();

        char[] allNums = openNums.toCharArray();
        for (int i = 0; i < allNums.length; i++) {
            set.add(String.valueOf(allNums[i]));
        }
        if (set.size() == 3) {
            return Third3Type.GROUP_SIX;
        } else if (set.size() == 1) {
            return Third3Type.GROUP_LEOPARD;
        } else {
            return Third3Type.GROUP_THREE;
        }
    }


    /**
     * 判断是否是
     * 组选120
     * 组选60
     * 组选30
     * 组选10
     * 组选5
     *
     * @return
     */
    public static SSCType findOpenNumsType(String openNums) {
        Set<String> set = new HashSet<>();

        char[] allNums = openNums.toCharArray();
        for (int i = 0; i < allNums.length; i++) {
            set.add(String.valueOf(allNums[i]));
        }

        if (set.size() == 5) {
            return SSCType.GROUP_120;
        } else if (set.size() == 4) {// 1. 2重号+3个号码
            return SSCType.GROUP_60;
        } else if (set.size() == 3) {
            /**
             * 需要判断是组三十还是组二十
             *
             *  3+1+1   或者  2+2+1
             */


            ArrayList<Integer> mList = findNumberCount(set, openNums);

            if (mList.get(0) == 2 && mList.get(1) == 2 && mList.get(2) == 1) {
                return SSCType.GROUP_30;
            } else {
                return SSCType.GROUP_20;
            }
        } else if (set.size() == 2) {
            /**
             * 需要判断是组十还是组5
             * 3+2+2    或者  4+1
             */
            ArrayList<Integer> mList = findNumberCount(set, openNums);

            if (mList.get(0) == 4) {
                return SSCType.GROUP_5;
            } else {
                return SSCType.GROUP_10;
            }
        } else {
            return SSCType.GROUP_1;
        }
    }

    /**
     * 判断一个字符在一个字符串中出现的次数
     *
     * @param str
     * @param subStr
     * @return
     */
    private static int getCount(String subStr, String str) {
        int result = 0;
        while (str.contains(subStr)) {
            str = str.substring(str.indexOf(subStr) + subStr.length(), str.length());
            result += 1;
        }
        return result;
    }

    /**
     * 获取每个数字在开奖号码出现的次数，并从大到小排列
     *
     * @param set
     * @param openNums
     * @return
     */
    private static ArrayList<Integer> findNumberCount(Set<String> set, String openNums) {
        ArrayList<Integer> mList = new ArrayList<>();
        for (String str : set) {
            mList.add(getCount(str, openNums));
        }
        mList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer t1, Integer t2) {
                return t2 - t1;
            }
        });
        return mList;
    }


}
