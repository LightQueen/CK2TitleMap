package service;

import bo.Character;
import bo.Dynasty;
import dto.TitleDay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DynastyService {


    public List<Character> getAllCharacter(Dynasty dyn){
        List<Character> characters = (List<Character>) JDBCUtil.query("select * from character where dyn=" + dyn.getCode());
        return characters;
    }

    public List<Character> getLivingCharacter(Dynasty dyn){
        List<Character> characters = (List<Character>) JDBCUtil.query("select * from character where d_d is null and dyn=" + dyn.getCode());
        return characters;
    }

    public int getScore(Dynasty dyn){
        int score = 0;
        for (Character c : getAllCharacter(dyn)) {
            score += c.getScore();
        }
        return score;
    }

    /**
     * @return
     */
    public List<TitleDay> getRealmWithDays(Dynasty dyn){
        List<TitleDay> res = (List<TitleDay>) JDBCUtil.query("select th.title,sum(th.hold_days) as hold_days ,min(th.expired_days) as expired_days\n" +
                "from title_history th\n" +
                "join character ch \n" +
                "on th.holder = ch.code\n" +
                "where ch.dyn = :dyn \n" +
                "group by th.title");
        return res;
    }

    public Map<String,Long> getRealmWithDepth(Dynasty dyn) {
        List<List<String>> lists = getLivingCharacter(dyn).stream()
                .map(p -> p.getRealms())
                .collect(Collectors.toList());
        List<String> list = new ArrayList<>();
        lists.stream().reduce(list,(a,b)->{
            a.addAll(b);
            return a;
        });
//        list.forEach(e -> System.out.println(e));

        Map<String,Long> map = list.stream().collect(Collectors.groupingBy(p -> p, Collectors.counting()));
//        map.forEach((key,value) -> {
//            System.out.println(key + " " + value);
//        });

        // 扩展： 找到 lists 中的 size
        // List<String> max = lists.stream().max(Comparator.comparingInt(vo -> vo.size())).get();
        return map;
    }
}
