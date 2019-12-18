package lottery.shishi;

import lottery.Model3D;
import lottery.ThirdLotteryUtils;
import lottery.Thirdinfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.*;

/**
 * ȫ����ѡ
 * ��120     252ע
 * ��60      840ע
 * ��30      360ע
 * ��20      360ע
 * ��10      90ע
 * ��5       90ע
 * ���Ǳ���     10ע
 */
public class SSCOmission {
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
                }
                /**
                 * ������Ž�������
                 */
                mList.sort(new Comparator<SSCModel>() {
                    @Override
                    public int compare(SSCModel t1, SSCModel t2) {
                        return Integer.valueOf(t1.getSequence())
                                - Integer.valueOf(t2.getSequence());
                    }
                });


                SSCType[] sscTypeArr = new SSCType[]{SSCType.GROUP_120
                      /*  , SSCType.GROUP_60,
                        SSCType.GROUP_30, SSCType.GROUP_20,
                        SSCType.GROUP_10, SSCType.GROUP_5, SSCType.GROUP_1*/
                };

                LinkedHashSet<String> allList = new LinkedHashSet<>();


                for (int i = 0; i < sscTypeArr.length; i++) {
                    allList.addAll(ThirdLotteryUtils.findAllCombinationNumsSSC(sscTypeArr[i]));
                }

                System.out.println("���е���ѡ���� " + allList.size());


               /* for (String str : allList) {
                    System.out.println(str);
                }*/

                ArrayList<Thirdinfo> thirdinfoList = new ArrayList<>();

                for (String targetStr : allList) {
                    Thirdinfo info = new Thirdinfo();
                    ArrayList<Integer> interval = new ArrayList<>();

                    int count = 0;
                    for (int i = 0; i < mList.size(); i++) {
                        String currentStr = ThirdLotteryUtils.sortString(mList.get(i).getOpenNums());
                        if (targetStr.equals(currentStr)) {
                            count += 1;
                            interval.add(i);
                        }
                    }
                    info.setIntervalList(interval);
                    info.setOpenCount(count);
                    info.setOpenNums(targetStr);
                    if (null != interval && interval.size() > 0) {
                        info.setCurrentOmission(mList.size() - interval.get(interval.size() - 1));
                    } else {
                        info.setCurrentOmission(mList.size());
                    }
                    thirdinfoList.add(info);
                }

                /**
                 * ���ݳ��ִ���ֵ��С
                 */
                Collections.sort(thirdinfoList, new Comparator<Thirdinfo>() {
                    @Override
                    public int compare(Thirdinfo thirdinfo, Thirdinfo t1) {
                        return t1.getOpenCount() - thirdinfo.getOpenCount();
                    }
                });
                /**
                 * ������©ֵ��С
                 */
               /* Collections.sort(thirdinfoList, new Comparator<Thirdinfo>() {
                    @Override
                    public int compare(Thirdinfo thirdinfo, Thirdinfo t1) {
                        return t1.getCurrentOmission() - thirdinfo.getCurrentOmission();
                    }
                });*/

                /**
                 * ���ǽ�ͳ�ƵĽ��д�뵽excel��������
                 */
                // ����������
                HSSFWorkbook workbook = new HSSFWorkbook();
                // ����������
                HSSFSheet sheet = workbook.createSheet("sheet1");
                HSSFRow rowsTitle = sheet.createRow(0);
                rowsTitle.createCell(0).setCellValue("��������");
                rowsTitle.createCell(1).setCellValue("��������");
                rowsTitle.createCell(2).setCellValue("��ǰ��©");

                for (int i = 0; i < 50; i++) {
                    rowsTitle.createCell(i + 3).setCellValue("��" + (i + 1) + "�γ���");
                }

                for (int i = 0; i < thirdinfoList.size(); i++) {
                    Thirdinfo thirdinfo = thirdinfoList.get(i);
                    HSSFRow rows = sheet.createRow(i + 1);
                    rows.createCell(0).setCellValue(thirdinfo.getOpenNums());
                    rows.createCell(1).setCellValue(String.valueOf(thirdinfo.getOpenCount()));
                    rows.createCell(2).setCellValue(String.valueOf(thirdinfo.getCurrentOmission()));


                    ArrayList<Integer> integerArrayList = thirdinfo.getIntervalList();
                    for (int j = 0; j < integerArrayList.size(); j++) {
                        String openNumsInterva = "";
                        if (j == 0) {
                            openNumsInterva = "0";
                        } else {
                            openNumsInterva = (String.valueOf((integerArrayList.get(j) - integerArrayList.get(j - 1))));
                        }
                        rows.createCell(j + 3).setCellValue(openNumsInterva);
                    }
                }

                File xlsFile = new File("ʱʱ��ѡ��©.xls");
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
