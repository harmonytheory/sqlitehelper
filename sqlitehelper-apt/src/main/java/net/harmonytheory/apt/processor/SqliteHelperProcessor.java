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

package net.harmonytheory.apt.processor;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import net.harmonytheory.apt.annotation.SqliteBean;
import net.harmonytheory.apt.common.Log;
import net.harmonytheory.apt.scanner.ScannerBase;
import net.harmonytheory.apt.scanner.SqliteBeanHelperBaseScanner;
import net.harmonytheory.apt.scanner.SqliteBeanScanner;

/**
 * 
 * @author suppi~
 */
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("net.harmonytheory.apt.annotation.SqliteBean")
public class SqliteHelperProcessor extends AbstractProcessor {
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		// アノテーションをすでに処理していた場合
		if (annotations.size() == 0) {
			return true;
		}
		Log.setup(processingEnv.getMessager());
		SqliteBeanScanner.setup(processingEnv);
		
		for (Element element : roundEnv.getElementsAnnotatedWith(SqliteBean.class)) {
			try {
				// SqliteBean用スキャナの生成
				ScannerBase scanner = new SqliteBeanScanner(element);
				scanner.generate();
				String output = scanner.output();
				System.out.println(output);
			} catch (Throwable th) {
				Log.e(element, th);
				th.printStackTrace();
			}
		}
		
		List<SqliteBeanHelperBaseScanner> helperScannerList = 
				SqliteBeanHelperBaseScanner.getScannerList(
					roundEnv.getElementsAnnotatedWith(SqliteBean.class));
		for (SqliteBeanHelperBaseScanner helperScanner : helperScannerList) {
			helperScanner.generate();
			try {
				String output = helperScanner.output();
				System.out.println(output);
			} catch (Throwable th) {
				Log.e(helperScanner.getClassElement(), th);
				th.printStackTrace();
			}
		}

		return true;
	}
}
