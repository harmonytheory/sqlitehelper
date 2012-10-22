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
				Log.e(th, element);
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
				Log.e(th);
				th.printStackTrace();
			}
		}

		return true;
	}
}
