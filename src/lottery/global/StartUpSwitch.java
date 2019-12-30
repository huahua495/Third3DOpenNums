package lottery.global;

import lottery.ThirdLotteryUtils;
import lottery.other.HeiLoingJiangLottery;
import lottery.other.TianJinLottery;
import lottery.other.XinJiangLottery;
import lottery.shishi.HistorySSCLottery;
import lottery.shishi.Sign;

import java.io.*;

public class StartUpSwitch {

    public static void main(String[] args) {
        readDataToShishiFile(Sign.SHISHI_DATA_PATH, Sign.SHISHI_PATH);
        readDataToShishiFile(Sign.TIANJIN_DATA_PATH, Sign.TIAN_JIN_PATH);
        readDataToShishiFile(Sign.XINJIANG_DATA_PATH, Sign.XIN_JIANG_PATH);
        readDataToShishiFile(Sign.HEI_LONG_JIANG_DATA_PATH, Sign.HEILONG_JIANG_PATH);

        HistorySSCLottery.createShishiFile();
        TianJinLottery.createTianJinFile();
        XinJiangLottery.createXinJiangFile();
        HeiLoingJiangLottery.createHLJgFile();
    }

    /**
     * 读取data文件，然后写入到对应的目标文件中
     *
     * @param dataPath       data文件path
     * @param targetFilePath 目标文件path
     */
    private static void readDataToShishiFile(String dataPath, String targetFilePath) {
        String lastLine = getLastLineContent(targetFilePath);
        System.out.println(lastLine);

        /**
         * 循环读取data 文件的内容，如果某一行包含此内容，然后记住此行号，读取下一行，写入到目标文件中
         */
        boolean isCointer = false;


        try {
            BufferedReader br = new BufferedReader(new FileReader(dataPath));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                if (isCointer) {
                    writeToTxtFile(s, targetFilePath);
                }

                if (!isCointer && s.contains(lastLine)) {
                    isCointer = true;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 将内容添加到文件末尾
     *
     * @param resultContent
     */
    private static void writeToTxtFile(String resultContent, String path) {
        if (ThirdLotteryUtils.isEmpty(resultContent)) {
            return;
        }
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(path, true);
            writer.write("\n" + resultContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLastLineContent(String filePath) {
        String lastLine = "";
        RandomAccessFile rf = null;
        try {
            rf = new RandomAccessFile(filePath, "r");
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
                        System.out.println(line);
                        return lastLine;
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
        return lastLine;
    }
}
