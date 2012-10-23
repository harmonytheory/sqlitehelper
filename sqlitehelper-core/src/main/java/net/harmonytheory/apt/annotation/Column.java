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

package net.harmonytheory.apt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sqliteカラム指定アノテーション。
 * Beanクラスのフィールドに定義しているSqliteのカラムに指定する。
 * 
 * @author suppi~
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Column {
	/**
	 * カラム名を指定する。
	 * 指定しない場合は変数名をアッパーケースに変更したものになる。
	 * @return カラム名
	 */
    String name() default "";
	/**
	 * カラムタイプを指定する。
	 * 指定しない場合は以下の変数の型から判定する。
	 * int,long：INTEGER
	 * doubel：REAL
	 * byte：BLOB
	 * 上記以外：TEXT
	 * @return カラムタイプ
	 */
    String type() default "";
	/**
	 * プライマリキーの場合にtrueを指定する。
	 * trueを指定した場合以下で使用される。
	 * ・CREATE文で使用
	 * ・生成されるselectメソッド等のパラメータ
	 * @return プライマリキーの場合true
	 */
    boolean primary() default false;
	/**
	 * カラムサイズを指定する。
	 * 指定しない場合はCREATE文でサイズ指定されない。
	 * @return カラムサイズ
	 */
    int size() default 0;
}
