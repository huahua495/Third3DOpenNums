package lottery.Test;

import lottery.shishi.Sign;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class Test {

    static String lastLine = "";

    public static void main(String[] args) {

        getLastLineContent();
        System.out.println(lastLine);


        if (null == lastLine || "".equals(lastLine) || lastLine.length() == 0) {
            return;
        }
        String[] lastLineArr = lastLine.split("\t");

        String currentDateLineNums = lastLineArr[1];
        currentDateLineNums = currentDateLineNums.substring(currentDateLineNums.length() - 2, currentDateLineNums.length());
        int currentNums = Integer.valueOf(lastLineArr[0]);
        int startDateLine = Integer.valueOf(currentDateLineNums);

        startDateLine += 1;
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("请输入");
            String str = scanner.nextLine();

            str.trim();

            if (null == str || "".equals(str) || str.length() == 0) {
                System.out.println("输入内容为空,不符合规范");
            } else {

                char[] nums = str.toCharArray();


                currentNums += 1;


                String printNums = "";

                if ((startDateLine + 1) > 59) {
                    startDateLine = 1;
                }
                if (startDateLine < 10) {
                    printNums = "00" + startDateLine;
                } else if (startDateLine < 100) {
                    printNums = "0" + startDateLine;
                }


                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                String currentDate = formatter.format(new Date());
                currentDate = currentDate + printNums;


                String result = "";
                for (int i = 0; i < nums.length; i++) {
                    result += nums[i] + ",";
                }

                result = result.substring(0, result.length() - 1);


                String resultContent = String.valueOf(currentNums) + "\t" + currentDate + "\t" + result;

                writeToTxtFile(resultContent);

                startDateLine += 1;
            }
        }
    }

    /**
     * 将内容添加到文件末尾
     *
     * @param resultContent
     */
    private static void writeToTxtFile(String resultContent) {
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(Sign.SHISHI_PATH, true);
            writer.write(resultContent + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getLastLineContent() {
        RandomAccessFile rf = null;
        try {
            rf = new RandomAccessFile(Sign.SHISHI_PATH, "r");
            long len = rf.length();
            long start = rf.getFilePointer();
            long nextend = start + len - 1;
            String line = "";
            rf.seek(nextend);
            int c = -1;
            int t = 0;
            while (nextend > start) {
                c = rf.read();
                if (c == '\n' || c == '\r') {
                    line = rf.readLine();
                    t++;
                    if (t >= 1 && line != null) {
                        lastLine = line;

                        System.out.println(line + "       最后一行的内容");
                        return;
                    }
                    nextend--;
                }
                nextend--;
                rf.seek(nextend);
                if (nextend == 0) {// 当文件指针退至文件开始处，输出第一行
                    System.out.println(rf.readLine() + "      文件指针退至文件开始处，输出第一行");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rf != null)
                    rf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
