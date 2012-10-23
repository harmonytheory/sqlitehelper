/*
 * Copyright 2012 suppi~
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.harmonytheory.apt.common;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * メッセージ出力クラス。
 * @author SPEEDY
 */
public class Log {
	/** メッセージ出力クラス。*/
	private static Messager messager = null;

	/**
	 * 初期設定。
	 * processingEnvから取得したメッセージ出力クラスを保持する。
	 * @param m メッセージ出力クラス
	 */
	public static void setup(Messager m) {
		messager = m;
	}
	
	/**
	 * 警告メッセージを出力する。
	 * @param element 対象Element
	 * @param msg メッセージ
	 */
	public static void w(Element element, String msg) {
		messager.printMessage(Diagnostic.Kind.WARNING, msg, element);
	}
	/**
	 * エラーメッセージを出力する。
	 * @param element 対象Element
	 * @param msg メッセージ
	 */
	public static void e(Element element, String msg) {
		messager.printMessage(Diagnostic.Kind.ERROR, msg, element);
	}
	/**
	 * エラーメッセージを出力する。
	 * @param element 対象Element
	 * @param e 発生エラー
	 */
	public static void e(Element element, Throwable e) {
		messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
	}
}
