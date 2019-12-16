package lottery.third3d;

import lottery.shishi.SSCModel;
import lottery.shishi.Sign;
import org.apache.poi.hssf.usermodel.HSSFAutoFilter;

import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;

public class Omission3DGroupSixApp {

    public static void main(String[] args) {

        File file = new File(
                Sign.THIRD_3D_OMISSION);


        if (file.isFile() && file.exists()) {

            LinkedList<Omission3D> mList = new LinkedList<>();

            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(
                        fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader);

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    String[] current = line.split("\t");
                    String arr1 = current[0];
                    String arr2 = current[1];

                    String opengCount = "";
                    if (!isEmpty(arr2)) {
                        opengCount = arr2.substring(0, arr2.length() - 3);
                    }

                    mList.add(new Omission3D(arr1, opengCount));
                }
                System.out.println("组六号码总数  " + mList.size());
                int allCount = 0;

                for (int i = 0; i < mList.size(); i++) {
                    allCount += Integer.valueOf(mList.get(i).getOpenCount());
                }
                System.out.println("总共统计期数  " + allCount);
                mList.sort(new Comparator<Omission3D>() {
                    @Override
                    public int compare(Omission3D t1, Omission3D t2) {
                        return Integer.valueOf(t2.getOpenCount()) - Integer.valueOf(t1.getOpenCount());
                    }
                });

                int avagenCount = allCount / mList.size();


                System.out.println("平均出现次数  " + avagenCount);
                LinkedList<Omission3D> halfList = new LinkedList<>();

                for (int i = 0; i < mList.size(); i++) {
                    if (Integer.valueOf(mList.get(i).getOpenCount()) >= avagenCount) {
                        halfList.add(mList.get(i));
                    }
                }

                System.out.println("开奖次数大于平均开出次数总共有    "+halfList.size()+"  注号码");
                for (Omission3D omission3D : halfList) {
                    System.out.println("号码  " + omission3D.getOpenNums() + "    开奖次数    " + omission3D.getOpenCount());
                }


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
