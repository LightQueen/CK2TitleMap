package com.example.accessingdatajpa.util;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * CkLoader
 *
 * @author Administrator
 * @date 2023/7/31
 */
public class CkLoader {
    public static void interceptFile(String fromFileName,String toFileName) throws IOException {
        List inList = Arrays.asList(new String[]{"\tdynasties=", "\tcharacter=", "\ttitle="});
//        List inList = Arrays.asList(new String[]{"\tcharacter="});
        String outString = "\t}";
        boolean in = false;

        File inputFile = new File(fromFileName);
        File tempFile = new File(toFileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {


            String line;
            while ((line = reader.readLine()) != null) {

                if (inList.indexOf(line) != -1) {
                    in = true;
                }

                if (in) {
                    writer.write(line);
                    writer.newLine();
                }

                if (outString.equals(line)) {
                    in = false;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to intercept the file.");
            throw e;
        }
    }

    public static void alterFileFirstRow(String fileName,String tempFileName) {

            File inputFile = new File(fileName);
            File tempFile = new File(tempFileName);

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                String firstLine = reader.readLine(); // 读取第一行内容
                if (firstLine != null) {
                    String modifiedFirstLine = firstLine + "={\n";
                    writer.write(modifiedFirstLine);
                }

                // 复制剩余的文件内容到临时文件
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 删除原文件并将临时文件重命名为原文件名
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            } else {
                System.out.println("Failed to update the file.");
            }
        }

}
