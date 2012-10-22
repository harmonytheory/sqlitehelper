package net.harmonytheory.apt.common;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

public class Log {
	private static Messager messager = null;

	private static boolean debug = false;

	public static void setup(Messager m) {
		messager = m;
	}
	public static void setDebug(boolean debugMode) {
		debug = debugMode;
	}
	

	public static void d() {
		if (!debug) {
			return;
		}
		d(getStackInfo());
	}

	public static void d(String msg) {
		if (!debug) {
			return;
		}
		if (messager == null) {
			return;
		}
		messager.printMessage(Diagnostic.Kind.NOTE, msg);
	}

	public static void w(String msg) {
		messager.printMessage(Diagnostic.Kind.WARNING, msg);
	}

	public static void w(String msg, Element element) {
		messager.printMessage(Diagnostic.Kind.WARNING, msg, element);
	}

	public static void e(String msg) {
		messager.printMessage(Diagnostic.Kind.ERROR, msg);
	}

	public static void e(String msg, Element element) {
		messager.printMessage(Diagnostic.Kind.ERROR, msg, element);
	}

	public static void e(Throwable e) {
		messager.printMessage(Diagnostic.Kind.ERROR,
				"exception thrown! " + e.getMessage());
	}

	public static void e(Throwable e, Element element) {
		messager.printMessage(Diagnostic.Kind.ERROR,
				"exception thrown! " + e.getMessage(), element);
	}

	static String getStackInfo() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for (StackTraceElement stack : stackTrace) {
			if (Thread.class.getCanonicalName().equals(stack.getClassName())) {
				continue;
			} else if (Log.class.getCanonicalName().equals(stack.getClassName())) {
				continue;
			} else {
				return stack.getClassName() + "#" + stack.getMethodName() + "@L"
						+ stack.getLineNumber();
			}
		}
		return "unknown";
	}
}
