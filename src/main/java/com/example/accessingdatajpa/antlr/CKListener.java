// Generated from C:/Users/Lucas/IdeaProjects/CK2TitleMap/src/main/java/com/example/accessingdatajpa/antlr\CK.g4 by ANTLR 4.12.0
package com.example.accessingdatajpa.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CKParser}.
 */
public interface CKListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CKParser#init}.
	 * @param ctx the parse tree
	 */
	void enterInit(CKParser.InitContext ctx);
	/**
	 * Exit a parse tree produced by {@link CKParser#init}.
	 * @param ctx the parse tree
	 */
	void exitInit(CKParser.InitContext ctx);
	/**
	 * Enter a parse tree produced by {@link CKParser#pair}.
	 * @param ctx the parse tree
	 */
	void enterPair(CKParser.PairContext ctx);
	/**
	 * Exit a parse tree produced by {@link CKParser#pair}.
	 * @param ctx the parse tree
	 */
	void exitPair(CKParser.PairContext ctx);
	/**
	 * Enter a parse tree produced by {@link CKParser#map}.
	 * @param ctx the parse tree
	 */
	void enterMap(CKParser.MapContext ctx);
	/**
	 * Exit a parse tree produced by {@link CKParser#map}.
	 * @param ctx the parse tree
	 */
	void exitMap(CKParser.MapContext ctx);
	/**
	 * Enter a parse tree produced by {@link CKParser#list}.
	 * @param ctx the parse tree
	 */
	void enterList(CKParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CKParser#list}.
	 * @param ctx the parse tree
	 */
	void exitList(CKParser.ListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CKParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(CKParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link CKParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(CKParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link CKParser#val}.
	 * @param ctx the parse tree
	 */
	void enterVal(CKParser.ValContext ctx);
	/**
	 * Exit a parse tree produced by {@link CKParser#val}.
	 * @param ctx the parse tree
	 */
	void exitVal(CKParser.ValContext ctx);
}