package com.markitserv.mwws.testutil;

import de.danielbechler.diff.ObjectDiffer;
import de.danielbechler.diff.ObjectDifferFactory;
import de.danielbechler.diff.node.Node;
import de.danielbechler.diff.visitor.PrintingVisitor;

public class Util {
	
	/**
	 * Diffs two objects and dumps the output to the console
	 * @param expected
	 * @param actual
	 */
	public static void printObjectDiffToConsole(Object expected, Object actual) {
		
		System.out.println("Comparing expected:");
		System.out.println(expected.toString());
		System.out.println("with");
		System.out.println(actual.toString());
		
		ObjectDiffer differ = ObjectDifferFactory.getInstance();
		Node diff = differ.compare(expected, actual);
		diff.visit(new PrintingVisitor(expected, actual));
		
	}
}