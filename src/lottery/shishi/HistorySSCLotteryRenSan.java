package lottery.shishi;

import lottery.ThirdLotteryUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;


/**
 * ����ֱѡ����ͳ��
 * <p>
 * ��ѡ120    5������ͬ������
 * ��ѡ60     2(����)+3
 * ��ѡ30     2(����)+2(����)+1
 * ��ѡ20     3(����)+1+1
 * ��ѡ10     3(����)+2(����)
 * ��ѡ5      4(ը��)+1
 */
public class HistorySSCLotteryRenSan {

    public static void main(String[] args) {

        File file = new File(
                Sign.SHISHI_PATH);


        if (file.isFile() && file.exists()) {

            LinkedList<SSCModel> mList = new LinkedList<>();

            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(
                        fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader);

                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    if (!isEmpty(line)) {
                        String[] current = line.split("\t");
                        if (current.length > 2) {

                            String arr0 = current[0];
                            String arr1 = current[1];
                            String arr2 = current[2];

                            mList.add(new SSCModel(arr0, arr1, arr2.replace(",", "")));
                        }
                    }
                }

                System.out.println("�ܹ�ͳ������  " + mList.size());

                mList.sort(new Comparator<SSCModel>() {
                    @Override
                    public int compare(SSCModel t1, SSCModel t2) {
                        return (int) (Double.valueOf(t1.getSequence()) - Double.valueOf(t2.getSequence()));
                    }
                });

                for (int i = 0; i < mList.size(); i++) {
                    System.out.println(mList.get(i).getSequence());
                }


                //�����������ж��Ƿ�����ѡ120��ƽ�����ּ������


                LinkedList<SSCInterval> sscIntervalList = new LinkedList<>();


                int total_120 = 0;
                int total_60 = 0;
                int total_30 = 0;
                int total_20_10_5_1 = 0;

                LinkedList<Integer> interval_120 = new LinkedList<>();
                LinkedList<Integer> interval_60 = new LinkedList<>();
                LinkedList<Integer> interval_30 = new LinkedList<>();
                LinkedList<Integer> interval_20_10_5_1 = new LinkedList<>();


                for (int i = 0; i < mList.size(); i++) {
                    String opengNums = mList.get(i).getOpenNums();
                    SSCType type = ThirdLotteryUtils.findOpenNumsType(opengNums);
                    int Serial_number = Integer.valueOf(mList.get(i).getSequence());
                    if (type == SSCType.GROUP_120) {
                        total_120 += 1;
                        interval_120.add(Serial_number);
                    } else if (type == SSCType.GROUP_60) {
                        total_60 += 1;
                        interval_60.add(Serial_number);
                    } else if (type == SSCType.GROUP_30) {
                        total_30 += 1;
                        interval_30.add((Serial_number));
                    } else {
                        total_20_10_5_1 += 1;
                        interval_20_10_5_1.add(Serial_number);
                    }
                }
                sscIntervalList.add(new SSCInterval("��ѡ120", total_120, interval_120,
                        mList.size() - interval_120.getLast()
                ));
                sscIntervalList.add(new SSCInterval("��ѡ60", total_60, interval_60,
                        mList.size() - interval_60.getLast()));
                sscIntervalList.add(new SSCInterval("��ѡ30", total_30, interval_30,
                        mList.size() - interval_30.getLast()));
                sscIntervalList.add(new SSCInterval("��ѡ20_10_5_1", total_20_10_5_1, interval_20_10_5_1,
                        mList.size() - interval_20_10_5_1.getLast()));

                /**
                 * ���ǽ�ͳ�ƵĽ��д�뵽excel��������
                 */
                // ����������
                HSSFWorkbook workbook = new HSSFWorkbook();
                // ����������
                HSSFSheet sheet = workbook.createSheet("sheet1");

                HSSFRow rowsTitle = sheet.createRow(0);
                rowsTitle.createCell(0).setCellValue("��ѡ����");
                rowsTitle.createCell(1).setCellValue("���ִ���");
                rowsTitle.createCell(2).setCellValue("��ǰ��©");
                rowsTitle.createCell(3).setCellValue("�����©");
                rowsTitle.createCell(4).setCellValue("��©���");


                for (int i = 0; i < sscIntervalList.size(); i++) {
                    SSCInterval sscInterval = sscIntervalList.get(i);
                    LinkedList<Integer> integerArrayList = sscInterval.getIntervalList();
                    HSSFRow rows = sheet.createRow(i + 1);
                    rows.createCell(0).setCellValue(sscInterval.getSscNumsType());
                    rows.createCell(1).setCellValue(String.valueOf(sscInterval.getOpenCount()));
                    rows.createCell(2).setCellValue(String.valueOf(sscInterval.getCurrentInterval()));


                    ArrayList<Integer> maxList = new ArrayList<>();

                    String openNumsInterva = "";
                    for (int j = 0; j < integerArrayList.size(); j++) {
                        if (j == 0) {
                            openNumsInterva += "*" + "--";
                        } else {
                            System.out.println("��������    " + sscInterval.getSscNumsType());
                            System.out.println("���ڿ����ں�  " + integerArrayList.get(j - 1));
                            System.out.println("���ڿ����ں�  " + integerArrayList.get(j));

                            int Difference = integerArrayList.get(j) - integerArrayList.get(j - 1);
                            openNumsInterva += (String.valueOf(Difference)) + "--";
                            maxList.add(Difference);
                        }
                    }
                    if (maxList.size() > 0) {
                        maxList.sort(new Comparator<Integer>() {
                            @Override
                            public int compare(Integer t1, Integer t2) {
                                return t2 - t1;
                            }
                        });

                        rows.createCell(3).setCellValue(maxList.get(0));
                    } else {
                        rows.createCell(3).setCellValue(0);
                    }
                    rows.createCell(4).setCellValue(openNumsInterva);
                }

                File xlsFile = new File("sscNumsRensan.xls");
                FileOutputStream xlsStream = new FileOutputStream(xlsFile);
                workbook.write(xlsStream);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("�ļ�������");
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
