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

package net.harmonytheory.apt.scanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;

import net.harmonytheory.apt.annotation.SqliteBean;

/**
 * SqliteBeanHelper生成用クラス。
 * ScannerBaseを継承しているが、クラスを走査はしていない。
 * 前の処理でSqliteBeanScannerで処理したクラスをパラメータにして生成し、
 * SqliteOpenHelperの継承クラスを生成する。
 * @author suppi~
 */
public class SqliteBeanHelperBaseScanner extends ScannerBase {
	/** 処理対象リスト。*/
	private List<Element> elementList;
	/** テンプレートクラス。*/
	private SqliteHelperTemplate template;
	/**
	 * コンストラクタ。
	 * 処理対象のBeanアノテーションが指定されたElementリストを指定する。
	 * @param elementList 処理対象のBeanアノテーションが指定されたElementリスト
	 */
	private SqliteBeanHelperBaseScanner(List<Element> elementList) {
		// とりあえず先頭かなぁ・・
		super(elementList.get(0));
		this.elementList = elementList;
		template = new SqliteHelperTemplate();
	}
	@Override
	protected TemplateBase getTemplate() {
		return template;
	}
	@Override
	public void generate() {
		SqliteBean sqliteBean = classElement.getAnnotation(SqliteBean.class);

		String helperBaseName = sqliteBean.helper();
		// Helper名末尾にアンダースコアをつけたものを親クラスとする（BeanSqliteHelperBaseScannerで生成）
		int packageIndex = helperBaseName.lastIndexOf(".");
		String sourceName = helperBaseName.substring(packageIndex + 1).concat("_");
		String sourcePackageName = helperBaseName.substring(0, packageIndex);

		template.setSourceName(sourceName);
    	template.setSourcePackageName(sourcePackageName);
    	template.setHelperBaseName(helperBaseName);

		// onCreateでサブクラスのメソッドを叩く用
    	for (Element element : elementList) {
    		template.addBeanHelperName(
    				new StringBuilder()
    					.append(sourcePackageName)
    					.append(".")
    					.append(element.getSimpleName().toString())
    					.append(SqliteBeanScanner.POST_FIX)
    					.toString());
    	}
	}
	
	public static List<SqliteBeanHelperBaseScanner> getScannerList(Collection<? extends Element> elementList) {
		List<SqliteBeanHelperBaseScanner> scannerList = new ArrayList<SqliteBeanHelperBaseScanner>();
		Map<String, List<Element>> elementMap = new HashMap<String, List<Element>>();
		for (Element element : elementList) {
			SqliteBean sqliteBean = element.getAnnotation(SqliteBean.class);
			String helper = sqliteBean.helper();
			List<Element> list = elementMap.get(helper);
			if (list == null) {
				list = new ArrayList<Element>();
				elementMap.put(helper, list);
			}
			list.add(element);
		}
		for (List<Element> list : elementMap.values()) {
			scannerList.add(new SqliteBeanHelperBaseScanner(list));
		}
		return scannerList;
	}

    private class SqliteHelperTemplate extends TemplateBase {
    	@Override
    	public String getTemplateName() {
    		return "SqliteBeanHelperBase.java.mvel";
    	}
    	@Override
    	public void setSourceName(String sourceName) {
    		super.setSourceName(sourceName);
    		putTemplateArg("sourceName", sourceName);
    	}
    	@Override
    	public void setSourcePackageName(String sourcePackageName) {
    		super.setSourcePackageName(sourcePackageName);
    		putTemplateArg("sourcePackageName", sourcePackageName);
    	}
    	public void setHelperBaseName(String helperBaseName) {
    		putTemplateArg("helperBaseName", helperBaseName);
    	}
    	public void addBeanHelperName(String name) {
    		List<Map<String, String>> columnList = (List<Map<String, String>>) getTemplateArg("beanHelperList");
    		if (columnList == null) {
    			columnList = new ArrayList<Map<String,String>>();
    			putTemplateArg("beanHelperList", columnList);
    		}
    		Map<String, String> columnMap = new HashMap<String, String>();
    		columnList.add(columnMap);
    		
    		columnMap.put("name", name);
    	}
    }
}
