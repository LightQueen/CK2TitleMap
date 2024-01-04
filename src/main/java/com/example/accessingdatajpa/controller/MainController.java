package com.example.accessingdatajpa.controller;

import com.example.accessingdatajpa.antlr.CKLexer;
import com.example.accessingdatajpa.antlr.CKParser;
import com.example.accessingdatajpa.entity.Title;
import com.example.accessingdatajpa.entity.TitleDay;
import com.example.accessingdatajpa.service.CKListenerService;
import com.example.accessingdatajpa.service.CKVisitorService;
import com.example.accessingdatajpa.service.HongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.util.Collection;
import java.util.Map;

@Api(tags = "文件解析接口")
@Controller
@RequestMapping(path="/main")
@Slf4j
public class MainController {

    @Autowired
    private HongService hongService;

    /**
     * 预处理，可进入特定位置
     */
    @Autowired
    private CKVisitorService visitor;

    /**
     * 正式处理，遍历、保存
     */
    @Autowired
    private CKListenerService listener;


    public ParseTree getParseTree(String toFileName) throws Exception{
        CharStream charStream = CharStreams.fromStream(new FileInputStream(toFileName));
        CKLexer lexer = new CKLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CKParser parser = new CKParser(tokens);
        ParseTree parseTree = parser.init();
        return parseTree;
    }

    @GetMapping(path={"/load"})
    @ApiOperation(value = "加载本地文件")
    public @ResponseBody String loadFile() {

        try {
//            String fromFileName = "D:\\\\Documents\\\\Paradox Interactive\\\\Crusader Kings II\\\\save games\\\\test.txt";
//            String toFileName = "D:\\\\Documents\\\\Paradox Interactive\\\\Crusader Kings II\\\\save games\\\\test.temp.txt";

            String fromFileName = "data/West_Francia769_01_01.ck2";
            String toFileName = "data/West_Francia769_01_01.temp.ck2";

            // 第一种方式
//            CkLoader.interceptFile(fromFileName,toFileName);
            // 第二种方式
            ParseTree parseTree1 = getParseTree(fromFileName);
            visitor.init();
            visitor.setStartVar("c_evora");
            visitor.setTargetDepth(2);
            visitor.visit(parseTree1,toFileName);

            ParseTree parseTree2 = getParseTree(toFileName);
            ParseTreeWalker walker = new ParseTreeWalker();
            listener.init();
//            listener.setIndex(1);
            walker.walk(listener,parseTree2);


            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    @GetMapping(path={"/prepare"})
    @ApiOperation(value = "准备数据")
    public @ResponseBody String prepareHong() {
        hongService.createHong();
        return "OK";
    }

    @GetMapping(path={"/test"})
    @ApiOperation(value = "测试数据")
    public @ResponseBody String testHong() {
        hongService.test();
        return "OK";
    }

    /**
     * 领地统治时长
     *
     * @param chCode "JiaBaoYu"
     * @return
     */
    @GetMapping(path="/realm-his")
    @ApiOperation(value = "领地统治时长")
    public @ResponseBody Collection<TitleDay> realmHis(String chCode) {
        return hongService.realmWithDaysOfChCode(chCode);
    }

    /**
     * 领地统治深度
     *
     * @param dyn 王朝 "Jia"
     * @param type 计算方式 1
     * @return
     */
    @GetMapping(path="/realm-depth")
    @ApiOperation(value = "领地统治深度")
    public @ResponseBody Map<Title, Integer> realmDepth(@Param("dyn") String dyn,@Param("type") Integer type) {
        return hongService.realmWithDynastyDepthByDynCode(dyn,type);
    }

}
