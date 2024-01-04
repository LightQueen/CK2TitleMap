package com.example.accessingdatajpa.service;

import com.example.accessingdatajpa.antlr.CKBaseVisitor;
import com.example.accessingdatajpa.antlr.CKParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class CKVisitorService extends CKBaseVisitor<String> {

    private int depth = 0;

    private static final char INDENT = '\t';

    private List inList = Arrays.asList(new String[]{"dynasties", "character", "title"});

    private int index = 0;

    private String startVar = "";

    private int targetDepth = 0;

    private boolean start = false;

    public void init() {
        depth = 0;
        index = 0;
        start = false;
    }

    public void setStartVar(String startVar) {
        this.startVar = startVar;
    }

    public void setTargetDepth(int targetDepth) {
        this.targetDepth = targetDepth;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private String getIndent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append(INDENT);
        }
        return sb.toString();
    }

    public void visit(ParseTree tree,String toFileName) {
        // 将 String 写入文件
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(toFileName))) {
            bw.write(visit(tree));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String visitInit(CKParser.InitContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (CKParser.PairContext pairCtx : ctx.pair()) {
            sb.append(visit(pairCtx)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String visitPair(CKParser.PairContext ctx) {

        String var = ctx.VAR().getText();
        String val;
        if (depth == 0) {
            if (!"CK2txt".equals(var)) {
                return "";
            }
            val = visit(ctx.val());
            return val;
        }
        if (depth == 1 && inList.indexOf(var) == -1) {
            return "";
        }
        if (targetDepth == depth ) {
            if (!start && startVar.equals(var)) {
                start = true;
            } else if (!start){
                return "";
            }
        }
        val = visit(ctx.val());
        return getIndent() + var + "=" + val;
    }

    @Override
    public String visitMap(CKParser.MapContext ctx) {
        StringBuilder sb = new StringBuilder();

        if ( depth != 0 ) {
            if (depth == 1) {
                sb.append("\n").append(getIndent());
            }
            sb.append("{\n");
        }
        depth++;
        for (CKParser.PairContext pairCtx : ctx.pair()) {
            String visit = visit(pairCtx);
            if (visit.length() > 0)
                sb.append(visit).append("\n");
        }
        depth--;
        if (depth != 0) {
            sb.append(getIndent()).append("}");
        }
        return sb.toString();
    }

    @Override
    public String visitList(CKParser.ListContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (CKParser.MapContext mapCtx : ctx.map()) {
            sb.append(visit(mapCtx)).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String visitArray(CKParser.ArrayContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (TerminalNode varNode : ctx.VAR()) {
            sb.append(varNode.getText()).append(" ");
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String visitVal(CKParser.ValContext ctx) {
        if (ctx.VAR() != null) {
            return ctx.VAR().getText();
        } else if (ctx.STR() != null) {
            return ctx.STR().getText();
        } else if (ctx.map() != null) {
            return visitMap(ctx.map());
        } else if (ctx.list() != null) {
            return visitList(ctx.list());
        } else if (ctx.array() != null) {
            return visitArray(ctx.array());
        } else {
            return "";
        }
    }
}
