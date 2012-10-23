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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.util.ElementScanner6;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import org.mvel2.templates.TemplateRuntime;

/**
 * スキャナ基底クラス。
 * @author suppi~
 */
public abstract class ScannerBase extends ElementScanner6<Void, Void> {
	protected static ProcessingEnvironment prcEnv;
	protected static Types typeUtils;
	protected static Elements elementUtils;
	
	/** 処理対象。*/
	protected Element classElement;
	/**
	 * 初期設定。
	 * @param pEnv
	 */
	public static void setup(ProcessingEnvironment pEnv) {
		prcEnv = pEnv;
		typeUtils = pEnv.getTypeUtils();
		elementUtils = pEnv.getElementUtils();
	}
	/**
	 * コンストラクタ。
	 * @param element クラスエレメント
	 * @param sqliteHelperTemplate 
	 */
	public ScannerBase(Element element) {
		classElement = element;
	}
	
	/**
	 * 処理対象Elementクラスを取得する。
	 * @return 処理対象Element
	 */
	public Element getClassElement() {
		return classElement;
	}
	
	/**
	 * テンプレートクラス生成。
	 * @return 各スキャナ用テンプレートクラス
	 */
	protected abstract TemplateBase getTemplate();
	
	/**
	 * ソース生成処理。
	 */
	public abstract void generate();
	
	/**
	 * ソースコード出力。
	 * @return 
	 * @throws IOException 
	 */
	public String output() throws IOException {
		TemplateBase template = getTemplate();
		// mvelからソースコード生成
		String gen = (String) TemplateRuntime.eval(
				ScannerBase.class.getClassLoader().getResourceAsStream(template.getTemplateName()), 
				null, 
				template.getTemplateArg());
		// ソースコード生成
		String sourcePath = template.getSourcePackageName().concat(".").concat(template.getSourceName());
		JavaFileObject javaFileObj = prcEnv.getFiler().createSourceFile(sourcePath, classElement);
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(javaFileObj.openOutputStream(), "UTF-8"));
		writer.write(gen);
		writer.flush();
		writer.close();
		return gen;
	}
	
    /**
     * キャメル記法を_記法に変換します。
     * 
     * @param s  テキスト
     * @return 結果の文字列
     */
    protected String decamelize(final String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == 1) {
            return s.toUpperCase();
        }
        StringBuilder buf = new StringBuilder(40);
        int pos = 0;
        for (int i = 1; i < s.length(); ++i) {
            if (Character.isUpperCase(s.charAt(i))) {
                if (buf.length() != 0) {
                    buf.append('_');
                }
                buf.append(s.substring(pos, i).toUpperCase());
                pos = i;
            }
        }
        if (buf.length() != 0) {
            buf.append('_');
        }
        buf.append(s.substring(pos, s.length()).toUpperCase());
        return buf.toString();
    }

    /**
     * Mvelテンプレートクラス。
     * @author SPEEDY
     */
    protected abstract class TemplateBase {
    	/** Mvelテンプレートパラメータ。*/
    	protected Map<String, Object> templateArg = new LinkedHashMap<String, Object>();
    	/** 生成クラス名。*/
    	private String sourceName;
    	/** 生成クラスパッケージ名。*/
    	private String sourcePackageName;
    	/**
    	 * Mvel用テンプレートファイル名取得処理。
    	 * @return Mvel用テンプレートファイル名
    	 */
		public abstract String getTemplateName();
    	public Map<String, Object> getTemplateArg() {
    		return templateArg;
    	}
    	public void putTemplateArg(String key, Object value) {
    		templateArg.put(key, value);
    	}
    	public Object getTemplateArg(String key) {
    		return templateArg.get(key);
    	}
    	public String getSourcePackageName() {
			return sourcePackageName;
		}
    	public void setSourcePackageName(String sourcePackageName) {
			this.sourcePackageName = sourcePackageName;
		}
		public String getSourceName() {
			return sourceName;
		}
		public void setSourceName(String sourceName) {
			this.sourceName = sourceName;
		}
    }
}
