package lottery;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class History3DLottery {

    public static void main(String[] args) {

        File file = new File(
                "D:/WorkSpace/IdeaProjects/Third3DOpenNums/src/lottery/豹子.txt");


        if (file.isFile() && file.exists()) {

            LinkedList<Model3D> mList = new LinkedList<>();

            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(
                        fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader);

                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    if (!isEmpty(line)) {
                        String[] current = line.split(" ");
                        mList.add(new Model3D(current[0], current[1],
                                current[2] + current[3] + current[4]));
                    }
                }

                mList.sort(new Comparator<Model3D>() {
                    @Override
                    public int compare(Model3D model3D, Model3D t1) {
                        return Integer.valueOf(model3D.getDateLine())
                                - Integer.valueOf(t1.getDateLine());
                    }
                });

                /*
                 * for (Model3D model : mList) {
                 * System.out.println(model.toString()); }
                 */

                /**
                 * 所有的组选号码,包括豹子号码
                 */
                LinkedHashSet<String> allList = ThirdLotteryUtils
                        .findAllCombinationNums();

                ArrayList<Thirdinfo> thirdinfoList = new ArrayList<>();

                for (String targetStr : allList) {
                    Thirdinfo info = new Thirdinfo();
                    ArrayList<Integer> interval = new ArrayList<>();

                    int count = 0;
                    for (int i = 0; i < mList.size(); i++) {
                        String currentStr = ThirdLotteryUtils.sortString(mList
                                .get(i).getOpenNums());
                        if (targetStr.equals(currentStr)) {
                            count += 1;
                            interval.add(i);
                        }
                    }
                    info.setIntervalList(interval);
                    info.setOpenCount(count);
                    info.setOpenNums(targetStr);
                    info.setCurrentOmission(mList.size() - interval.get(interval.size() - 1));
                    thirdinfoList.add(info);
                }
                /**
                 * 根据出现次数
                 */
               /* Collections.sort(thirdinfoList, new Comparator<Thirdinfo>() {
                    @Override
                    public int compare(Thirdinfo thirdinfo, Thirdinfo t1) {
                        return t1.getOpenCount() - thirdinfo.getOpenCount();
                    }
                });*/
                /**
                 * 根据遗漏值大小
                 */
                Collections.sort(thirdinfoList, new Comparator<Thirdinfo>() {
                    @Override
                    public int compare(Thirdinfo thirdinfo, Thirdinfo t1) {
                        return t1.getCurrentOmission() - thirdinfo.getCurrentOmission();
                    }
                });

                /**
                 * 我们将统计的结果写入到excel工作表中
                 */
                // 创建工作薄
                HSSFWorkbook workbook = new HSSFWorkbook();
                // 创建工作表
                HSSFSheet sheet = workbook.createSheet("sheet1");
                HSSFRow rowsTitle = sheet.createRow(0);
                rowsTitle.createCell(0).setCellValue("开奖号码");
                rowsTitle.createCell(1).setCellValue("开出次数");
                rowsTitle.createCell(2).setCellValue("当前遗漏");

                for (int i = 0; i < 100; i++) {
                    rowsTitle.createCell(i + 3).setCellValue("第" + i + "次出现");
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

                File xlsFile = new File("ThirdNums.xls");
                FileOutputStream xlsStream = new FileOutputStream(xlsFile);
                workbook.write(xlsStream);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("文件不存在");
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
