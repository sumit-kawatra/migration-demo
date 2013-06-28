package com.markitserv.msws.internal;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.danielbechler.diff.ObjectDiffer;
import de.danielbechler.diff.ObjectDifferFactory;
import de.danielbechler.diff.node.Node;
import de.danielbechler.diff.visitor.PrintingVisitor;

/**
 * Handy utilities used for debugging only.
 */
public class DebugUtils {

	/**
	 * Diffs two objects and dumps the output to the console
	 * 
	 * @param expected
	 * @param actual
	 */
	public static void printObjectDiffToConsole(Object expected, Object actual) {

		System.err.println("Comparing expected:");
		System.err.println(expected.toString());
		System.err.println("with");
		System.err.println(actual.toString());

		ObjectDiffer differ = ObjectDifferFactory.getInstance();
		Node diff = differ.compare(expected, actual);
		diff.visit(new PrintingVisitor(expected, actual));

	}

	/**
	 * Modeled after perl's Data::Dumper
	 * @param obj
	 * @return
	 */
	public static String dumper(Object obj) {
		return ReflectionToStringBuilder.toString(obj,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public static void printDumperToConsole(Object obj) {
		System.err.println(dumper(obj));
	}
}
