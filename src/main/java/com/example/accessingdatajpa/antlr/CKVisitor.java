// Generated from G:/Documents/myjob/IdeaProjects2/demo2/demo2/src/main/java/com/example/accessingdatajpa/antlr\CK.g4 by ANTLR 4.10.1
package com.example.accessingdatajpa.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CKParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CKVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CKParser#init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInit(CKParser.InitContext ctx);
	/**
	 * Visit a parse tree produced by {@link CKParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(CKParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link CKParser#pair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair(CKParser.PairContext ctx);
	/**
	 * Visit a parse tree produced by {@link CKParser#val}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVal(CKParser.ValContext ctx);
}