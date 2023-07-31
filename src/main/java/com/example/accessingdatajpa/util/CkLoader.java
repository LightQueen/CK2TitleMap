package com.example.accessingdatajpa.util;

import com.example.accessingdatajpa.antlr.CKBaseListener;
import com.example.accessingdatajpa.antlr.CKLexer;
import com.example.accessingdatajpa.antlr.CKParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;

/**
 * CkLoader
 *
 * @author Administrator
 * @date 2023/7/31
 */
public class CkLoader {
    public static void run(String s) throws IOException {

        CharStream charStream = CharStreams.fromStream(new FileInputStream(new File("")));
        CKLexer lexer = new CKLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CKParser parser = new CKParser(tokens);
        ParseTree parseTree = parser.init();

        ParseTreeWalker walker = new ParseTreeWalker();
        CKBaseListener listener = new CKBaseListener();
        walker.walk(listener,parseTree);

    }
}
