package service;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class DynastyServiceTest {

    @Test
    void getTitleDepth() {


        List<List> lists = Lists.newArrayList();
        List<String> list1 = Lists.newArrayList();
        List<String> list2 = Lists.newArrayList();
        List<String> list3 = Lists.newArrayList();
        list1.add("1");
        list1.add("2");
        list2.add("1");
        list2.add("2");
        list2.add("3");
        list3.add("3");
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);



        List<String> list = new ArrayList<>();
        list = lists.stream().reduce(list,(a,b)->{
           a.addAll(b);
           return a;
        });

        list.forEach(e -> System.out.println(e));

//        Collections.addAll(list,lists.stream().toArray());
//
        Map<String,Long> map = list.stream().collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        map.forEach((key,value) -> {
            System.out.println(key + " " + value);
        });

    }

    @Test
    void dateTest() {
        Date date = Date.valueOf("0900-01-01");
        System.out.println(date.toString());
    }

}