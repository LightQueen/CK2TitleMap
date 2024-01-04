package com.example.accessingdatajpa.service;

import com.example.accessingdatajpa.dao.ChRepository;
import com.example.accessingdatajpa.dao.DynastyRepository;
import com.example.accessingdatajpa.dao.TitleHistoryRepository;
import com.example.accessingdatajpa.dao.TitleRepository;
import com.example.accessingdatajpa.entity.*;
import com.example.accessingdatajpa.util.Calc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class HongService {
    @Autowired
    private DynastyRepository dynastyRepository;
    @Autowired
    private ChRepository chRepository;
    @Autowired
    private TitleRepository titleRepository;
    @Autowired
    private TitleHistoryRepository titleHistoryRepository;

    @Transactional
    public void test(){
//        Title title = new Title();
//        title.setCode("d_holstein");

        Title title1 = new Title();
        title1.setCode("test21");
        title1 = titleRepository.save(title1);

        Title title = new Title();
        title.setCode("test22");
        title1.setDe_jure_liege(title);
        Title title2 = new Title();
        title2.setCode("test23");
        title2.setDe_jure_liege(title);
        Title title3 = new Title();
        title3.setCode("test24");
        title3.setDe_jure_liege(title1);
        titleRepository.saveAll(Arrays.asList(title2,title,title1,title3));

        /*
        * 210
        * 3210
        * 2310
        * 2013
        *
        * */

//        titleRepository.save(title);
//        title.setName("test1");
//        TitleHistory titleHistory = new TitleHistory();
//        titleHistory.setTitle(title);
//        titleHistory.setExpiredDays(3);
//        TitleHistory titleHistory1 = new TitleHistory();
//        titleHistory1.setTitle(title);
//        titleHistory1.setExpiredDays(4);
//        title.getTitleHistories().add(titleHistory);
//        title.getTitleHistories().add(titleHistory1);
//        titleRepository.save(title);
    }

    /**
     * 清空数据
     */
    @Transactional
    public void clearHong() {
        // 删除
        titleHistoryRepository.deleteAll();
        titleRepository.deleteAll();
        chRepository.deleteAll();
        dynastyRepository.deleteAll();
    }
    /**
     * 准备数据
     */
    @Transactional
    public void createHong(){

        // clearHong();
        // 创建
        Dynasty Jia = dynastyRepository.save(new Dynasty("jia", "贾"));
        Dynasty Wang = dynastyRepository.save(new Dynasty("wang", "王"));
        Dynasty Shi = dynastyRepository.save(new Dynasty("shi", "史"));
        Dynasty Xue = dynastyRepository.save(new Dynasty("xue", "薛"));
        Dynasty Lin = dynastyRepository.save(new Dynasty("lin", "林"));

        Ch JiaDaiShan = chRepository.save(new Ch("JiaDaiShan", "贾代善", false, Date.valueOf("0950-01-01"), Date.valueOf("1000-01-01"),  Jia,null,null,null));
        Ch JiaMu = chRepository.save(new Ch("JiaMu", "贾母", true, Date.valueOf("0950-01-01"), null,Shi,null,null,null));
        Ch JiaZheng = chRepository.save(new Ch("JiaZheng", "贾政", false, Date.valueOf("0975-01-01"), null,Jia,JiaDaiShan,JiaMu,JiaMu));
        Ch WangFuRen = chRepository.save(new Ch("WangFuRen", "王夫人", true, Date.valueOf("0975-01-01"), null,Wang,null,null,JiaMu));
        Ch JiaBaoYu = chRepository.save(new Ch("JiaBaoYu", "贾宝玉", false, Date.valueOf("1000-01-01"), null,Jia,JiaZheng,WangFuRen,JiaMu));
        Ch XueBaoChai = chRepository.save(new Ch("XueBaoChai", "薛宝钗", true, Date.valueOf("1000-03-01"), null,Xue,null,null,null));
        Ch LinDaiYu = chRepository.save(new Ch("LinDaiYu", "林黛玉", true, Date.valueOf("1000-03-01"), null,Lin,null,null,null));

        Title e_JiaFu = titleRepository.save(new Title("e_JiaFu","贾府"));
        Title k_DaGuanYuan = titleRepository.save(new Title("k_DaGuanYuan","大观园"));
        k_DaGuanYuan.setDe_jure_liege(e_JiaFu);
        k_DaGuanYuan.setLiege(e_JiaFu);
        Title k_RongGuoFu = titleRepository.save(new Title("k_RongGuoFu","荣国府"));
        k_RongGuoFu.setDe_jure_liege(e_JiaFu);
        k_RongGuoFu.setLiege(e_JiaFu);
        Title k_NingGuoFu = titleRepository.save(new Title("k_NingGuoFu","宁国府"));
        k_NingGuoFu.setDe_jure_liege(e_JiaFu);
        k_NingGuoFu.setLiege(e_JiaFu);
        Title d_DaGuanYuan = titleRepository.save(new Title("d_DaGuanYuan","大观园"));
        d_DaGuanYuan.setDe_jure_liege(k_DaGuanYuan);
        d_DaGuanYuan.setLiege(k_DaGuanYuan);
        Title c_YiHongYuan = titleRepository.save(new Title("c_YiHongYuan","怡红院"));
        c_YiHongYuan.setDe_jure_liege(k_DaGuanYuan);
        c_YiHongYuan.setLiege(k_DaGuanYuan);
        Title c_XiaoXiangGuan = titleRepository.save(new Title("c_XiaoXiangGuan", "潇湘馆"));
        c_XiaoXiangGuan.setDe_jure_liege(k_DaGuanYuan);
        c_XiaoXiangGuan.setLiege(k_DaGuanYuan);
        Title c_HengWuYuan = titleRepository.save(new Title("c_HengWuYuan","蘅芜苑"));
        c_HengWuYuan.setDe_jure_liege(k_DaGuanYuan);
        c_HengWuYuan.setLiege(k_DaGuanYuan);
        Title c_DaoXiangCun = titleRepository.save(new Title("c_DaoXiangCun","稻香村"));
        c_DaoXiangCun.setDe_jure_liege(k_DaGuanYuan);
        c_DaoXiangCun.setLiege(k_DaGuanYuan);

        JiaMu.addTitle(e_JiaFu);
        JiaMu.addTitle(k_NingGuoFu);
        JiaMu.addTitle(k_DaGuanYuan);
        JiaZheng.addTitle(k_RongGuoFu);
        JiaZheng.addTitle(d_DaGuanYuan);
        LinDaiYu.addTitle(c_XiaoXiangGuan);
        JiaBaoYu.addTitle(c_DaoXiangCun);
        JiaBaoYu.addTitle(c_YiHongYuan);
        XueBaoChai.addTitle(c_HengWuYuan);
        // titleHistoryRepository.deleteByTitle(c_YiHongYuan);
        // titleHistoryRepository.deleteByTitle(c_XiaoXiangGuan);
        // titleHistoryRepository.deleteByTitle(c_HengWuYuan);
        // titleHistoryRepository.deleteByTitle(c_DaoXiangCun);
        TitleHistory titleHistory1 = TitleHistory.builder().title(c_YiHongYuan).holder(JiaBaoYu).holdDays(25).latest(true).build();
        TitleHistory titleHistory2 = TitleHistory.builder().title(c_XiaoXiangGuan).holder(LinDaiYu).holdDays(20).latest(true).build();
        TitleHistory titleHistory3 = TitleHistory.builder().title(c_HengWuYuan).holder(XueBaoChai).holdDays(25).latest(true).build();
        TitleHistory titleHistory4 = TitleHistory.builder().title(c_DaoXiangCun).holder(JiaBaoYu).holdDays(10).latest(true).build();
        TitleHistory titleHistory5 = TitleHistory.builder().title(c_XiaoXiangGuan).holder(JiaBaoYu).holdDays(5).expiredDays(20).build();

        TitleHistory titleHistory6 = TitleHistory.builder().title(e_JiaFu).holder(JiaDaiShan).holdDays(30).expiredDays(25).build();
        TitleHistory titleHistory7 = TitleHistory.builder().title(k_NingGuoFu).holder(JiaDaiShan).holdDays(30).expiredDays(25).build();
        TitleHistory titleHistory8 = TitleHistory.builder().title(e_JiaFu).holder(JiaDaiShan).holdDays(30).expiredDays(25).build();
        TitleHistory titleHistory9 = TitleHistory.builder().title(e_JiaFu).holder(JiaDaiShan).holdDays(30).expiredDays(25).build();
        TitleHistory titleHistory10 = TitleHistory.builder().title(k_NingGuoFu).holder(JiaMu).holdDays(25).latest(true).build();
        TitleHistory titleHistory11 = TitleHistory.builder().title(k_NingGuoFu).holder(JiaMu).holdDays(25).latest(true).build();

        // 保存
        List<TitleHistory> titleHistories1 = Arrays.asList(titleHistory1, titleHistory2, titleHistory3, titleHistory4,titleHistory5);
        List<TitleHistory> titleHistories2 = Arrays.asList(titleHistory6, titleHistory7, titleHistory8, titleHistory9, titleHistory10,titleHistory11);
        titleHistoryRepository.saveAll(titleHistories1);
        titleHistoryRepository.saveAll(titleHistories2);

        List<Title> e_titles = Arrays.asList(e_JiaFu);
        List<Title> k_titles = Arrays.asList(k_DaGuanYuan,k_RongGuoFu,k_NingGuoFu);
        List<Title> d_titles = Arrays.asList(d_DaGuanYuan);
        List<Title> c_titles = Arrays.asList(c_YiHongYuan,c_XiaoXiangGuan,c_HengWuYuan,c_DaoXiangCun);
        List<Title> titles = new ArrayList<>();
        titles.addAll(e_titles);
        titles.addAll(k_titles);
        titles.addAll(d_titles);
        titles.addAll(c_titles);
//        List<Title> titles = new ArrayList<>(){{addAll(c_titles);addAll(d_titles);addAll(k_titles);addAll(e_titles);}};
        titles.forEach(title -> {Calc.calcLevel(title);});

        List<Ch> chs = Arrays.asList(JiaDaiShan,JiaMu,JiaZheng,JiaBaoYu,WangFuRen,XueBaoChai,LinDaiYu);
        chs.forEach(ch -> {Calc.calcLevel(ch);Calc.calcScore(ch);});

        List<Dynasty> dynasties = Arrays.asList(Jia,Wang,Shi,Xue,Lin);
        dynasties.forEach(Calc::calcScore);

        dynastyRepository.saveAll(dynasties);
        chRepository.saveAll(chs);
        titleRepository.saveAll(titles);
    }

    /**
     * 一个角色的领地统治历史。由 TitleHistory 得到统治的离开日期、统治总时长。
     *
     * @param holder_code 角色的编号
     * @return
     */
    public Collection<TitleDay> realmWithDaysOfChCode(String holder_code) {
        List<TitleHistory> histories = titleHistoryRepository.findAllByHolder_code(holder_code);

        Collection<TitleDay> titleDays = histories.stream().collect(Collectors.groupingBy(TitleHistory::getTitle, Collectors.collectingAndThen(Collectors.toList(), his -> {
            int holdDays = his.stream().mapToInt(x-> (int) (x.getEd().toLocalDate().toEpochDay()- x.getSd().toLocalDate().toEpochDay())).sum();
            int expiredDays = his.stream().mapToInt(x-> Objects.isNull(x) ? 0 : Optional.ofNullable(x.getExpiredDays()).orElse(0)).min().orElse(0);
            TitleDay titleDay = new TitleDay(his.get(0).getTitle().getName(), holdDays, expiredDays);
            return titleDay;
        }))).values();

        return titleDays;
    }

    /**
     * 一个（或多个）角色直辖的伯爵领。
     *
     * @param chCodes 角色的编号数组 chCodes = new String[]{"JiaBaoYu","XueBaoChai"};
     * @return
     */
    public List<Title> realmOfChCode(String... chCodes) {
        List<Title> realms = titleRepository.findByHolder_CodeInAndCodeLike(chCodes, "c%");
        return realms;
    }

    /**
     * 一个（或多个）角色直辖的伯爵领。
     *
     * @param chs 角色的数组
     * @return
     */
    public List<Title> realmOfCh(Ch... chs) {
        List<Title> realms = titleRepository.findByHolderInAndCodeLike(chs, "c%");
        return realms;
    }

    /**
     * 一个角色的所有下级。递归地获得。
     *
     * @param root 角色
     * @return
     */
    public List<Ch> allGuestsByCh(Ch root) {
        List<Ch> result = new ArrayList<>();
        result.add(root);
        List<Ch> chList = new ArrayList<>();
        chList.add(root);
        while(chList.size() != 0) {
            chList = chRepository.findAllByHostIn(chList.toArray(new Ch[chList.size()]));
            result.addAll(chList);
        }
        return result;
    }

    /**
     * 一个角色的所有下级，及各级的 DynastyDepth。
     *
     * @param root 角色
     * @return
     */
    public Map<Ch, Integer> allGuestsWithDynastyDepthByCh(Ch root) {

        Map<Ch,Integer> result = new HashMap<>();
        result.put(root,1);
        Dynasty dynasty = root.getDnt();

        List<Ch> chList = new ArrayList<>();
        chList.add(root);
        while(chList.size() != 0) {
            chList = chRepository.findAllByHostIn(chList.toArray(new Ch[chList.size()]));
            chList.forEach(x -> {
                int val = 0;
                if (x.getDnt().equals(dynasty)) {
                    val = 1;
                }
                result.put(x,result.get(x.getHost())+val);
            });
        }
        return result;
    }

    /**
     * 一个家族的领地，及各级地块的 DynastyDepth。
     *
     * @param dntCode 家族的编号
     * @param type 计算方式
     * @return
     */
    public Map<Title, Integer> realmWithDynastyDepthByDynCode(String dntCode,int type) {
        Map<Title, Integer> result = new HashMap<>();
        List<Ch> chs = chRepository.findAllByDntCodeAndDdIsNullOrderByLevelDesc(dntCode);
        Map<Ch,Integer> processedChars = new HashMap<>();
        chs.forEach(c ->  {
            if (!processedChars.containsKey(c)) {
                Map<Ch, Integer> chMap = allGuestsWithDynastyDepthByCh(c);
                processedChars.putAll(chMap);
            }
        });
        log.info("=======================================================");
        if (type == 1) {
            // 法一：每次循环显式访问一次数据库
            processedChars.forEach((c, depth) -> {
                realmOfCh(c).forEach(title -> {
                    result.put(title, depth);
                });
            });
        }
        if (type == 2) {
            // 法二：依赖 Ch 的 titles
            processedChars.forEach((c, depth) -> {
                c.getTitles().stream()
                        .filter(title -> title.getLevel() == TitleLevel.C)
                        .forEach(title -> {
                            result.put(title, depth);
                        });
            });
        }
        if (type == 3) {
            // 法三：依赖 Title 的 holder
            List<Title> realms = realmOfCh(processedChars.keySet().toArray(new Ch[0]));
            realms.forEach(title -> {
                result.put(title, processedChars.get(title.getHolder()));
            });
        }
        return result;
    }

    public Map<Title, Integer> realmWithDynastyDepthByDynCode2(String dntCode) {
        Map<Title, Integer> result = new HashMap<>();

        List<Ch> chs = chRepository.findAllByDntCodeAndDdIsNullOrderByLevelDesc(dntCode);
        // 为了能够移除数据，所以用了迭代器
        Iterator<Ch> iterator = chs.iterator();
        while (iterator.hasNext()) {
            Ch c = iterator.next();
            Map<Ch, Integer> chMap = allGuestsWithDynastyDepthByCh(c);
            chMap.forEach((k,v)->{
                realmOfCh(k).forEach(x->{
                    result.put(x,v);
                });
            });
            // 移除重复数据
            iterator.remove();
            chs.removeAll(chMap.keySet());
        }
        return result;
    }

    public Map<Title, Integer> realmWithDynastyDepthByDynCode3(String dntCode) {

        List<Ch> chs = chRepository.findAllByDntCodeAndDdIsNullOrderByLevelDesc(dntCode);
        Set<Ch> processedChars = new HashSet<>();

        return chs.stream()
                .filter(ch -> !processedChars.contains(ch))
                .flatMap(ch -> {
                    Map<Ch, Integer> chMap = allGuestsWithDynastyDepthByCh(ch);
                    processedChars.addAll(chMap.keySet());
                    return chMap.entrySet().stream();
                })
                .flatMap(entry -> realmOfCh(entry.getKey()).stream()
                        .map(title -> new AbstractMap.SimpleEntry<>(title,entry.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,Math::max));
    }
}
