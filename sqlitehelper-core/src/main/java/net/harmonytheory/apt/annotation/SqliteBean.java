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
 * Beanクラスに指定するアノテーション。
 * 指定した場合にBeanに対応するSqliteOpenHelperを生成する。
 * 
 * @author suppi~
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface SqliteBean {
	/**
	 * テーブル名を指定する。
	 * 指定しない場合はクラス名をアッパーケースに変更したものになる。
	 * @return テーブル名
	 */
    String table() default "";
	/**
	 * 基底のSqliteOpenHelperクラスを指定する。
	 * バージョン名や、DB名のコンストラクタのみ指定したabstractクラスを指定する。
	 * @return SqliteOpenHelperクラス名
	 */
    String helper();
	/**
	 * insert時にnullの場合の代替値を指定する。
	 * @return insert時にnullの場合の代替値
	 */
    String nullColumnHack() default "";
}
