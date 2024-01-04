package com.example.accessingdatajpa.service;

import com.example.accessingdatajpa.antlr.CKBaseListener;
import com.example.accessingdatajpa.antlr.CKParser;
import com.example.accessingdatajpa.dao.*;
import com.example.accessingdatajpa.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CKListenerService extends CKBaseListener{

    @Autowired
    public DynastyRepository dynastyRepository;
    @Autowired
    public ChRepository chRepository;
    @Autowired
    public TitleRepository titleRepository;
    @Autowired
    public TitleHistoryRepository titleHistoryRepository;
    @Autowired
    public TransactionService transactionService;

    private int depth = 0;
    private int index = 0;
    private boolean isTitleHistory = false;

    private int BATCH_SIZE = 1;
    /**
     * 临时对象
     */
    private Dynasty dyn;

    private Ch ch;

    private Title title;

    private TitleHistory titleHistory;
    private TitleHistory lastTitleHistory;

    /**
     * 待保存队列
     */
    private List<Dynasty> dyns;
    private List<Ch> chs;
    private List<Title> titles;
    private List<TitleHistory> titleHistories;

    /**
     * 全量数据缓存
     */
    private Map<String, Dynasty> dynMap;
    private Map<String, Ch> chMap;
    private Map<String, Title> titleMap;


    public void init() {
        dyns = new ArrayList<>();
        chs = new ArrayList<>();
        titles = new ArrayList<>();
        dynMap = new HashMap<>();
        chMap = new HashMap<>();
        titleMap = new HashMap<>();
        dynastyRepository.findAll().forEach(d -> dynMap.put(d.getCode(), d));
        chRepository.findAll().forEach(c -> chMap.put(c.getCode(), c));
        titleRepository.findAll().forEach(t -> titleMap.put(t.getCode(), t));
        isTitleHistory = false;
        depth = 0;
        index = 0;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public<T extends BaseEntity> void batchSave(List<T> list,Map<String,T> map, BaseRepository<T> repository) {
        System.out.print("进入batchSave，开始保存" + list.size() + "条数据，");
        for (T t : list) {
            System.out.print(t.getCode()+",");
        }
        System.out.println();
        List<T> newList = transactionService.doInTransaction(() -> {
            System.out.println(titleMap.size());
            List<T> tmp = new ArrayList<>();
            for (T t : list) {
                    tmp.add(repository.save(t));
            }
            return tmp;
        });
        list.clear();
        System.out.println("退出batchSave");
        map.putAll(newList.stream().collect(Collectors.toMap(BaseEntity::getCode, en -> en)));
    }

    /**
     *
     * 大概流程如下：
     * depth == 0，退出时保存实体
     * depth == 1，进入时创建新的实体，退出时保存实体
     * depth == 2，实体属性赋值
     * depth == 3，进入时创建历史的实体，不过退出时没有保存实体，而是将实体添加到 titleHistories 中，然后在上一层退出时赋值给 title
     * depth == 4，历史实体属性赋值
     * @param ctx the parse tree
     */
    @Override
    public void enterPair(CKParser.PairContext ctx) {

        if (depth == 0) {
            index ++;
        } else if (depth == 1) {
            if (index == 1) {
                String code = ctx.VAR().getText().replace("\"", "");
                // 根据 ID 查询实体，如果不存在则新建
                dyn = dynMap.get(code);
                if (dyn == null) {
                    dyn = new Dynasty();
                    dyn.setCode(code);
                    dynMap.put(code, dyn);
                }
                dyns.add(dyn);
            }else if (index == 2) {
                String code = ctx.VAR().getText().replace("\"", "");
                ch = chMap.get(code);
                if (ch == null) {
                    ch = new Ch();
                    ch.setCode(code);
                    chMap.put(code, ch);
                }
                chs.add(ch);
            }else if (index == 3) {
                String code = ctx.VAR().getText().replace("\"", "");
                title = titleMap.get(code);
                if (title == null) {
                    title = new Title();
                    title.setCode(code);
                    titleMap.put(code, title);
                }
                // 做一个头部插入
                titles.add(0,title);
            }
        } else if (depth == 2) {
            if (index == 1) {
                prepareDyn(ctx);
            }else if (index == 2) {
                prepareCh(ctx);
            }else if (index == 3) {
                prepareTitle(ctx);
            }
        } else if (depth == 3) {
            if (isTitleHistory) {
                String dateString = ctx.VAR().getText();
                Date date = convertDate(dateString);
                titleHistory = new TitleHistory();
                titleHistory.setTitle(title);
                titleHistory.setSd(date);
                titleHistory.setLatest(true);
            }
        } else if (depth == 4) {
            if (isTitleHistory) {
                prepareTitleHistory(ctx);
            }
        }
    }

    @Override
    public void exitPair(CKParser.PairContext ctx) {
        if (depth == 0) {
            if (index == 1) {
                if (dyns.size() >= 1) {
                    batchSave(dyns, dynMap, dynastyRepository);
                }
            } else if (index == 2) {
                if (chs.size() >= 1) {
                    if (titles.size() >= 1) {
                        batchSave(titles,titleMap, titleRepository);
                    }
                    batchSave(chs,chMap,chRepository);
                }
            } else if (index == 3) {
                if (titles.size() >= 1) {
                    batchSave(titles,titleMap, titleRepository);
                }
            }
        } else if (depth == 1){
            if (index == 2) {
                if (chs.size() >= BATCH_SIZE) {
                    if (titles.size() >= 1) {
                        batchSave(titles, titleMap, titleRepository);
                    }
                    batchSave(chs,chMap,chRepository);
                }
            } else if (index == 3) {
                if (titles.size() >= BATCH_SIZE) {
                    batchSave(titles,titleMap, titleRepository);
                }
            }
        } else if (depth == 2){
            if (isTitleHistory) {
                List<TitleHistory> titleHistoriesTemp = title.getTitleHistories();
                titleHistoriesTemp.clear();
                titleHistoriesTemp.addAll(titleHistories);
                lastTitleHistory = null;
                isTitleHistory = false;
            }
        } else if (depth == 3) {
            if (isTitleHistory) {
                // 计算持有天数（日期差值）
                if (lastTitleHistory != null) {
                    lastTitleHistory.setEd(titleHistory.getSd());
                    long differenceMillis = lastTitleHistory.getEd().getTime() - lastTitleHistory.getSd().getTime();
                    long differenceDays = TimeUnit.DAYS.convert(differenceMillis, TimeUnit.MILLISECONDS);
                    lastTitleHistory.setHoldDays((int) differenceDays);
                    lastTitleHistory.setLatest(false);
                }
                lastTitleHistory = titleHistory;
                titleHistories.add(titleHistory);
                titleHistory = null;
            }
        }
    }

    @Override
    public void enterMap(CKParser.MapContext ctx) {
        depth++;
    }

    @Override
    public void exitMap(CKParser.MapContext ctx) {
        depth--;
    }

    private Date convertDate(String val){
        String[] dateParts = val.split("\\.");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);
        val = String.format("%04d-%02d-%02d", year, month, day);
        return Date.valueOf(val);
    }

    private void prepareDyn(CKParser.PairContext ctx) {
        String key = ctx.VAR().getText();
        String val = ctx.val().getText().replace("\"", "");
        switch (key) {
            case "name":
                dyn.setName(val);
                break;
        }
    }

    private void prepareCh(CKParser.PairContext ctx) {
        String key = ctx.VAR().getText();
        String val = ctx.val().getText().replace("\"", "");

        Dynasty tempDyn;
        Ch tempCh;
        Title tempTitle;
        switch (key) {
            case "bn":
                ch.setName(val);
                break;
            case "fem":
                ch.setFem(true);
                break;
            case "b_d":
                ch.setBd(convertDate(val));
                break;
            case "d_d":
                ch.setDd(convertDate(val));
                break;
            case "dnt":
                tempDyn = dynMap.get(val);
                ch.setDnt(tempDyn);
                break;
            case "host":
                // 特殊逻辑，如果 host 为本人，则不做处理
                if (ch.getCode().equals(val)) {
                    break;
                }
                tempCh = chMap.get(val);
                if (tempCh == null) {
                    tempCh = new Ch();
                    tempCh.setCode(val);
                    chMap.put(val, tempCh);
                }
                ch.setHost(tempCh);
                break;
            case "fat":
                tempCh = chMap.get(val);
                if (tempCh == null) {
                    tempCh = new Ch();
                    tempCh.setCode(val);
                    chMap.put(val, tempCh);
                }
                ch.setFat(tempCh);
                break;
            case "mot":
                tempCh = chMap.get(val);
                if (tempCh == null) {
                    tempCh = new Ch();
                    tempCh.setCode(val);
                    chMap.put(val, tempCh);
                }
                ch.setMot(tempCh);
                break;
            case "wealth":
                ch.setWealth(new BigDecimal(val));
                break;
            case "piety":
                ch.setPiety(new BigDecimal(val));
                break;
            case "prs":
                ch.setPrs(new BigDecimal(val));
                break;
            case "oh":
                tempTitle = titleMap.get(val);
                if (tempTitle == null) {
                    tempTitle = new Title();
                    tempTitle.setCode(val);
                    titleMap.put(val, tempTitle);
                    titles.add(tempTitle);
                }
                ch.getOh().add(tempTitle);
                break;
        }
    }


    private void prepareTitle(CKParser.PairContext ctx) {
        String key = ctx.VAR().getText();
        String val = ctx.val().getText().replace("\"", "");

        Ch tempCh;
        Title tempTitle;
        switch (key) {
            case "holder":
                tempCh = chMap.get(val);
                title.setHolder(tempCh);
                break;
            case "liege":
                tempTitle = titleMap.get(val);
                if (tempTitle == null) {
                    tempTitle = new Title();
                    tempTitle.setCode(val);
                    titleMap.put(val, tempTitle);
                }
                title.setLiege(tempTitle);
                break;
            case "de_jure_liege":
                tempTitle = titleMap.get(val);
                if (tempTitle == null) {
                    tempTitle = new Title();
                    tempTitle.setCode(val);
                    titleMap.put(val, tempTitle);
                }
                title.setDe_jure_liege(tempTitle);
                break;
            case "history":
                isTitleHistory = true;
                titleHistories = new ArrayList<>();
                break;
        }
    }


    private void prepareTitleHistory(CKParser.PairContext ctx) {
        String key = ctx.VAR().getText();
        String val = ctx.val().getText().replace("\"", "");

        Ch tempCh;
        switch (key) {
            case "holder":
                tempCh = chMap.get(val);
                titleHistory.setHolder(tempCh);
                break;
        }
    }
}