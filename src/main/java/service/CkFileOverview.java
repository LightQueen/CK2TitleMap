package service;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class CkFileOverview {

    static class MyLinkedHashMap<K,V> extends LinkedHashMap<K,V> {
        public MyLinkedHashMap<K,V> parent;

    }
    static class Matcher{
        String type;
        Matcher(String str){
            if (Pattern.matches("^[0-9]+$", str)){
                type = "type1";
            }else if (Pattern.matches("^[bcdke]_.*", str)){
                type = "type2";
            }else if (Pattern.matches(".*[0-9].*", str)){
                type = "type3";
            }else{
                throw new RuntimeException();
            }
        }
        boolean match(String str){
            ArrayList<String> list = new ArrayList<>();
            list.add(str);
            return match(list);
        }
        boolean match(String str1,String str2){
            ArrayList<String> list = new ArrayList<>();
            list.add(str1);
            list.add(str2);
            return match(list);
        }
        boolean match(Collection<String> strList){
            Stream<String> stream = strList.stream();
            Iterator<String> iterator = strList.iterator();

            if (type.equals("type1") && stream.allMatch(str -> Pattern.matches("^[0-9]+$", str))){
                return true;
            }
            if (type.equals("type2") &&  stream.allMatch(str -> Pattern.matches("^[bcdke]_.*", str))){
                return true;
            }
            if (type.equals("type3")){
                boolean result = true;
                String strTemp = null;
                while(iterator.hasNext()){
                    String str2 = Pattern.compile("[0-9]").matcher(iterator.next()).replaceAll("");
                    if(strTemp==null || "".equals(strTemp)){
                        strTemp = str2;
                    }else if(!strTemp.equals(str2)){
                        result = false;
                    }
                }
                return result;
            }
            return false;
        }
    }

    private String strictFind(Map map, String key){
        if (map.get(key) != null){ return key;}
        Matcher matcher;
        try{
            matcher = new Matcher(key);
        }catch(Exception e){
            return null;
        }

        String s = null;
        Iterator<String> it = map.keySet().iterator();
        // 寻找能够匹配数字的s
        while ( !(s != null && s.length()>0 && matcher.match(s,key))){
            if(it.hasNext()) { // 移动游标
                s = it.next();
            }else{ // 结束
                s = null;
                break;
            }
        }
        return s;
    }

    private String find(Map map, String key){
        String s = strictFind(map,key);
        return (s==null || "".equals(s))?key:s;
    }

    private Map<String,Object> parser(String path) {

/*
        String str = "CK2txt\n"+
                "\tversion=\"3.3.5.1\"\n" +
        "\tdate=\"867.1.1\"\n" +
        "\trel_101=\n" +
        "\t{\n" +
        "\t\tid=101\n" +
        "\t\ttype=64\n" +
        "\t}\n" +
        "\trel_303=\n" +
        "\t{\n" +
        "\t\tid=303\n" +
        "\t\ttype=65\n" +
        "\t\totherType=166\n" +
        "\t}\n" +
        "\tplayer_realm=\"k_france\"\n" +
        "\tplayer_name=\"King Charles 'the Bald'\"\n" +
        "\tc_player_age=43\n" +
        "\tb_player_age=20\n" +
        "\tplayer_portrait=\n" +
        "\t{\n" +
        "\t\tfem=no\n" +
        "\t\tdna=\"cjlcjygaaar\"\n" +
        "\t\tproperties=\"an0abd0000000000000000000000000n000000\"\n" +
        "\t\treligion=\"catholic\"\n" +
        "\t\tculture=\"frankish\"\n" +
        "\t\tgovernment=\"feudal_government\"\n" +
        "\t\tdynasty=25061\n" +
        "\t}";

        System.out.println(str);

        File file = File.createTempFile("testrunoobtmp", ".txt");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(str);
        fileWriter.flush();
        fileWriter.close();

 */


        MyLinkedHashMap<String,Object> rootMap = new MyLinkedHashMap<>();
        MyLinkedHashMap<String,Object> temp; // 用于 switch
        MyLinkedHashMap<String,Object> currentMap = rootMap; // 指针
        String currentFieldName = "";
        try {
            File file = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while (line != null) {
                int equalIndex = line.indexOf("=");
                 // 判断格式
                if (equalIndex != -1){ // 普通 map 行
                    String fieldValue = line.substring(equalIndex+1).trim();

                    String fieldName = line.substring(0,equalIndex).trim(); // 此处返回值 NotNull,空字符串
                    // 对键进行处理
                    fieldName = find(currentMap,fieldName);
                    currentMap.putIfAbsent(fieldName,(fieldValue.isEmpty()?null:fieldValue)); // 若为空字符串则插入 null
                    currentFieldName = fieldName;

                }else if(line.contains("{")){ // 开始行
                    Object o = currentMap.get(currentFieldName);
                    // 判断子类型
                    if (o == null || !(o instanceof MyLinkedHashMap)){ // 类型不符
                        // 子级维护父级
                        temp =  new MyLinkedHashMap<>();
                        temp.parent = currentMap;
                        // 父亲维护子级
                        currentMap.put(currentFieldName,temp);
                        // 当前指针指向子级
                        currentMap = temp;
                    }else {
                        // 当前指针指向子级
                        currentMap = (MyLinkedHashMap<String, Object>) o;
                    }
                }else if(line.contains("}")){ // 结束行
                    // 当前指针指向父级
                    if(currentMap.parent != null){
                        currentMap = currentMap.parent;
                    }
                }else { // 无值的情况
                    String fieldName = line.trim();
                    // 对键进行处理
                    fieldName = find(currentMap,fieldName);
                    currentMap.put(fieldName,null);
                }

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootMap;
    }

    public static void main(String[] args) {
        CkFileOverview ckFileOverview = new CkFileOverview();

        try {
            Map map = ckFileOverview.parser("G:\\Documents\\myjob\\PyProjects\\fang\\ck2-social-networks-master\\ObserverModeSave.ck2");
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStr = objectMapper.writeValueAsString(map);

            File file = new File("G:\\Documents\\myjob\\PyProjects\\fang\\ck2-social-networks-master\\ObserverModeSave2.json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonStr);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
