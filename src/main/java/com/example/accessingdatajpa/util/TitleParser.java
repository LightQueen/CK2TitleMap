package com.example.accessingdatajpa.util;

import com.example.accessingdatajpa.entity.Dynasty;
import com.example.accessingdatajpa.entity.Title;
import com.example.accessingdatajpa.entity.Ch;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TitleHistory
 *
 * @author lucas
 * @date 2023/07/13
 */
public class TitleParser {


    //  希望实现一个泛型的、工厂的 parse 方法
//    private List<Title> parser(String path,String chapter) {
//        Chapter c = Chapter.fromStatus(chapter);
//        c.toString();
//
//    }

    public static List<Title> parse(String path) {

        List<Title> list = new ArrayList<>();


        try {
            File file = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            // 跳至标题行
            while (!line.equals("\ttitle=")) {
                line = reader.readLine();
            }
            // 跳至左括号
            reader.readLine();
            // 跳至主体内容
            line = reader.readLine();
            // 循环至右括号
            while (!line.equals("\t}")) {
                // 初始化title
                Title t = new Title();
                String name = line.substring(0,line.indexOf("=")).trim();
                t.setName(name);
                /*
                    title 记录了
                    b级别 holding_dynasty
                    c级别 无，只能通过 holder 关联到
                    dke级别 dynasty
                    k_dyn e_dyn级别 coa_dynasty
                */
                String indexStr = "dynasty=";
                // 循环至右括号
                while (!line.equals("\t\t}")) {

                    int index = line.indexOf(indexStr);
                    if(index > -1){
                        String dnt = line.substring(index + indexStr.length());
                        Dynasty dynasty = new Dynasty();
                        dynasty.setCode(dnt);
                        dynasty.setName(dnt);
                    }


                    int index2 = line.indexOf("\tholder=");
                    if(index2 > -1){
                        String holder_name = line.substring(index2 + "\tholder=".length());
                        Ch ch = new Ch();
                        ch.setCode(holder_name);
                        ch.setName(holder_name);
                        t.setHolder(ch);
                    }


                    line = reader.readLine();
                }

                // 排除 b 和 c，留下 d 和 k 和 e
                if( t.getName().charAt(0) != 'b' && t.getHolder() != null){
                    list.add(t);
                }

                // 进入下一个title
                line = reader.readLine();
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return list;
    }



    public static void main(String[] args) {

        try {
            List<Title> list = TitleParser.parse("G:\\Documents\\myjob\\PyProjects\\fang\\ck2-social-networks-master\\ObserverModeSave2.ck2");

            File file = new File("G:\\Documents\\myjob\\PyProjects\\fang\\ck2-social-networks-master\\title.csv");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            list.forEach(t -> printWriter.println(t.getName()));
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
