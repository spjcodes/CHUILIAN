package sparseArray;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SparseArrImpl implements Serializable {

    /**
     * 目标二维数组
     *   0	0	0	0	0	0	0	0	0	0	0
     *   0	0	1	0	0	0	0	0	0	0	0
     *   0	0	0	0	0	0	0	0	0	0	0
     *   0	0	0	2	0	0	0	0	0	0	0
     *   0	0	0	0	0	0	0	0	0	0	0
     *   **********  稀疏数组  ************
     *   5	11	2
     *   1	2	1
     *   3	3	2
     */

    int[][]  oldArray = new int[5][11];
    int[][] sparseArr = new int[3][3];

    public static void main(String[] args) {
        SparseArrImpl demo = new SparseArrImpl();
      /*  // 原来的二维数组
        demo.initOldArray();
        demo.printArr(demo.oldArray);
        //转换为稀疏数组
        demo.tranitionToSparseArr(demo.oldArray);
        System.out.println("**********  稀疏数组  ************");
        demo.printArr(demo.sparseArr);
        //保存到文件
        demo.saveToFile(demo.sparseArr);
        */
        //从文件恢复
        String path = "F:\\j2eeProjecet\\lear\\chuilian\\dataStructuresAndAlgorithms\\src\\sparseArray\\files\\SparseArrCode.txt";
        File file = new File(path);
        demo.fromFileRecover(file);

    }

    private void fromFileRecover(File file) {
        FileInputStream fos = null;
        System.out.println("文件内容读取结果如下: ");
        try {
            fos = new FileInputStream(file);
            int temp = 0;
            List chars = new ArrayList();

            while((temp = fos.read() )!= -1) {
              /* String str = String.valueOf(temp);
               System.out.println(str);*/
               chars.add((char) temp);
            }

            //将文件内容解析为一个数据一行的数组格式，便于后面处理
            String str = chars.toString();
            String[] rowStrs = null;
            String[] splitStrs = str.split("\n");
            for (String s: splitStrs) {
                rowStrs = s.split("\t");
                for (String s1: rowStrs) {
                    String[] split = s1.split("\\,");
                    for (String s2 : split) {
                        System.out.println(s2);
                    }
                }
            }

            //初始化数组，并解析数据内容进行数组数据填充
            int[][] spaArr = new int[rowStrs.length][3];
            for (String s : rowStrs) {
                String[] rs = s.split("\\,");
                System.out.println(rs);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fs != null)
                    fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void tranitionToSparseArr(int[][] arr) {
        /**
         * 计算数组行和列大小
         * 统计有效元素个数和具体位置
         */

        int row;
        int col;
        int value;
        List totalElement = new ArrayList();

        row = arr.length;
        col = arr[0].length;

        int flag = 1;

        for (int i = 0; i<row; i++ ) {
            for (int j = 0; j<col; j++) {
                value = arr[i][j];
                if (value != 0 ) {
                    sparseArr[flag][0] = i;
                    sparseArr[flag][1] = j;
                    sparseArr[flag][2] = value;
                    flag++;
                }
            }
        }

        sparseArr[0][0] = row;
        sparseArr[0][1] = col;
        sparseArr[0][2] = flag -1;
        flag = 0;


    }

    public void initOldArray () {
        oldArray[1][2] = 1;
        oldArray[3][3] = 2;
    }

    public void printArr(int[][] arr){
        for (int[] temp: arr ) {
            for ( int a: temp) {
                System.out.print(a + "\t");
            }
            System.out.println();

        }
    }

    FileOutputStream fs = null;
    public void saveToFile(int[][] sparseArr) {
        String path = "F:\\j2eeProjecet\\lear\\chuilian\\dataStructuresAndAlgorithms\\src\\sparseArray\\files\\SparseArrCode.txt";
        File file = new File(path);
        if (!file.exists()) {
            try {

                byte[] bs = new byte[sparseArr[0].length];
                bs[0] = '\n';
                fs = new FileOutputStream(file);

                for (int[] item : sparseArr) {
                    for (int i : item) {
                        fs.write(i);
                    }
                    fs.write(bs);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}