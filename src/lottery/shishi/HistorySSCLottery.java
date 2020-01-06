package lottery.shishi;

import lottery.Model3D;
import lottery.ThirdLotteryUtils;
import lottery.Thirdinfo;
import lottery.third3d.Third3Type;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.*;
import java.util.*;


/**
 * ��ѡ120    5������ͬ������
 * ��ѡ60     2(����)+3
 * ��ѡ30     2(����)+2(����)+1
 * ��ѡ20     3(����)+1+1
 * ��ѡ10     3(����)+2(����)
 * ��ѡ5      4(ը��)+1
 *
 */
public class HistorySSCLottery {


    public static void createShishiFile(){
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
                int sequenceNums = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    if (!isEmpty(line) && sequenceNums > 0) {
                        String[] current = line.split("  ");
                        String arr0 = String.valueOf(sequenceNums);
                        String arr1 = current[0];
                        arr1 = arr1.replace("-", "");
                        String arr2 = current[1];

                        mList.add(new SSCModel(arr0, arr1, arr2));
                    }
                    sequenceNums += 1;

                  /*  if (!isEmpty(line)) {
                        String[] current = line.split("\t\t\t");
                        if (current.length > 2) {

                            String arr0 = String.valueOf(sequenceNums);
                            String arr1 = current[0];
                            arr1 = arr1.replace("-", "");
                            String arr2 = current[1];

                            mList.add(new SSCModel(arr0, arr1, arr2));
                            sequenceNums += 1;

                           *//* String arr0 = current[0];
                            String arr1 = current[1];
                            String arr2 = current[2];

                            System.out.println("��ǰ���    " + current[0]);
                            System.out.println("��ǰ�ں�    " + current[1]);
                            System.out.println("��ǰ����    " + current[2]);

                            mList.add(new SSCModel(arr0, arr1, arr2.replace(",", "")));*//*
                        }
                    }*/
                }

                System.out.println("�ܹ�ͳ������  " + mList.size());

                mList.sort(new Comparator<SSCModel>() {
                    @Override
                    public int compare(SSCModel t1, SSCModel t2) {
                        return Integer.valueOf(t1.getSequence()) - Integer.valueOf(t2.getSequence());
                    }
                });

                //�����������ж��Ƿ�����ѡ120��ƽ�����ּ������


                LinkedList<SSCInterval> sscIntervalList = new LinkedList<>(); //new ArrayList<>();


                int total_120 = 0;
                int total_60 = 0;
                int total_30 = 0;
                int total_20 = 0;
                int total_10 = 0;
                int total_5 = 0;
                int total_1 = 0;

                int total_qiansan_6 = 0;
                int total_qiansan_3 = 0;
                int total_qiansan_leopard = 0;

                int total_zhongsan_6 = 0;
                int total_zhongsan_3 = 0;
                int total_zhongsan_leopard = 0;

                int total_housan_6 = 0;
                int total_housan_3 = 0;
                int total_housan_leopard = 0;


                LinkedList<Integer> interval_120 = new LinkedList<>();
                LinkedList<Integer> interval_60 = new LinkedList<>();
                LinkedList<Integer> interval_30 = new LinkedList<>();
                LinkedList<Integer> interval_20 = new LinkedList<>();
                LinkedList<Integer> interval_10 = new LinkedList<>();
                LinkedList<Integer> interval_5 = new LinkedList<>();
                LinkedList<Integer> interval_1 = new LinkedList<>();


                LinkedList<Integer> interval_qiansan_6 = new LinkedList<>();
                LinkedList<Integer> interval_qiansan_3 = new LinkedList<>();
                LinkedList<Integer> interval_qiansan_leopard = new LinkedList<>();


                LinkedList<Integer> interval_zhongsan_6 = new LinkedList<>();
                LinkedList<Integer> interval_zhongsan_3 = new LinkedList<>();
                LinkedList<Integer> interval_zhongsan_leopard = new LinkedList<>();


                LinkedList<Integer> interval_housan_6 = new LinkedList<>();
                LinkedList<Integer> interval_housan_3 = new LinkedList<>();
                LinkedList<Integer> interval_housan_leopard = new LinkedList<>();

                /**
                 * ����
                 */
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
                    } else if (type == SSCType.GROUP_20) {
                        total_20 += 1;
                        interval_20.add(Serial_number);
                    } else if (type == SSCType.GROUP_10) {
                        total_10 += 1;
                        interval_10.add(Serial_number);
                    } else if (type == SSCType.GROUP_5) {
                        total_5 += 1;
                        interval_5.add(Serial_number);
                    } else if (type == SSCType.GROUP_1) {
                        total_1 += 1;
                        interval_1.add(Serial_number);
                    }
                }
                /**
                 * ǰ��
                 */
                for (int i = 0; i < mList.size(); i++) {
                    String opengNums = mList.get(i).getOpenNums();
                    opengNums = opengNums.substring(0, opengNums.length() - 2);
                    Third3Type type = ThirdLotteryUtils.FindThirdNumsType(opengNums);
                    int Serial_number = Integer.valueOf(mList.get(i).getSequence());
                    if (type == Third3Type.GROUP_SIX) {
                        total_qiansan_6 += 1;
                        interval_qiansan_6.add(Serial_number);
                    } else if (type == Third3Type.GROUP_THREE) {
                        total_qiansan_3 += 1;
                        interval_qiansan_3.add(Serial_number);
                    } else {
                        total_qiansan_leopard += 1;
                        interval_qiansan_leopard.add(Serial_number);
                    }
                }
                /**
                 * ����
                 */
                for (int i = 0; i < mList.size(); i++) {
                    String opengNums = mList.get(i).getOpenNums();
                    opengNums = opengNums.substring(1, opengNums.length() - 1);
                    Third3Type type = ThirdLotteryUtils.FindThirdNumsType(opengNums);
                    int Serial_number = Integer.valueOf(mList.get(i).getSequence());
                    if (type == Third3Type.GROUP_SIX) {
                        total_zhongsan_6 += 1;
                        interval_zhongsan_6.add(Serial_number);
                    } else if (type == Third3Type.GROUP_THREE) {
                        total_zhongsan_3 += 1;
                        interval_zhongsan_3.add(Serial_number);
                    } else {
                        total_zhongsan_leopard += 1;
                        interval_zhongsan_leopard.add(Serial_number);
                    }
                }
                /**
                 * ����
                 */
                for (int i = 0; i < mList.size(); i++) {
                    String opengNums = mList.get(i).getOpenNums();
                    opengNums = opengNums.substring(2, opengNums.length());
                    Third3Type type = ThirdLotteryUtils.FindThirdNumsType(opengNums);
                    int Serial_number = Integer.valueOf(mList.get(i).getSequence());
                    if (type == Third3Type.GROUP_SIX) {
                        total_housan_6 += 1;
                        interval_housan_6.add(Serial_number);
                    } else if (type == Third3Type.GROUP_THREE) {
                        total_housan_3 += 1;
                        interval_housan_3.add(Serial_number);
                    } else {
                        total_housan_leopard += 1;
                        interval_housan_leopard.add(Serial_number);
                    }
                }


                sscIntervalList.add(new SSCInterval("��ѡ120", total_120, interval_120,
                        mList.size() - interval_120.getLast()
                ));
               /* sscIntervalList.add(new SSCInterval("��ѡ60", total_60, interval_60,
                        mList.size() - interval_60.getLast()));*/
                sscIntervalList.add(new SSCInterval("��ѡ30", total_30, interval_30,
                        mList.size() - interval_30.getLast()));
                sscIntervalList.add(new SSCInterval("��ѡ20", total_20, interval_20,
                        mList.size() - interval_20.getLast()));
                sscIntervalList.add(new SSCInterval("��ѡ10", total_10, interval_10,
                        mList.size() - interval_10.getLast()));
                sscIntervalList.add(new SSCInterval("��ѡ5", total_5, interval_5,
                        mList.size() - interval_5.getLast()));
                sscIntervalList.add(new SSCInterval("�����걪", total_1, interval_1,
                        mList.size() - interval_1.getLast()));

                /**
                 * ǰ��
                 */
              /*  sscIntervalList.add(new SSCInterval("ǰ������", total_qiansan_6, interval_qiansan_6,
                        mList.size() - interval_qiansan_6.getLast()));*/
                sscIntervalList.add(new SSCInterval("ǰ������", total_qiansan_3, interval_qiansan_3,
                        mList.size() - interval_qiansan_3.getLast()));
                sscIntervalList.add(new SSCInterval("ǰ������", total_qiansan_leopard,
                        interval_qiansan_leopard,
                        mList.size() - interval_qiansan_leopard.getLast()));


                /**
                 * ����
                 */
               /* sscIntervalList.add(new SSCInterval("��������", total_zhongsan_6, interval_zhongsan_6,
                        mList.size() - interval_zhongsan_6.getLast()));*/
                sscIntervalList.add(new SSCInterval("��������", total_zhongsan_3, interval_zhongsan_3,
                        mList.size() - interval_zhongsan_3.getLast()));
                sscIntervalList.add(new SSCInterval("��������", total_zhongsan_leopard, interval_zhongsan_leopard,
                        mList.size() - interval_zhongsan_leopard.getLast()));
                /**
                 * ����
                 */
               /* sscIntervalList.add(new SSCInterval("��������", total_housan_6, interval_housan_6,
                        mList.size() - interval_housan_6.getLast()));*/
                sscIntervalList.add(new SSCInterval("��������", total_housan_3, interval_housan_3,
                        mList.size() - interval_housan_3.getLast()));
                sscIntervalList.add(new SSCInterval("��������", total_housan_leopard, interval_housan_leopard,
                        mList.size() - interval_housan_leopard.getLast()));

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
                rowsTitle.createCell(5).setCellValue("-------");


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
                            openNumsInterva += "*" + "-";
                        } else {
//                            System.out.println("��������    " + sscInterval.getSscNumsType());
//                            System.out.println("���ڿ����ں�  " + integerArrayList.get(j - 1));
//                            System.out.println("���ڿ����ں�  " + integerArrayList.get(j));

                            int Difference = integerArrayList.get(j) - integerArrayList.get(j - 1);
                            openNumsInterva += (String.valueOf(Difference)) + "-";
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

                File xlsFile = new File("����ʱʱ.xls");
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
